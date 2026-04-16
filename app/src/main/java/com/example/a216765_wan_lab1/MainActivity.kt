package com.example.a216765_wan_lab1

import android.os.Bundle
import java.util.Calendar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment as UiAlignment

val MintLight     = Color(0xFFf8fdf9)
val MintSurface   = Color(0xFFe8f5ee)
val MintBorder    = Color(0xFFc8e6d4)
val MintGreen     = Color(0xFF3a9e6a)
val SageDark      = Color(0xFF2d6b4a)
val SageMid       = Color(0xFF4a7a60)
val SageMuted     = Color(0xFF7aaa90)
val White         = Color(0xFFFFFFFF)

// goal card colors
val GoalBlue      = Color(0xFFe8f5ee)
val GoalBlueTxt   = Color(0xFF2d6b4a)
val GoalPink      = Color(0xFFfce8f0)
val GoalPinkTxt   = Color(0xFFa0526d)
val GoalPurple    = Color(0xFFede8fc)
val GoalPurpleTxt = Color(0xFF6b52b8)
val GoalEmpty     = Color(0xFFf5f5f5)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MoodSpaceApp() }
    }
}

fun getGreeting(): String {
    val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    return when {
        hour < 12 -> "Good morning"
        hour < 17 -> "Good afternoon"
        hour < 21 -> "Good evening"
        else      -> "Good night"
    }
}

