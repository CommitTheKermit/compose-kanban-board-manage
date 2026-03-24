package woowacourse.kanban.board.model

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import woowacourse.kanban.model.KanbanTask
import woowacourse.kanban.model.TaskStatus

class BoardState(val scope: CoroutineScope, initTasks: List<KanbanTask> = emptyList(), val project: KanbanProject) {

    private val totalTasks: MutableList<KanbanTask>
    val totalTaskCount by derivedStateOf { totalTasks.size }

    val todoCardList: List<KanbanTask> by derivedStateOf { totalTasks.filter { task -> task.status == TaskStatus.TO_DO } }

    val inProgressCardList: List<KanbanTask> by derivedStateOf { totalTasks.filter { task -> task.status == TaskStatus.IN_PROGRESS } }

    val doneCardList: List<KanbanTask> by derivedStateOf { totalTasks.filter { task -> task.status == TaskStatus.DONE } }

    val progress by derivedStateOf {
        if (totalTasks.isEmpty()) 0.0 else doneCardList.size.toDouble() / totalTasks.size.toDouble()
    }

    val showDialog = mutableStateOf(false)
    val snackbarHostState = SnackbarHostState()

    init {
        initTasks.forEach { task -> distributeTask(task) }
        totalTasks = project.tasks
    }

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
    ): KanbanTask {
        val newTask = KanbanTask(task.data, status)

        return newTask
    }
}
