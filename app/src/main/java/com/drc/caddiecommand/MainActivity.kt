
package com.drc.caddiecommand

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Navy = Color(0xFF101B30)
private val Gold = Color(0xFFD5B56D)
private val White = Color(0xFFF5F6F8)

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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Navy)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "CADDIE COMMAND",
            color = Gold,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(12.dp))

        Text(
            text = "YOUR COURSE. YOUR STRATEGY. YOUR EDGE.",
            color = White,
            fontSize = 13.sp,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(48.dp))

        if (!entered) {
            Button(
                onClick = { entered = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Gold,
                    contentColor = Navy
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("ENTER", fontWeight = FontWeight.Bold)
            }
        } else {
            Text(
                text = "CADDIE COMMAND",
                color = Gold,
                fontSize = 24.sp
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Caddie systems being prepared",
                color = White,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))

            Button(onClick = { entered = false }) {
                Text("BACK")
            }
        }
    }
}
