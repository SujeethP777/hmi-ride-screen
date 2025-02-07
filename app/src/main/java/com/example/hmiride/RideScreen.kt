package com.example.hmiride

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun RideScreen(
    isMotorOn: Boolean,
    onMotorToggle: (Boolean) -> Unit
) {
    var targetSpeed by remember { mutableStateOf(0) }
    var distanceCovered by remember { mutableStateOf(0f) }

    val speed by animateIntAsState(
        targetValue = targetSpeed,
        animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing), label = ""
    )
    var speedFeedbackMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(speed) {
        if (speed >= 90) {
            speedFeedbackMessage = "Speed Limit Exceeded! Slow Down!"
        } else {
            speedFeedbackMessage = null
        }
    }

    LaunchedEffect(isMotorOn) {
        if (isMotorOn) {
            while (true) {
                targetSpeed = 100
                delay(2000)
                targetSpeed = 0
                delay(2000)
            }
        } else {
            targetSpeed = 0
        }
    }


    var leftIndicatorOn by remember { mutableStateOf(false) }
    var rightIndicatorOn by remember { mutableStateOf(false) }

    val gradientBackground by remember {
        derivedStateOf {
            when (speed) {
                in 1..10 -> Brush.verticalGradient(
                    colors = listOf(Color(0xFFE0F8E0), Color(0xFFB0FFB0))
                )

                in 11..20 -> Brush.verticalGradient(
                    colors = listOf(Color(0xFFB0FFB0), Color(0xFF80FF80))
                )

                in 21..30 -> Brush.verticalGradient(
                    colors = listOf(Color(0xFF80FF80), Color(0xFF66CC66))
                )

                in 31..40 -> Brush.verticalGradient(
                    colors = listOf(Color(0xFF66CC66), Color(0xFF4CAF50))
                )

                in 41..50 -> Brush.verticalGradient(
                    colors = listOf(Color(0xFF4CAF50), Color(0xFF388E3C))
                )

                in 51..60 -> Brush.verticalGradient(
                    colors = listOf(Color(0xFF388E3C), Color(0xFF006400))
                )

                in 61..70 -> Brush.verticalGradient(
                    colors = listOf(Color(0xFFFFFF00), Color(0xFFFFE600))
                )

                in 71..80 -> Brush.verticalGradient(
                    colors = listOf(Color(0xFFFFE600), Color(0xFFFFD700))
                )

                in 81..90 -> Brush.verticalGradient(
                    colors = listOf(Color(0xFFFF6347), Color(0xFFFF4500))
                )

                in 91..100 -> Brush.verticalGradient(
                    colors = listOf(Color(0xFFB22222), Color(0xFF8B0000))
                )

                else -> Brush.verticalGradient(
                    colors = listOf(Color(0xFFE0F8E0), Color(0xFFB0FFB0))
                )
            }
        }
    }

    val totalDistance = 10f
    LaunchedEffect(isMotorOn) {
        while (isMotorOn) {
            val elapsedTime = 1f / 3600f
            distanceCovered += speed * elapsedTime
            delay(1000)
        }
    }

    var milestoneReached by remember { mutableStateOf(false) }
    if (distanceCovered >= totalDistance && !milestoneReached) {
        milestoneReached = true
    }

    val remainingDistance = totalDistance - distanceCovered

    val calculatedEta = if (speed > 0) {
        val etaInHours = remainingDistance / speed
        val etaInMinutes = (etaInHours * 60).toInt()
        val hours = etaInMinutes / 60
        val minutes = etaInMinutes % 60
        if (hours > 0) "$hours hours $minutes minutes" else "$minutes minutes"
    } else {
        "N/A"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            MotorStatus(isMotorOn = isMotorOn, onToggle = onMotorToggle)
        }

        AnimatedVisibility(
            visible = isMotorOn,
            enter = fadeIn(animationSpec = tween(800)) + slideInVertically()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(gradientBackground)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "$speed",
                        fontSize = 100.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.digital_7)),
                        color = Color.Black
                    )
                    Text(
                        text = "km/h",
                        fontSize = 24.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }

        speedFeedbackMessage?.let {
            Text(
                text = it,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        // Ride Progress Bar - Animated
        RideProgressBar(totalDistance = totalDistance, distanceCovered = distanceCovered)

        // Milestone Message
        if (milestoneReached) {
            Text(
                text = "Milestone Reached: 10 km!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Blue,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        // Estimated Time of Arrival (ETA)
        ETA(eta = calculatedEta)

        // Indicator Controls (Left & Right Blinkers)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IndicatorBlinking(
                isOn = isMotorOn && leftIndicatorOn,
                onClick = {
                    if (isMotorOn) {
                        leftIndicatorOn = !leftIndicatorOn
                        if (leftIndicatorOn) rightIndicatorOn = false
                    }
                },
                isLeft = true
            )
            IndicatorBlinking(
                isOn = isMotorOn && rightIndicatorOn,
                onClick = {
                    if (isMotorOn) {
                        rightIndicatorOn = !rightIndicatorOn
                        if (rightIndicatorOn) leftIndicatorOn = false
                    }
                },
                isLeft = false
            )
        }
    }
}