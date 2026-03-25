package woowacourse.kanban.board.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.runComposeUiTest
import kotlin.test.Test
import woowacourse.kanban.board.model.KanbanProject

@OptIn(ExperimentalTestApi::class)
class BoardUiTest {

    @Test
    fun `새 태스크 생성 버튼을 누르면 생성 다이얼로그가 열려야 한다`() = runComposeUiTest {
        // given : 새 태스크 버튼이 주어진다
        setContent {
            KanbanBoard(
                project = KanbanProject(mutableListOf()),
            )
        }

        // when : 새 태스크 버튼을 눌렀을 때
        onNodeWithText("새 태스크 생성").performClick()
        waitForIdle()
        // then : 생성 다이얼로그가 열려야 한다
        onNodeWithText("태스크 제목을 입력하세요").assertExists()
    }

    @Test
    fun `생성 다이얼로그에서 정상적인 값들을 입력 후 생성 버튼을 누르면 칸반 보드 리스트에 표시되어야 한다`() = runComposeUiTest {
        // given : 태스크 카드 정상 입력값이 주어진다
        setContent {
            KanbanBoard(project = KanbanProject(mutableListOf()))
        }

        // when : 생성 다이얼로그에서 정상적인 값을 입력 후 생성 버튼을 누를 때
        onNodeWithText("새 태스크 생성").performClick()
        waitForIdle()
        onNodeWithText("태스크 제목을 입력하세요").performTextInput("태스크제목")
        waitForIdle()
        onNodeWithText("생성").performClick()
        waitForIdle()

        // then : 칸반 보드에서 입력된 카드가 보여야 한다
        onNodeWithText("태스크제목").assertExists()
    }

    @Test
    fun `태스크 카드가 생성되고 스낵바가 출력되어야 한다`() = runComposeUiTest {
        // given : 태스크 카드 정상 입력값이 주어진다
        setContent {
            KanbanBoard(project = KanbanProject(mutableListOf()))
        }

        // when : 생성 다이얼로그에서 정상적인 값을 입력 후 생성 버튼을 누를 때
        onNodeWithText("새 태스크 생성").performClick()
        waitForIdle()
        onNodeWithText("태스크 제목을 입력하세요").performTextInput("태스크제목")
        waitForIdle()
        onNodeWithText("생성").performClick()
        waitForIdle()

        // then : 칸반 보드 하단에 스낵바가 출력되어야 한다
        onNodeWithText("새로운 태스크가 추가되었습니다.").assertExists()
    }
}
