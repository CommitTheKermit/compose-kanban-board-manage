package woowacourse.kanban.board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import woowacourse.kanban.Colors
import woowacourse.kanban.board.ui.KanbanPage

@Composable
fun App() {
    KanbanPage(
        modifier = Modifier.size(
            height = 800.dp,
            width = 1300.dp,
        )
            .background(Colors.Surface),
    )
}
