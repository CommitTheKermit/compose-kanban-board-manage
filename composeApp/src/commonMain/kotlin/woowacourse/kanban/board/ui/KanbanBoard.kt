package woowacourse.kanban.board.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.dp
import kotlin.collections.listOf
import woowacourse.kanban.board.model.BoardState
import woowacourse.kanban.board.model.KanbanProject
import woowacourse.kanban.create.ui.TaskCreateDialog
import woowacourse.kanban.model.Assignee
import woowacourse.kanban.model.KanbanTask
import woowacourse.kanban.model.Nickname
import woowacourse.kanban.model.TaskStatus

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun KanbanBoard(
    modifier: Modifier = Modifier,
    project: KanbanProject,
) {
    val scope = rememberCoroutineScope()
    val state = remember { BoardState(scope, project) }

    var draggedTask by remember { mutableStateOf<KanbanTask?>(null) }
    var currentDragPosition by remember { mutableStateOf<Offset?>(null) }
    val columnBounds = remember { mutableStateMapOf<TaskStatus, Rect>() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(state.snackbarHostState, modifier = Modifier.offset(y = (-50).dp)) { data ->
                KanbanSnackBar(data)
            }
        },
        modifier = modifier,
    ) { innerPadding ->
        Column(modifier = Modifier.padding(paddingValues = innerPadding)) {
            KanbanBoardHeader(
                progress = state.progress,
                doneTaskCount = state.doneCardList.size,
                totalTaskCount = state.totalTaskCount,
                onClick = { state.showDialog.value = true },
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth(0.75f),
            ) {
                StatusCardList(
                    tasks = state.todoCardList,
                    status = TaskStatus.TO_DO,
                    modifier = Modifier.weight(1f),
                    getIsDropTarget = {
                        currentDragPosition?.let { columnBounds[TaskStatus.TO_DO]?.contains(it) }
                            ?: false
                    },
                    onBoundsChanged = { rect -> columnBounds[TaskStatus.TO_DO] = rect },
                    onTaskDragStart = { task -> draggedTask = task },
                    onTaskDragChange = { pos -> currentDragPosition = pos },
                    onTaskDragEnd = {
                        val dropPosition = currentDragPosition
                            ?: return@StatusCardList
                        val targetStatus = columnBounds.entries
                            .firstOrNull { (_, rect) -> rect.contains(dropPosition) }?.key

                        draggedTask?.let { task ->
                            if (targetStatus != null && task.status != targetStatus) {
                                val idx = state.todoCardList.indexOfFirst { it.data.title == task.data.title }
                                if (idx != -1) {
                                    state.totalTasksGetter()[idx] = state.todoCardList[idx].copy(status = targetStatus)
                                    print("")
                                }
                            }
                        }
                        currentDragPosition = null
                        draggedTask = null
                    },
                    onTaskDragCancel = {
                        currentDragPosition = null
                        draggedTask = null
                    },
                )
                StatusCardList(
                    tasks = state.inProgressCardList,
                    status = TaskStatus.IN_PROGRESS,
                    modifier = Modifier.weight(1f),
                    getIsDropTarget = {
                        currentDragPosition?.let { columnBounds[TaskStatus.IN_PROGRESS]?.contains(it) }
                            ?: false
                    },
                    onBoundsChanged = { rect -> columnBounds[TaskStatus.IN_PROGRESS] = rect },
                    onTaskDragStart = { task -> draggedTask = task },
                    onTaskDragChange = { pos -> currentDragPosition = pos },
                    onTaskDragEnd = {
                        val dropPosition = currentDragPosition
                            ?: return@StatusCardList
                        val targetStatus = columnBounds.entries
                            .firstOrNull { (_, rect) -> rect.contains(dropPosition) }?.key

                        draggedTask?.let { task ->
                            if (targetStatus != null && task.status != targetStatus) {
                                val idx = state.totalTasksGetter().indexOfFirst { it.data.title == task.data.title }
                                if (idx != -1) {
                                    state.totalTasksGetter()[idx] = state.totalTasksGetter()[idx].copy(status = targetStatus)
                                    print("")
                                }
                            }
                        }
                        currentDragPosition = null
                        draggedTask = null
                    },
                    onTaskDragCancel = {
                        currentDragPosition = null
                        draggedTask = null
                    },
                )
                StatusCardList(
                    tasks = state.doneCardList,
                    status = TaskStatus.DONE,
                    modifier = Modifier.weight(1f),
                    getIsDropTarget = {
                        currentDragPosition?.let { columnBounds[TaskStatus.DONE]?.contains(it) }
                            ?: false
                    },
                    onBoundsChanged = { rect -> columnBounds[TaskStatus.DONE] = rect },
                    onTaskDragStart = { task -> draggedTask = task },
                    onTaskDragChange = { pos -> currentDragPosition = pos },
                    onTaskDragEnd = {
                        val dropPosition = currentDragPosition
                            ?: return@StatusCardList
                        val targetStatus = columnBounds.entries
                            .firstOrNull { (_, rect) -> rect.contains(dropPosition) }?.key

                        draggedTask?.let { task ->
                            if (targetStatus != null && task.status != targetStatus) {
                                val idx = state.totalTasksGetter().indexOfFirst { it.data.title == task.data.title }
                                if (idx != -1) {
                                    state.totalTasksGetter()[idx] = state.totalTasksGetter()[idx].copy(status = targetStatus)
                                    print("")
                                }
                            }
                        }
                        currentDragPosition = null
                        draggedTask = null
                    },
                    onTaskDragCancel = {
                        currentDragPosition = null
                        draggedTask = null
                    },
                )
            }
        }
    }

    if (state.showDialog.value) {
        TaskCreateDialog(
            onDismiss = { state.showDialog.value = false },
            onCreateTask = { task -> state.addTask(task) },
            assignees = listOf(
                Assignee(
                    Nickname(
                        "다이노",
                    ),
                ),
                Assignee(
                    Nickname(
                        "페임스",
                    ),
                ),
            ),
            modifier = Modifier,

        )
    }
}
