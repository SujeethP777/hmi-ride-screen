package com.example.hmiride

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun IndicatorBlinking(isOn: Boolean, onClick: () -> Unit, isLeft: Boolean) {
    val transition = rememberInfiniteTransition(label = "")

    val alpha by if (isOn) {
        transition.animateFloat(
            initialValue = 1f,
            targetValue = 0f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 500, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ), label = ""
        )
    } else {
        mutableStateOf(1f)
    }

    Icon(
        imageVector = if (isLeft) Icons.Default.ArrowBack else Icons.Default.ArrowForward,
        contentDescription = if (isLeft) "Left Indicator" else "Right Indicator",
        tint = Color.Yellow.copy(alpha = if (isOn) alpha else 1f),
        modifier = Modifier
            .size(48.dp)
            .clickable { onClick() }
    )
}