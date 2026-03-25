package woowacourse.kanban.board.model

import woowacourse.kanban.model.KanbanTask
import woowacourse.kanban.model.TaskStatus

class TaskManager(private val tasks: MutableList<KanbanTask>) {

    fun addTask(task: KanbanTask) {
        tasks.add(task)
    }

    fun changeStatus(
        task: KanbanTask,
        status: TaskStatus,
        idx: Int,
    ) {
        tasks[idx] = task.copy(status = status)
    }
}
