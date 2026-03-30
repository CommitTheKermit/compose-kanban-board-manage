package woowacourse.kanban.board.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import woowacourse.kanban.Colors

@Composable
fun KanbanSnackBar(
    data: SnackbarData,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color = Colors.SurfaceDark),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth(0.25f)
                .padding(horizontal = 16.dp, vertical = 14.dp),
        ) {
            Text(
                text = data.visuals.message,
                color = Colors.OnSurfaceDark,
                fontWeight = FontWeight.W400,
                fontSize = 14.sp,
            )
            Icon(
                Icons.Default.Close,
                contentDescription = "스낵바 닫기",
                tint = Colors.OnSurfaceDark,
                modifier = Modifier.clickable(
                    onClick = {
                        data.dismiss()
                    },
                ),
            )
        }
    }
}
