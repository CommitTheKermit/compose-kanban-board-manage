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
import woowacourse.kanban.board.model.BoardState
import woowacourse.kanban.board.model.KanbanProject
import woowacourse.kanban.create.ui.TaskCreateDialog
import woowacourse.kanban.model.Assignee
import woowacourse.kanban.model.KanbanTask
import woowacourse.kanban.model.Nickname
import woowacourse.kanban.model.TaskStatus

private fun TaskStatus.tasks(state: BoardState): List<KanbanTask> {
    return when (this) {
        TaskStatus.TO_DO -> state.todoCardList
        TaskStatus.IN_PROGRESS -> state.inProgressCardList
        TaskStatus.DONE -> state.doneCardList
    }
}

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
                TaskStatus.entries.forEach { status ->
                    StatusCardList(
                        tasks = status.tasks(state),
                        status = status,
                        modifier = Modifier.weight(1f),
                        getIsDropTarget = {
                            currentDragPosition?.let { columnBounds[status]?.contains(it) }
                                ?: false
                        },
                        onBoundsChanged = { rect -> columnBounds[status] = rect },
                        onTaskDragStart = { task ->
                            draggedTask = task
                        },
                        onTaskDragChange = { pos -> currentDragPosition = pos },
                        onTaskDragEnd = {
                            val dropPosition = currentDragPosition
                                ?: return@StatusCardList
                            val targetStatus = columnBounds.entries
                                .firstOrNull { (_, rect) -> rect.contains(dropPosition) }?.key

                            draggedTask?.let { task ->
                                if (targetStatus != null && task.status != targetStatus) {
                                    val idx = state.totalTasksGetter().indexOfFirst { it.data.id == task.data.id }
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
