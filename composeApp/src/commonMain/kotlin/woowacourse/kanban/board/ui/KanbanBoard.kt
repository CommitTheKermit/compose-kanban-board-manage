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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.collections.listOf
import woowacourse.kanban.board.model.BoardState
import woowacourse.kanban.create.ui.TaskCreateDialog
import woowacourse.kanban.model.Assignee
import woowacourse.kanban.model.KanbanTask
import woowacourse.kanban.model.Nickname
import woowacourse.kanban.model.TaskStatus

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun KanbanBoard(
    modifier: Modifier = Modifier,
    initTasks: List<KanbanTask> = emptyList(),
) {
    val scope = rememberCoroutineScope()
    val state = remember { BoardState(scope, initTasks) }

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
                )
                StatusCardList(
                    tasks = state.inProgressCardList,
                    status = TaskStatus.IN_PROGRESS,
                    modifier = Modifier.weight(1f),
                )
                StatusCardList(
                    tasks = state.doneCardList,
                    status = TaskStatus.DONE,
                    modifier = Modifier.weight(1f),
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
