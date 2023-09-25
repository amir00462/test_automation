package ir.dunijet.teamgit.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.Px
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.TabPosition
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun String.toParagraph(size: Int): String {

    val stringBuilder = StringBuilder()
    val lines = this.split(". ")

    var currentParagraph = ""
    var lineCount = 0
    for (line in lines) {
        val trimmedLine = line.trim()
        if (trimmedLine.isNotEmpty()) {
            currentParagraph += if (currentParagraph.isNotEmpty()) {
                " $trimmedLine"
            } else {
                trimmedLine
            }
        }

        lineCount++
        if (lineCount >= size) {
            stringBuilder.append(currentParagraph).append(".\n\n")
            currentParagraph = ""
            lineCount = 0
        }
    }

    if (currentParagraph.isNotEmpty()) {
        stringBuilder.append(currentParagraph)
    }

    return stringBuilder.toString()
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.toReadableDateTime(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val dateTime = LocalDateTime.parse(this, formatter)

    val zoneId = ZoneId.systemDefault()
    val instant = dateTime.atZone(zoneId).toInstant()
    val readableDate = DateTimeFormatter.ofPattern("yyyy/M/d").format(dateTime)
    val readableTime = DateTimeFormatter.ofPattern("H:mm").format(dateTime)

    return "$readableTime ,$readableDate"
}

fun Context.showToast(str: String) {
    Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
}

@Composable
fun getCurrentOrientation(): Int {

    // 0 ->  "Portrait"
    // 1 -> "Landscape"

    val configuration = LocalConfiguration.current
    return if (configuration.screenWidthDp > configuration.screenHeightDp) {
        1
    } else {
        0
    }
}

fun Modifier.customTabIndicatorOffset(
    currentTabPosition: TabPosition,
    tabWidth: Dp,
    tabHeight: Dp,
    topCornerRadius: Dp
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "customTabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val currentTabWidth by animateDpAsState(
        targetValue = tabWidth,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing), label = ""
    )
    val indicatorOffset by animateDpAsState(
        targetValue = ((currentTabPosition.left + currentTabPosition.right - tabWidth) / 2),
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing), label = ""
    )

    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .offset(x = indicatorOffset)
        .width(currentTabWidth)
        .height(tabHeight)
        .clip( RoundedCornerShape( topStart = topCornerRadius , topEnd = topCornerRadius ) )


}


//{
//    "_id": "64b7de236a18e86d9a914dce",
//    "name": "اخبار روز تکنولوژی",
//    "__v": 0
//},
//{
//    "_id": "64b7ddee6a18e86d9a914dcc",
//    "name": "برنامه نویسی",
//    "__v": 0
//},
//{
//    "_id": "64b7dd586a18e86d9a914dca",
//    "name": "گجت های روز",
//    "__v": 0
//},
//{
//    "_id": "64b7dd3f6a18e86d9a914dc8",
//    "name": "نجوم و فضا",
//    "__v": 0
//},
//{
//    "_id": "64b7dd216a18e86d9a914dc6",
//    "name": "هوش مصنوعی",
//    "__v": 0
//},
//{
//    "_id": "64a9181c4920f108e326d08e",
//    "name": "نوشته های عمومی",
//    "__v": 0
//}
//]
