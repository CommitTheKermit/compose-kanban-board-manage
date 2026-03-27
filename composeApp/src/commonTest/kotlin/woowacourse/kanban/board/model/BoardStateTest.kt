package woowacourse.kanban.board.model

import androidx.compose.material3.SnackbarHostState
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest
import woowacourse.kanban.board.ui.stateholder.BoardState
import woowacourse.kanban.board.ui.stateholder.KanbanProjectState
import woowacourse.kanban.model.BoardData
import woowacourse.kanban.model.KanbanTask
import woowacourse.kanban.model.Nickname
import woowacourse.kanban.model.Tags
import woowacourse.kanban.model.TaskStatus
import woowacourse.kanban.model.Title

class BoardStateTest {
    @Test
    fun `전체 태스크 중 Done 상태의 비율로 완료율을 계산해야 한다`() = runTest {
        // given : Done 상태 태스크 2개, To Do 상태 태스크 1개, In Progress 상태 태스크 1개
        val tasks = listOf(
            KanbanTask(
                data = BoardData(
                    title = Title("제목"),
                    content = "내용",
                    tags = Tags(),
                    nickname = Nickname("아오"),
                ),
                status = TaskStatus.DONE,
            ),
            KanbanTask(
                data = BoardData(
                    title = Title("제목"),
                    content = "내용",
                    tags = Tags(),
                    nickname = Nickname("아오"),
                ),
                status = TaskStatus.DONE,
            ),
            KanbanTask(
                data = BoardData(
                    title = Title("제목"),
                    content = "내용",
                    tags = Tags(),
                    nickname = Nickname("아오"),
                ),
                status = TaskStatus.TO_DO,
            ),
            KanbanTask(
                data = BoardData(
                    title = Title("제목"),
                    content = "내용",
                    tags = Tags(),
                    nickname = Nickname("아오"),
                ),
                status = TaskStatus.IN_PROGRESS,
            ),
        )

        val projectState = KanbanProjectState(
            tasks,
        )
        val state = BoardState(
            scope = backgroundScope,
            initTasks = projectState.tasks,
            snackBarHostState = SnackbarHostState(),
        )

        // when : 완료율을 계산하면
        val result: Double = state.progress

        // then : 완료율은 50%이어야 한다.
        assertEquals(
            0.5,
            result,
        )
    }

    @Test
    fun `태스크가 상태에 맞는 컬럼에 분류되어야 한다`() = runTest {
        // given : Done 상태 태스크 1개, To Do 상태 태스크 1개, In Progress 상태 태스크 1개가 주어진다
        val tasks = listOf(
            KanbanTask(
                data = BoardData(
                    title = Title("제목"),
                    content = "내용",
                    tags = Tags(),
                    nickname = Nickname("아오"),
                ),
                status = TaskStatus.DONE,
            ),
            KanbanTask(
                data = BoardData(
                    title = Title("제목"),
                    content = "내용",
                    tags = Tags(),
                    nickname = Nickname("아오"),
                ),
                status = TaskStatus.TO_DO,
            ),
            KanbanTask(
                data = BoardData(
                    title = Title("제목"),
                    content = "내용",
                    tags = Tags(),
                    nickname = Nickname("아오"),
                ),
                status = TaskStatus.IN_PROGRESS,
            ),
        )

        // when : 컬럼들을 분류하면 TO_DO, IN_PROGRESS, DONE 상태 별로 리스트에 배치되어야 한다.

        val projectState = KanbanProjectState(
            initTasks = tasks,
        )
        val state = BoardState(
            scope = backgroundScope,
            initTasks = projectState.tasks,
            snackBarHostState = SnackbarHostState(),
        )

        // then : TO_DO, IN_PROGRESS, DONE 카드 리스트 각각 하나씩 존재해야 한다
        assertEquals(
            1,
            state.todoCardList.size,
        )
        assertEquals(
            1,
            state.inProgressCardList.size,
        )
        assertEquals(
            1,
            state.doneCardList.size,
        )
    }
}
