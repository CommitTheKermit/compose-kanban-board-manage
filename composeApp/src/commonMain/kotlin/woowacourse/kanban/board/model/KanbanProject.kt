package woowacourse.kanban.board.model

import woowacourse.kanban.commonmodel.KanbanTask

class KanbanProject(inputTasks: List<KanbanTask>, val title: String = "") {
    private val tasks: MutableList<KanbanTask> = inputTasks.toMutableList()
    fun addTask(task: KanbanTask) {
        tasks.add(task)
    }

    fun getTasks(): List<KanbanTask> {
        return tasks.toList()
    }
}
