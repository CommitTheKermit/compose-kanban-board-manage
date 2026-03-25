package woowacourse.kanban.board.ui

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test

@OptIn(ExperimentalTestApi::class)
class KanbanProjectUiTest {

    @Test
    fun `프로젝트를 선택하면 프로젝트에 저장되어 있는 태스크들이 표시되어야 한다`() = runComposeUiTest {
        // given : 칸반 페이지가 주어지고 프로젝트 리스트는 칸반 페이지 내부에 MockData로 설정 되어 있다
        setContent {
            KanbanPage()
        }

        // when : 프로젝트 버튼을 눌렀을 때
        onNodeWithText("Compose2").performClick()
        waitForIdle()

        // then : 프로젝트에 저장되어 있는 태스크들이 표시되어야 한다
        onNodeWithTag("In Progress")
            .onChildren()
            .assertCountEquals(3)
    }
}
