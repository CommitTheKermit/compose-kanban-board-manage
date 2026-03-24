package woowacourse.kanban.board.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProjectItem(
    projectTitle: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {

    val bgColor = if (isSelected) Color(0xffEEf2ff) else Color.Transparent
    val textColor = if (isSelected) Color(0xff432dd7) else Color(0xff364153)
    Text(
        projectTitle,
        fontSize = 16.sp,
        color = textColor,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(bgColor)
            .padding(vertical = 12.dp, horizontal = 15.dp),
    )
}

@Preview
@Composable
fun ProjectItemPreview(modifier: Modifier = Modifier) {
    ProjectItem("Compose1", isSelected = false)
}
