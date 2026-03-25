package woowacourse.kanban.board.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import woowacourse.kanban.board.model.KanbanProject
import woowacourse.kanban.model.KanbanTask
import woowacourse.kanban.model.TaskStatus

class BoardState(
    private val scope: CoroutineScope,
    project: KanbanProject,
    private val snackBarHostState: SnackbarHostState = SnackbarHostState(),
) {

    private val totalTasks: MutableList<KanbanTask> = project.tasks

    val totalTaskCount by derivedStateOf { totalTasks.size }

    val todoCardList: List<KanbanTask> by derivedStateOf { totalTasks.filter { task -> task.status == TaskStatus.TO_DO } }

    val inProgressCardList: List<KanbanTask> by derivedStateOf { totalTasks.filter { task -> task.status == TaskStatus.IN_PROGRESS } }

    val doneCardList: List<KanbanTask> by derivedStateOf { totalTasks.filter { task -> task.status == TaskStatus.DONE } }

    val progress by derivedStateOf {
        if (totalTasks.isEmpty()) 0.0 else doneCardList.size.toDouble() / totalTasks.size.toDouble()
    }

    val showDialog = mutableStateOf(false)

    fun totalTasksGetter(): MutableList<KanbanTask> {
        return totalTasks
    }

    fun showKanbanSnackBar(message: String) {
        scope.launch {
            snackBarHostState.currentSnackbarData?.dismiss()
            snackBarHostState.showSnackbar(message)
        }
    }
}
