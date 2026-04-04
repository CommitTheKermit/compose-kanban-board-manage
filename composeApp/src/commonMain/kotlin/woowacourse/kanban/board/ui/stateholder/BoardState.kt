package woowacourse.kanban.board.ui.stateholder

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import woowacourse.kanban.board.domain.ChangeStatusReturnType
import woowacourse.kanban.board.domain.DeleteResult
import woowacourse.kanban.board.domain.DeleteReturnType
import woowacourse.kanban.board.domain.KanbanProject
import woowacourse.kanban.board.domain.StatusChangeResult
import woowacourse.kanban.domain.KanbanTask
import woowacourse.kanban.domain.TaskStatus

class BoardState(initProject: KanbanProject) {

    private var project by mutableStateOf(initProject)

    private val totalTasks by derivedStateOf { project.getTasks() }

    val totalTaskCount by derivedStateOf { totalTasks.size }

    val progress by derivedStateOf {
        if (totalTasks.isEmpty()) 0.0 else project.getTasksByStatus(TaskStatus.DONE).size.toDouble() / totalTasks.size.toDouble()
    }

    var currentTask by mutableStateOf<KanbanTask?>(null)
        private set

    var showDialog by mutableStateOf(false)
        private set

    // 키고 끄고, 수정/삭제, 신규를 표시해야함, 수정/삭제일경우 태스크값을 넘겨야함
    fun toggleDialog(
        controlValue: Boolean,
        task: KanbanTask? = null,
    ) {
        showDialog = controlValue
        currentTask = task
    }

    fun addTask(task: KanbanTask) {
        project = project.addTask(task)
    }

    fun changeStatus(
        taskId: Long,
        status: TaskStatus,
    ): ChangeStatusReturnType {
        val result = project.changeStatus(taskId, status)
        return when (result) {
            is StatusChangeResult.Success -> {
                project = result.project
                ChangeStatusReturnType.CHANGE_SUCCESS
            }

            StatusChangeResult.NotChangeable ->
                ChangeStatusReturnType.NOT_CHANGEABLE

            StatusChangeResult.NotAssigned ->
                ChangeStatusReturnType.NOT_ASSIGNED
        }
    }

    fun getTasksByStatus(status: TaskStatus): List<KanbanTask> {
        return project.getTasksByStatus(status)
    }

    fun deleteTask(taskId: Long): DeleteReturnType {
        return when (val result = project.deleteTask(taskId)) {
            is DeleteResult.Success -> {
                project = result.project
                DeleteReturnType.DELETE_SUCCESS
            }

            is DeleteResult.NotDeletable -> DeleteReturnType.NOT_DELETABLE
        }
    }

    fun updateTask(task: KanbanTask) {
        project = project.updateTask(task)
    }
}
