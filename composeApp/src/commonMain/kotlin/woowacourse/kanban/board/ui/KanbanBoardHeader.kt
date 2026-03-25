package woowacourse.kanban.board.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.Colors

@Composable
fun KanbanBoardHeader(
    progress: Double,
    doneTaskCount: Int,
    totalTaskCount: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    headerTitle: String = "",
) {
    Column(
        modifier = modifier
            .border(color = Colors.PrimaryBorder, width = 1.dp)
            .padding(horizontal = 24.dp, vertical = 16.dp),
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Column {
                Text(
                    headerTitle,
                    fontSize = 24.sp,
                )
                Spacer(modifier = Modifier.height(4.5.dp))
                Text(
                    "완료율 ${(progress * 100).toInt()}% ($doneTaskCount/$totalTaskCount)",
                    fontWeight = FontWeight.W400,
                    fontSize = 14.sp,
                    color = Colors.PrimaryBorder,
                )
            }
            Button(
                onClick = {
                    onClick()
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonColors(
                    containerColor = Colors.ActionPrimary,
                    contentColor = ButtonDefaults.buttonColors().contentColor,
                    disabledContainerColor = ButtonDefaults.buttonColors().disabledContainerColor,
                    disabledContentColor = ButtonDefaults.buttonColors().disabledContentColor,
                ),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    Icon(Icons.Default.Add, contentDescription = "태스크 생성 버튼")
                    Text(
                        "새 태스크 생성",
                        fontWeight = FontWeight.W400,
                        fontSize = 16.sp,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LinearProgressIndicator(
            progress = { progress.toFloat() },
            color = Colors.ActionPrimary,
            trackColor = Colors.PrimaryBorder,
            drawStopIndicator = {},
            gapSize = (-4).dp,
            modifier = Modifier.fillMaxWidth().height(8.dp),
        )
    }
}

@Preview(backgroundColor = 0xfff9fafb)
@Composable
private fun KanbanBoardHeaderPreview() {
    KanbanBoardHeader(
        progress = 3.0 / 7.0,
        doneTaskCount = 3,
        totalTaskCount = 7,
        onClick = { },
    )
}
