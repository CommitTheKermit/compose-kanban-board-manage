package woowacourse.kanban.board.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.board.model.KanbanProject

@Composable
fun KanbanSidebar(
    projects: List<KanbanProject>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .width(256.dp)
            .fillMaxHeight(),
    ) {
        Column(modifier.padding(24.dp)) {
            Text("프로젝트", fontSize = 18.sp, fontWeight = FontWeight.W600, color = Color(0xff101828))
            Spacer(modifier.height(4.dp))
            Text("4주차 미션 보드", fontSize = 14.sp, fontWeight = FontWeight.W400, color = Color(0xff6a7282))
        }
        HorizontalDivider()
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.padding(16.dp),
        ) {
            items(projects.size) { index ->
                ProjectItem(projects[index].title, isSelected = false)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun KanbanSidebarPreview() {
    KanbanSidebar(
        projects = listOf(
            KanbanProject(
                inputTasks = mutableListOf(),
                title = "Compose1",
            ),
            KanbanProject(
                inputTasks = mutableListOf(),
                title = "Compose2",
            ),
        ),
    )
}
