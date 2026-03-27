package woowacourse.kanban.board.ui.stateholder

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import woowacourse.kanban.commonmodel.KanbanTask
import woowacourse.kanban.commonmodel.TaskStatus

class BoardState(initTasks: MutableList<KanbanTask>) {

    private val totalTasks: MutableList<KanbanTask> = initTasks

    val totalTaskCount by derivedStateOf { totalTasks.size }

    val todoCardList: List<KanbanTask> by derivedStateOf { totalTasks.filter { task -> task.status == TaskStatus.TO_DO } }

    val inProgressCardList: List<KanbanTask> by derivedStateOf { totalTasks.filter { task -> task.status == TaskStatus.IN_PROGRESS } }

    val doneCardList: List<KanbanTask> by derivedStateOf { totalTasks.filter { task -> task.status == TaskStatus.DONE } }

    val progress by derivedStateOf {
        if (totalTasks.isEmpty()) 0.0 else doneCardList.size.toDouble() / totalTasks.size.toDouble()
    }

    val showDialog = mutableStateOf(false)

    fun totalTasksGetter(): List<KanbanTask> {
        return totalTasks
    }
}
