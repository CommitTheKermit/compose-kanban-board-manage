package woowacourse.kanban.board.ui.stateholder

import androidx.compose.runtime.mutableStateListOf
import woowacourse.kanban.commonmodel.KanbanTask
import woowacourse.kanban.commonmodel.TaskStatus

class KanbanProjectState(initTasks: List<KanbanTask>, val title: String = "") {
    val tasks = mutableStateListOf<KanbanTask>().apply { addAll(initTasks) }

    fun changeStatus(
        status: TaskStatus,
        idx: Int,
    ) {
        tasks[idx] = tasks[idx].copy(status = status)
    }
}