@Composable
fun MoodSpaceApp() {
    val scrollState = rememberScrollState()


    val settingsIcon: Painter = painterResource(id = R.drawable.settings_icon)
    val homeIconTop: Painter = painterResource(id = R.drawable.home_icon)
    val refreshIcon: Painter = painterResource(id = R.drawable.refresh_icon)

    val smileFaceIcon: Painter = painterResource(id = R.drawable.smile_face_icon)
    val heartIcon: Painter = painterResource(id = R.drawable.heart_icon)
    val sleepIcon: Painter = painterResource(id = R.drawable.sleep_icon)

    val homeIcon: Painter = painterResource(id = R.drawable.home_icon)
    val inboxIcon: Painter = painterResource(id = R.drawable.inbox_icon)
    val plusIcon: Painter = painterResource(id = R.drawable.plus_icon)
    val insightsIcon: Painter = painterResource(id = R.drawable.insight_icon)
    val historyIcon: Painter = painterResource(id = R.drawable.history_icon)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MintLight)
            .verticalScroll(scrollState)
    ) {

        // TOP BAR
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .padding(horizontal = 18.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left: Settings icon
            Icon(
                painter = settingsIcon,
                contentDescription = "Settings",
                modifier = Modifier.size(20.dp),
                tint = Color.Unspecified
            )

            // Middle: Home icon
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = UiAlignment.Center
            ) {
                Icon(
                    painter = homeIconTop,
                    contentDescription = "Home",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Unspecified
                )
            }

            // Right: Refresh icon
            Icon(
                painter = refreshIcon,
                contentDescription = "Refresh",
                modifier = Modifier.size(20.dp),
                tint = Color.Unspecified
            )
        }

        // GREETING
        Column(modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp)) {
            Text(
                text = "${getGreeting()}, Adlyna! 🌿",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = SageDark
            )
            Text(
                text = "How are you feeling today?",
                fontSize = 13.sp,
                color = SageMuted
            )
        }

        // STATS CARD (Mood Streak + Daily Goals)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            border = CardDefaults.outlinedCardBorder().copy(width = 0.5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Left: Mood Streak
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "MOOD STREAK",
                        fontSize = 10.sp,
                        color = SageMuted,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        "1",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = MintGreen
                    )
                    Text("day in a row", fontSize = 11.sp, color = SageMuted)
                }

                // Divider
                Box(
                    modifier = Modifier
                        .width(0.5.dp)
                        .height(70.dp)
                        .background(MintBorder)
                )

                // Right: Daily Goals
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "DAILY GOALS",
                        fontSize = 10.sp,
                        color = SageMuted,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        "33%",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = MintGreen
                    )
                    Text("1 of 3", fontSize = 11.sp, color = SageMuted)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // DAILY GOALS SECTION HEADER
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "DAILY GOALS",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = SageDark,
                letterSpacing = 0.5.sp
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(MintSurface)
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text("+ add", fontSize = 12.sp, color = MintGreen)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // GOAL CARDS GRID
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            border = CardDefaults.outlinedCardBorder().copy(width = 0.5.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                // Row 1: Mood + Gratitude
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    GoalCardImage(
                        icon = smileFaceIcon,
                        label = "Mood",
                        bgColor = GoalBlue,
                        textColor = GoalBlueTxt,
                        isDone = true,
                        modifier = Modifier.weight(1f)
                    )
                    GoalCardImage(
                        icon = heartIcon,
                        label = "Gratitude",
                        bgColor = GoalPink,
                        textColor = GoalPinkTxt,
                        isDone = false,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Row 2: Sleep + Empty (add slot)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    GoalCardImage(
                        icon = sleepIcon,
                        label = "Sleep",
                        bgColor = GoalPurple,
                        textColor = GoalPurpleTxt,
                        isDone = false,
                        modifier = Modifier.weight(1f)
                    )
                    // Empty 4th slot (max 4 goals)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(GoalEmpty)
                            .border(1.5.dp, MintBorder, RoundedCornerShape(12.dp)),
                        contentAlignment = UiAlignment.Center
                    ) {
                        Column(horizontalAlignment = UiAlignment.CenterHorizontally) {
                            Text("＋", fontSize = 24.sp, color = SageMuted)
                            Text("Add goal", fontSize = 11.sp, color = SageMuted)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // REMINDERS SECTION HEADER
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "REMINDERS",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = SageDark,
                letterSpacing = 0.5.sp
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(MintSurface)
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text("+ add", fontSize = 12.sp, color = MintGreen)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // REMINDER CARD
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            border = CardDefaults.outlinedCardBorder().copy(width = 0.5.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "5 slow belly breaths can help you let go of today. Extra long exhales. 🌬️",
                    fontSize = 13.sp,
                    color = SageMid,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = {},
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MintGreen),
                        border = ButtonDefaults.outlinedButtonBorder.copy(width = 0.5.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text("📓 Journal", fontSize = 11.sp)
                    }
                    OutlinedButton(
                        onClick = {},
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MintGreen),
                        border = ButtonDefaults.outlinedButtonBorder.copy(width = 0.5.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text("↗ Share", fontSize = 11.sp)
                    }
                    Text("hide", fontSize = 12.sp, color = SageMuted)
                }
            }
        }

        Spacer(modifier = Modifier.height(80.dp))
    }

    // BOTTOM NAVIGATION BAR
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .border(0.5.dp, MintBorder)
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItemImage(
                icon = homeIcon,
                label = "Home",
                isSelected = true
            )
            BottomNavItemImage(
                icon = inboxIcon,
                label = "Inbox",
                isSelected = false
            )

            // Centre + button
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(MintGreen),
                contentAlignment = UiAlignment.Center
            ) {
                Icon(
                    painter = plusIcon,
                    contentDescription = "Add",
                    modifier = Modifier.size(32.dp),
                    tint = White
                )
            }

            BottomNavItemImage(
                icon = insightsIcon,
                label = "Insights",
                isSelected = false
            )
            BottomNavItemImage(
                icon = historyIcon,
                label = "History",
                isSelected = false
            )
        }
    }
}

// REUSABLE: Single Goal Card with Image Icon (icon is 3/4 of box size)
@Composable
fun GoalCardImage(
    icon: Painter,
    label: String,
    bgColor: Color,
    textColor: Color,
    isDone: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Icon is 3/4 (0.75) of the box size
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .fillMaxHeight(0.75f),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = icon,
                    contentDescription = label,
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.Unspecified
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(label, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = textColor)
        }
        // Green tick if done
        if (isDone) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(6.dp)
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(MintGreen),
                contentAlignment = Alignment.Center
            ) {
                Text("✓", fontSize = 10.sp, color = White)
            }
        }
    }
}

// REUSABLE: Bottom Nav Item with Image Icon (bigger icons)
@Composable
fun BottomNavItemImage(
    icon: Painter,
    label: String,
    isSelected: Boolean
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Made icons bigger: 32dp instead of 28dp (inbox, insights, history)
        Icon(
            painter = icon,
            contentDescription = label,
            modifier = Modifier.size(42.dp),
            tint = Color.Unspecified
        )
        Text(
            label,
            fontSize = 9.sp,
            color = if (isSelected) MintGreen else SageMuted,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MoodSpacePreview() {
    MoodSpaceApp()
}