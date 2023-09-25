package ir.dunijet.teamgit.ui.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ir.dunijet.teamgit.ui.theme.cBorder
import ir.dunijet.teamgit.ui.theme.radius2

@Composable
fun MainButton(modifier: Modifier, src: Int, onButtonClicked: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .size(41.dp)
            .border(BorderStroke((1.7).dp, SolidColor(cBorder)), radius2)
    ) {

        Icon(
            modifier = Modifier
                .padding(10.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    onButtonClicked.invoke()
                },
            painter = painterResource(id = src),
            contentDescription = "Navigation icon"
        )

    }

}