package woowacourse.kanban.board.domain

import woowacourse.kanban.domain.KanbanTask
import woowacourse.kanban.domain.TaskStatus

class KanbanProject(inputTasks: List<KanbanTask>, val title: String = "") {
    private val tasks: List<KanbanTask> = inputTasks.toList()

    fun copy(
        newInputTasks: List<KanbanTask> = tasks,
        newTitle: String = title,
    ): KanbanProject {
        return KanbanProject(newInputTasks, newTitle)
    }

    fun addTask(task: KanbanTask): KanbanProject {
        return copy(
            newInputTasks = tasks + task,
            newTitle = title,
        )
    }

    fun isAssigned(taskId: Long): ChangeStatusReturnType? {
        val targetIndex = tasks.indexOfFirst { it.data.id == taskId }
        require(targetIndex != -1) { "$taskId not found" }
        val targetTask = tasks[targetIndex]

        if (targetTask.data.assignee == null) {
            return ChangeStatusReturnType.NOT_ASSIGNED
        } else {
            return null
        }
    }

    fun changeStatus(
        taskId: Long,
        status: TaskStatus,
    ): StatusChangeResult {
        if (status == TaskStatus.TO_DO) {
            val result = isAssigned(
                taskId = taskId,
            )
            if (result == ChangeStatusReturnType.NOT_ASSIGNED) {
                return StatusChangeResult.NotAssigned
            }
        }

        val targetIndex = tasks.indexOfFirst { it.data.id == taskId }
        require(targetIndex != -1) { "$taskId not found" }
        val targetTask = tasks[targetIndex]

        if (targetTask.status.isChangeable(status)) {
            return StatusChangeResult.Success(
                copy(
                    newInputTasks = tasks.map {
                        if (it.data.id == taskId)
                            it.copy(inputStatus = status)
                        else it
                    },
                ),
            )
        }
        return StatusChangeResult.NotChangeable
    }

    // List로 반환하더라도 toMutableList()를 통해 캐스팅하면 원본 리스트에 대해서
    // 조작이 가능하다. 반환할 때 toList()를 사용해 새로운 리스트를 만들어 주면
    // 원본 리스트와는 다른 리스트로 반환되게 됨으로 조작이 차단된다.
    fun getTasks(): List<KanbanTask> {
        return tasks.toList()
    }

    fun getTasksByStatus(status: TaskStatus): List<KanbanTask> {
        return tasks.filter { it.status == status }
    }

    fun deleteTask(taskId: Long): DeleteResult {
        val targetIndex = tasks.indexOfFirst { it.data.id == taskId }
        require(targetIndex != -1) { "$taskId not found" }
        val targetTask = tasks[targetIndex]
        return if (targetTask.status.isRemovable) {
            DeleteResult.Success(copy(newInputTasks = tasks.filter { it.data.id != taskId }))
        } else DeleteResult.NotDeletable
    }

    fun updateTask(task: KanbanTask): KanbanProject {
        return copy(
            newInputTasks = tasks.map {
                if (it.data.id == task.data.id)
                    task
                else it
            },
        )
    }
}
