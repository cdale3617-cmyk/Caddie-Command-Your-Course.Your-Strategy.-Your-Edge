
package com.drc.caddiecommand

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Navy = Color(0xFF101B30)
private val Gold = Color(0xFFD5B56D)
private val White = Color(0xFFF5F6F8)
private val Green = Color(0xFF248B61)
private val Red = Color(0xFFD84C50)
private val Yellow = Color(0xFFF5D44B)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CaddieCommandApp()
            }
        }
    }
}

@Composable
fun CaddieCommandApp() {
    var entered by remember { mutableStateOf(false) }
    var hole by remember { mutableIntStateOf(1) }
    var score by remember { mutableIntStateOf(5) }
    var route by remember { mutableStateOf("SAFE") }
    var tab by remember { mutableStateOf("HOLE") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Navy)
            .padding(12.dp)
    ) {
        if (!entered) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "CADDIE COMMAND",
                    color = Gold,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    "YOUR COURSE. YOUR STRATEGY. YOUR EDGE.",
                    color = White,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(48.dp))
                GoldButton("ENTER") { entered = true }
            }
        } else {
            Text(
                "CADDIE COMMAND",
                color = Gold,
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                "YOUR COURSE. YOUR STRATEGY. YOUR EDGE.",
                color = White,
                fontSize = 10.sp
            )

            Spacer(Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                listOf("HOLE", "CADDIE", "SCORE", "BAG").forEach {
                    Box(Modifier.weight(1f)) {
                        SmallButton(it, tab == it) { tab = it }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            when (tab) {
                "HOLE" -> {
                    Text(
                        "HOLE $hole  |  PAR 5",
                        color = Gold,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        "DEMONSTRATION HOLE - NOT GPS DATA",
                        color = White,
                        fontSize = 11.sp
                    )

                    Spacer(Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1.7f)
                                .fillMaxHeight()
                                .border(
                                    1.dp,
                                    Gold,
                                    RoundedCornerShape(12.dp)
                                )
                                .background(
                                    Color(0xFF18382D),
                                    RoundedCornerShape(12.dp)
                                )
                        ) {
                            HoleDrawing(route)
                        }

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            InfoPanel("GPS", "NOT CONNECTED")
                            InfoPanel("WIND", "NOT CONNECTED")
                            InfoPanel("FRONT", "-- m")
                            InfoPanel("CENTRE", "-- m")
                            InfoPanel("BACK", "-- m")
                            InfoPanel("CLUB", "NOT SELECTED")
                        }
                    }

                    Spacer(Modifier.height(10.dp))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(Modifier.weight(1f)) {
                            RouteButton(
                                "SAFE",
                                Yellow,
                                route == "SAFE"
                            ) { route = "SAFE" }
                        }
                        Box(Modifier.weight(1f)) {
                            RouteButton(
                                "ATTACK",
                                Red,
                                route == "ATTACK"
                            ) { route = "ATTACK" }
                        }
                    }

                    Spacer(Modifier.height(10.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        SmallButton("PREV", false) {
                            if (hole > 1) hole--
                        }
                        Text(
                            "$hole / 18",
                            color = Gold,
                            fontWeight = FontWeight.Bold
                        )
                        SmallButton("NEXT", false) {
                            if (hole < 18) hole++
                        }
                    }
                }

                "CADDIE" -> {
                    SectionTitle("YOUR CADDIE")
                    Text(
                        "Your strategy assistant will provide " +
                        "club selection, wind adjustment, " +
                        "hazard awareness and shot planning " +
                        "when live course data is connected.",
                        color = White
                    )
                    Spacer(Modifier.height(16.dp))
                    InfoPanel("COURSE", "NOT SELECTED")
                    InfoPanel("DISTANCE", "AWAITING GPS")
                    InfoPanel("WIND", "AWAITING WEATHER")
                    InfoPanel("VOICE", "NOT CONNECTED")
                }

                "SCORE" -> {
                    SectionTitle("SCORECARD")
                    Text(
                        "Hole $hole",
                        color = White,
                        fontSize = 20.sp
                    )
                    Spacer(Modifier.height(20.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        SmallButton("-", false) {
                            if (score > 1) score--
                        }
                        Text(
                            "$score",
                            color = Gold,
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold
                        )
                        SmallButton("+", false) {
                            if (score < 20) score++
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Temporary score. Round saving " +
                        "will be added in a later build.",
                        color = White
                    )
                }

                "BAG" -> {
                    SectionTitle("MY BAG")
                    Text(
                        "Club distances and equipment " +
                        "management will be connected here.",
                        color = White
                    )
                }
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun HoleDrawing(route: String) {
    Canvas(Modifier.fillMaxSize().padding(10.dp)) {
        val w = size.width
        val h = size.height

        val fairway = Path().apply {
            moveTo(w * .40f, h * .94f)
            cubicTo(
                w * .08f, h * .70f,
                w * .22f, h * .48f,
                w * .36f, h * .28f
            )
            cubicTo(
                w * .52f, h * .10f,
                w * .82f, h * .20f,
                w * .70f, h * .40f
            )
            cubicTo(
                w * .60f, h * .60f,
                w * .86f, h * .78f,
                w * .60f, h * .94f
            )
            close()
        }

        drawPath(fairway, Green)

        drawCircle(
            Color(0xFF79C98C),
            radius = w * .16f,
            center = Offset(w * .53f, h * .16f)
        )

        val lineColor =
            if (route == "SAFE") Yellow else Red

        val routePath = Path().apply {
            moveTo(w * .50f, h * .90f)
            if (route == "SAFE") {
                lineTo(w * .43f, h * .65f)
                lineTo(w * .55f, h * .42f)
            } else {
                lineTo(w * .65f, h * .58f)
            }
            lineTo(w * .53f, h * .16f)
        }

        drawPath(
            routePath,
            lineColor,
            style = Stroke(width = 5f)
        )

        drawCircle(
            Gold,
            radius = 8f,
            center = Offset(w * .50f, h * .90f)
        )

        drawCircle(
            White,
            radius = 7f,
            center = Offset(w * .53f, h * .16f)
        )
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        title,
        color = Gold,
        fontSize = 23.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(Modifier.height(12.dp))
}

@Composable
private fun InfoPanel(title: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                1.dp,
                Gold,
                RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        Text(title, color = Gold, fontSize = 11.sp)
        Text(value, color = White, fontSize = 13.sp)
    }
}

@Composable
private fun GoldButton(
    title: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Gold,
            contentColor = Navy
        )
    ) {
        Text(title, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun SmallButton(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                if (selected) Gold else Color(0xFF26354E),
                RoundedCornerShape(7.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            title,
            color = if (selected) Navy else White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun RouteButton(
    title: String,
    colour: Color,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                if (selected) colour else Color(0xFF26354E),
                RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick)
            .padding(13.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            title,
            color = if (selected) Navy else White,
            fontWeight = FontWeight.Bold
        )
    }
}
