package woowacourse.kanban.board.model

import androidx.compose.runtime.mutableStateListOf
import woowacourse.kanban.model.KanbanTask

class KanbanProject(inputTasks: MutableList<KanbanTask>, val title: String = "") {
    val tasks = mutableStateListOf<KanbanTask>()

    init {
        tasks.addAll(inputTasks)
    }
}
