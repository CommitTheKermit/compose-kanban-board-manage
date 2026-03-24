package woowacourse.kanban.board.model

import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
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
        val state = BoardState(backgroundScope, project = project)

        state.addTask(
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
}
