package ir.dunijet.teamgit.util

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FadeInOutWidget(
    visible: Boolean,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        content()
    }
}

@Composable
fun ScaleAnimation(
    working :Boolean = true,
    scale: Float,
    duration: Int = 400,
    onFinished :() -> Unit ,
    content: @Composable () -> Unit
) {
    Log.v("testLog" , scale.toString())
    val animatedScale by animateFloatAsState(
        targetValue = scale,
        animationSpec = tween(durationMillis = duration),
        label = "",
        finishedListener = {

        }
    )

    Box(
        modifier = if(working) Modifier
            .graphicsLayer(
                scaleX = animatedScale,
                scaleY = animatedScale
            ) else Modifier
    ) {
        content()
    }
}


