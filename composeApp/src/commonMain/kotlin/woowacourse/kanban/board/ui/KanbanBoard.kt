package woowacourse.kanban.board.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import woowacourse.kanban.board.model.KanbanProject
import woowacourse.kanban.board.model.TaskManager
import woowacourse.kanban.board.ui.constant.MockData
import woowacourse.kanban.board.ui.constant.SnackBarText
import woowacourse.kanban.board.ui.stateholder.BoardState
import woowacourse.kanban.create.ui.TaskCreateDialog
import woowacourse.kanban.model.KanbanTask
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
    project: KanbanProject,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
) {
    val scope = rememberCoroutineScope()

    val state = remember(project) { BoardState(scope, project, snackbarHostState) }
    val action = remember(state) { TaskManager(project.tasks) }

    var draggedTask by remember { mutableStateOf<KanbanTask?>(null) }
    var currentDragPosition by remember { mutableStateOf<Offset?>(null) }
    val columnBounds = remember { mutableStateMapOf<TaskStatus, Rect>() }

    Column(modifier = modifier) {
        KanbanBoardHeader(
            progress = state.progress,
            doneTaskCount = state.doneCardList.size,
            totalTaskCount = state.totalTaskCount,
            onClick = { state.showDialog.value = true },
            headerTitle = project.title,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(1f),
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
                                    action.changeStatus(
                                        task = state.totalTasksGetter()[idx],
                                        status = targetStatus,
                                        idx = idx,
                                    )
                                    state.showKanbanSnackBar(SnackBarText.EDIT_TASK)
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
            onCreateTask = { task ->
                action.addTask(task)
                state.showKanbanSnackBar(SnackBarText.CREATE_TASK)
            },
            assignees = MockData.ASSIGNEES,
            modifier = Modifier,
        )
    }
}

@Preview(widthDp = 1500, heightDp = 800)
@Composable
fun KanbanBoardPreview() {
    KanbanBoard(KanbanProject(mutableListOf()))
}
