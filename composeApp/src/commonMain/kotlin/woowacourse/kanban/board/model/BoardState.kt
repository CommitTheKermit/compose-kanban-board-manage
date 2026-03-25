package woowacourse.kanban.board.model

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import woowacourse.kanban.model.KanbanTask
import woowacourse.kanban.model.TaskStatus

class BoardState(val scope: CoroutineScope, val project: KanbanProject) {

    private val totalTasks: MutableList<KanbanTask> = project.tasks

    val totalTaskCount by derivedStateOf { totalTasks.size }

    val todoCardList: List<KanbanTask> by derivedStateOf { totalTasks.filter { task -> task.status == TaskStatus.TO_DO } }

    val inProgressCardList: List<KanbanTask> by derivedStateOf { totalTasks.filter { task -> task.status == TaskStatus.IN_PROGRESS } }

    val doneCardList: List<KanbanTask> by derivedStateOf { totalTasks.filter { task -> task.status == TaskStatus.DONE } }

    val progress by derivedStateOf {
        if (totalTasks.isEmpty()) 0.0 else doneCardList.size.toDouble() / totalTasks.size.toDouble()
    }

    val showDialog = mutableStateOf(false)
    val snackbarHostState = SnackbarHostState()

    var draggedTask by mutableStateOf<KanbanTask?>(null)
    var currentDragPosition by mutableStateOf<Offset?>(null)
    val columnBounds = mutableStateMapOf<TaskStatus, Rect>()

    fun distributeTask(task: KanbanTask) {
        totalTasks.add(task)
    }

    fun addTask(task: KanbanTask) {
        distributeTask(task)

        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar("새로운 태스크가 추가되었습니다.")
        }
    }

    fun changeStatus(
        task: KanbanTask,
        status: TaskStatus,
        idx: Int,
    ) {

        totalTasks[idx] = task.copy(status = status)

        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar("태스크가 이동되었습니다.")
        }
    }

    fun totalTasksGetter(): MutableList<KanbanTask> {
        return totalTasks
    }
}
