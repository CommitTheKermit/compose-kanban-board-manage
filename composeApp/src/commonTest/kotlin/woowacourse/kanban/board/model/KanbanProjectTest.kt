package woowacourse.kanban.board.model

import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import woowacourse.kanban.board.ui.stateholder.KanbanProjectState
import woowacourse.kanban.commonmodel.BoardData
import woowacourse.kanban.commonmodel.KanbanTask
import woowacourse.kanban.commonmodel.Nickname
import woowacourse.kanban.commonmodel.Tags
import woowacourse.kanban.commonmodel.TaskStatus
import woowacourse.kanban.commonmodel.Title

class KanbanProjectTest {
    @Test
    fun `새 태스크를 생성했을 때 현재 프로젝트에 삽입되어야 한다`() = runTest {
        val project = KanbanProject(mutableListOf())

        project.addTask(
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

        assertEquals(1, project.getTasks().size)
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

        val state = KanbanProjectState(mutableListOf(task))

        state.changeStatus(TaskStatus.DONE, idx = 0)

        assertEquals(TaskStatus.DONE, state.tasks.first().status)
    }
}
