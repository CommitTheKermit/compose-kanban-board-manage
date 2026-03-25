package woowacourse.kanban.board.model

import androidx.compose.material3.SnackbarHostState
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import woowacourse.kanban.board.ui.stateholder.BoardState
import woowacourse.kanban.model.BoardData
import woowacourse.kanban.model.KanbanTask
import woowacourse.kanban.model.Nickname
import woowacourse.kanban.model.Tags
import woowacourse.kanban.model.TaskStatus
import woowacourse.kanban.model.Title

class KanbanProjectTest {
    @Test
    fun `새 태스크를 생성했을 때 현재 프로젝트에 삽입되어야 한다`() = runTest {
        val project = KanbanProject(mutableListOf())

        val state = BoardState(
            scope = backgroundScope,
            project = project,
            snackBarHostState = SnackbarHostState(),
        )
        val action =
            TaskManager(
                project.tasks,
            )

        action.addTask(
            KanbanTask(
                data = BoardData(
                    title = Title("제목"),
                    content = "내용",
                    tags = Tags(),
                    nickname = Nickname("아오"),
                ),
                status = TaskStatus.DONE,
            ),
        )

        assertEquals(1, project.tasks.size)
    }

    @Test
    fun `태스크의 상태를 변경 할 수 있어야 한다`() = runTest {
        var task = KanbanTask(
            data = BoardData(
                title = Title("제목"),
                content = "내용",
                tags = Tags(),
                nickname = Nickname("아오"),
            ),
            status = TaskStatus.IN_PROGRESS,
        )

        val project = KanbanProject(mutableListOf(task))

        val state = BoardState(
            scope = backgroundScope,
            project = project,
            snackBarHostState = SnackbarHostState(),
        )
        val action =
            TaskManager(
                project.tasks,
            )

        action.changeStatus(task, TaskStatus.DONE, idx = 0)

        assertEquals(TaskStatus.DONE, state.totalTasksGetter().first().status)
    }
}
