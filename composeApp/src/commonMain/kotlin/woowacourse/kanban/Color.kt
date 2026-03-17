package woowacourse.kanban

import androidx.compose.ui.graphics.Color

object Colors {
    // 배경색
    val SurfaceLight = Color(0xFFF3F4F6)
    val SurfaceDark = Color(0xFF322F35)
    val OnSurfaceDark = Color(0xFFF5EEF7)

    // 중릭색
    val NeutralGray = Color(0xFF888888)
    val OnNeutral = Color.White

    // 텍스트색상
    val PrimaryText = Color(0xFF364153)
    val PrimarySubText = Color(0xFF101828)
    val ContentText = Color(0xFF4A5565)

    // 텍스트 필드 색상
    val TextFieldBorder = Color(0xFF79747E)
    val TextFieldError = Color(0xFFB3261E)
    val TextFieldPlaceholder = Color(0xFFAAAAAA)
    val TextFieldHint = Color(0xFF49454F)

    // 액션 색상
    val ActionPrimary = Color(0xFF4F39F6)
    val ActionPrimaryDisabled = Color(0xFFA7A4BC)

    // 아이콘 색상
    val IconSecondary = Color(0xFF6A7282)
    val IconTertiary = Color(0xFF838383)

    // 보더
    val PrimaryBorder = Color(0xFFE5E7EB)

    // 상태 선택
    val StatusBorderSelected = Color(0xFF1447E6)
    val StatusBgSelected = Color(0xFFEFF6FF)
    val StatusTextSelected = Color(0xFF1447E6)
    val SecondarySelectedBorder = Color(0xFF615FFF)
    val SecondarySelectedBg = Color(0xFFEEF2FF)

    // 칸반 보드 리스트 - 타이틀 색상
    val StatusBgToDo = Color(0xFF155DFC)
    val StatusBgInProgress = Color(0xFFE17100)
    val StatusBgDone = Color(0xFF00A63E)

    // 칸반 보드 리스트 - 배경 색상
    val StatusListBgToDo = Color(0xFFEFF6FF)
    val StatusListBgInProgress = Color(0xFFFFFBEB)
    val StatusListBgDone = Color(0xFFF0FDF4)

    // 칸반 보드 리스트 - 테두리 색상
    val StatusListBorderToDo = Color(0xFFBEDBFF)
    val StatusListBorderInProgress = Color(0xFFFEE685)
    val StatusListBorderDone = Color(0xFFB9F8CF)
}
