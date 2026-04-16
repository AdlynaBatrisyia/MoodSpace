package com.example.a216765_wan_lab1

import android.os.Bundle
import java.util.Calendar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.Alignment as UiAlignment

// ── COLORS ──
val MintLight     = Color(0xFFf8fdf9)
val MintSurface   = Color(0xFFe8f5ee)
val MintBorder    = Color(0xFFc8e6d4)
val MintGreen     = Color(0xFF3a9e6a)
val SageDark      = Color(0xFF2d6b4a)
val SageMid       = Color(0xFF4a7a60)
val SageMuted     = Color(0xFF7aaa90)
val White         = Color(0xFFFFFFFF)
val GoalBlue      = Color(0xFFe8f5ee)
val GoalBlueTxt   = Color(0xFF2d6b4a)
val GoalPink      = Color(0xFFfce8f0)
val GoalPinkTxt   = Color(0xFFa0526d)
val GoalPurple    = Color(0xFFede8fc)
val GoalPurpleTxt = Color(0xFF6b52b8)
val GoalEmpty     = Color(0xFFf5f5f5)

val Black         = Color(0xFF000000)
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

    // ── STATE VARIABLES ──
    var nameInput       by remember { mutableStateOf("") }
    var displayedName   by remember { mutableStateOf("") }
    var showMoodDialog  by remember { mutableStateOf(false) }   // controls popup visibility
    var moodDone        by remember { mutableStateOf(false) }   // controls green tick on Mood card
    var selectedMood    by remember { mutableStateOf("") }      // stores chosen mood label

    val settingsIcon:   Painter = painterResource(id = R.drawable.settings_icon)
    val homeIconTop:    Painter = painterResource(id = R.drawable.home_icon)
    val refreshIcon:    Painter = painterResource(id = R.drawable.refresh_icon)
    val smileFaceIcon:  Painter = painterResource(id = R.drawable.smile_face_icon)
    val heartIcon:      Painter = painterResource(id = R.drawable.heart_icon)
    val sleepIcon:      Painter = painterResource(id = R.drawable.sleep_icon)
    val homeIcon:       Painter = painterResource(id = R.drawable.home_icon)
    val inboxIcon:      Painter = painterResource(id = R.drawable.inbox_icon)
    val plusIcon:       Painter = painterResource(id = R.drawable.plus_icon)
    val insightsIcon:   Painter = painterResource(id = R.drawable.insight_icon)
    val historyIcon:    Painter = painterResource(id = R.drawable.history_icon)
    val greatemoji:     Painter = painterResource(id = R.drawable.great_emoji)
    val goodemoji:      Painter = painterResource(id = R.drawable.good_emoji)
    val okemoji:        Painter = painterResource(id = R.drawable.ok_emoji)
    val pooremoji:      Painter = painterResource(id = R.drawable.poor_emoji)
    val bademoji:       Painter = painterResource(id = R.drawable.bad_emoji)

    // ── MOOD POPUP DIALOG ──
    // This appears when user taps the Mood card
    if (showMoodDialog) {
        MoodDialog(
            currentMood = selectedMood,
            onSave = { chosenMood ->
                selectedMood = chosenMood   // save the chosen mood
                moodDone = true             // show green tick on Mood card
                showMoodDialog = false      // close the popup
            },
            onDismiss = {
                showMoodDialog = false      // close popup if X is tapped
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MintLight)
            .verticalScroll(scrollState)
    ) {

        // ── TOP BAR ──
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .padding(horizontal = 18.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(painter = settingsIcon, contentDescription = "Settings",
                modifier = Modifier.size(20.dp), tint = Color.Unspecified)
            Box(modifier = Modifier.weight(1f), contentAlignment = UiAlignment.Center) {
                Icon(painter = homeIconTop, contentDescription = "Home",
                    modifier = Modifier.size(20.dp), tint = Color.Unspecified)
            }
            Icon(painter = refreshIcon, contentDescription = "Refresh",
                modifier = Modifier.size(20.dp), tint = Color.Unspecified)
        }

        // ── GREETING ──
        Column(modifier = Modifier.padding(horizontal = 18.dp, vertical = 16.dp)) {
            Text(
                text = if (displayedName.isNotEmpty())
                    "${getGreeting()}, $displayedName! 🌿"
                else
                    "${getGreeting()}, User! 🌿",
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

        // ── NAME INPUT CARD ──
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            border = CardDefaults.outlinedCardBorder().copy(width = 0.5.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("What's your name?", fontSize = 13.sp,
                    fontWeight = FontWeight.Bold, color = SageDark)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    placeholder = {
                        Text("Enter your name...", fontSize = 13.sp, color = SageMuted)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MintGreen,
                        unfocusedBorderColor = MintBorder
                    ),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = { if (nameInput.isNotEmpty()) displayedName = nameInput },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MintGreen)
                ) {
                    Text("Save Name", fontSize = 14.sp, color = White)
                }
                if (displayedName.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Welcome back, $displayedName! 💚",
                        fontSize = 13.sp, color = MintGreen, fontWeight = FontWeight.Bold)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ── STATS CARD ──
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
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("MOOD STREAK", fontSize = 10.sp, color = SageMuted,
                        fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp)
                    Text("1", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = MintGreen)
                    Text("day in a row", fontSize = 11.sp, color = SageMuted)
                }
                Box(modifier = Modifier
                    .width(0.5.dp)
                    .height(70.dp)
                    .background(MintBorder))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("DAILY GOALS", fontSize = 10.sp, color = SageMuted,
                        fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp)
                    Text("33%", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = MintGreen)
                    Text("1 of 3", fontSize = 11.sp, color = SageMuted)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ── DAILY GOALS HEADER ──
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("DAILY GOALS", fontSize = 12.sp, fontWeight = FontWeight.Bold,
                color = SageDark, letterSpacing = 0.5.sp)
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(MintSurface)
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) { Text("+ add", fontSize = 12.sp, color = MintGreen) }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // ── GOAL CARDS GRID ──
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            border = CardDefaults.outlinedCardBorder().copy(width = 0.5.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // ── MOOD CARD — tapping this opens the popup ──
                    GoalCardImage(
                        icon = smileFaceIcon,
                        label = "Mood",
                        bgColor = GoalBlue,
                        textColor = GoalBlueTxt,
                        isDone = moodDone,              // green tick appears after mood saved
                        modifier = Modifier
                            .weight(1f)
                            .clickable { showMoodDialog = true }  // opens popup on tap
                    )
                    GoalCardImage(
                        icon = heartIcon, label = "Gratitude",
                        bgColor = GoalPink, textColor = GoalPinkTxt, isDone = false,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    GoalCardImage(
                        icon = sleepIcon, label = "Sleep",
                        bgColor = GoalPurple, textColor = GoalPurpleTxt, isDone = false,
                        modifier = Modifier.weight(1f)
                    )
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

        // ── REMINDERS HEADER ──
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("REMINDERS", fontSize = 12.sp, fontWeight = FontWeight.Bold,
                color = SageDark, letterSpacing = 0.5.sp)
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(MintSurface)
                    .padding(horizontal = 12.dp, vertical = 4.dp)
            ) { Text("+ add", fontSize = 12.sp, color = MintGreen) }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // ── REMINDER CARD ──
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
                    fontSize = 13.sp, color = SageMid, lineHeight = 20.sp
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
                    ) { Text("📓 Journal", fontSize = 11.sp) }
                    OutlinedButton(
                        onClick = {},
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MintGreen),
                        border = ButtonDefaults.outlinedButtonBorder.copy(width = 0.5.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                    ) { Text("↗ Share", fontSize = 11.sp) }
                    Text("hide", fontSize = 12.sp, color = SageMuted)
                }
            }
        }

        // ── MATRIC NUMBER (required by lab) ──
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "A216765",
            fontSize = 11.sp, color = SageMuted,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(80.dp))
    }

    // ── BOTTOM NAV BAR ──
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
            BottomNavItemImage(icon = homeIcon, label = "Home", isSelected = true)
            BottomNavItemImage(icon = inboxIcon, label = "Inbox", isSelected = false)
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(MintGreen),
                contentAlignment = UiAlignment.Center
            ) {
                Icon(painter = plusIcon, contentDescription = "Add",
                    modifier = Modifier.size(32.dp), tint = White)
            }
            BottomNavItemImage(icon = insightsIcon, label = "Insights", isSelected = false)
            BottomNavItemImage(icon = historyIcon, label = "History", isSelected = false)
        }
    }
}

// ── MOOD POPUP DIALOG ──
// This is the popup screen that appears when user taps the Mood card
@Composable
fun MoodDialog(
    currentMood: String,
    onSave: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var tempMood by remember { mutableStateOf(currentMood) }

    // ── ADD ICON DECLARATIONS HERE ──
    val greatemoji: Painter = painterResource(id = R.drawable.great_emoji)
    val goodemoji:  Painter = painterResource(id = R.drawable.good_emoji)
    val okemoji:    Painter = painterResource(id = R.drawable.ok_emoji)
    val pooremoji:  Painter = painterResource(id = R.drawable.poor_emoji)
    val bademoji:   Painter = painterResource(id = R.drawable.bad_emoji)

    // ── UPDATED: now uses Painter instead of emoji string ──
    val moods = listOf(
        greatemoji to "Great",
        goodemoji   to "Good",
        okemoji     to "OK",
        pooremoji   to "Poor",
        bademoji    to "Bad"
    )

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                // ── DIALOG TOP BAR ──
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("✕", fontSize = 18.sp, color = SageDark)
                    }
                    Text("Mood", fontSize = 16.sp,
                        fontWeight = FontWeight.Bold, color = SageDark)
                    TextButton(
                        onClick = { if (tempMood.isNotEmpty()) onSave(tempMood) }
                    ) {
                        Text("Save", fontSize = 14.sp,
                            fontWeight = FontWeight.Bold, color = MintGreen)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("What is your mood?", fontSize = 14.sp,
                    fontWeight = FontWeight.Bold, color = SageDark)

                Spacer(modifier = Modifier.height(16.dp))

                // ── MOOD ICONS ROW ──
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    moods.forEach { (icon, label) ->
                        val isSelected = tempMood == label
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(
                                    if (isSelected) MintGreen else Color.Transparent
                                )
                                .clickable { tempMood = label }
                                .padding(10.dp)
                        ) {
                            // ── ICON  ──
                            Icon(
                                painter = icon,
                                contentDescription = label,
                                modifier = Modifier.size(36.dp),
                                tint = Color.Unspecified
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                label,
                                fontSize = 11.sp,
                                color = if (isSelected) White else Black,
                                fontWeight = if (isSelected) FontWeight.Bold
                                else FontWeight.Normal
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // ── COMMENTS FIELD ──
                Text("Comments", fontSize = 13.sp,
                    fontWeight = FontWeight.Bold, color = SageDark)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = {
                        Text(
                            "Enter comments about what you're thinking and feeling...",
                            fontSize = 12.sp,
                            color = SageMuted,
                            lineHeight = 18.sp
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MintGreen,
                        unfocusedBorderColor = MintBorder
                    )
                )
            }
        }
    }
}

// ── REUSABLE: Goal Card ──
@Composable
fun GoalCardImage(
    icon: Painter, label: String, bgColor: Color,
    textColor: Color, isDone: Boolean, modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .fillMaxHeight(0.75f),
                contentAlignment = Alignment.Center
            ) {
                Icon(painter = icon, contentDescription = label,
                    modifier = Modifier.fillMaxSize(), tint = Color.Unspecified)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(label, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = textColor)
        }
        if (isDone) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(6.dp)
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(MintGreen),
                contentAlignment = Alignment.Center
            ) { Text("✓", fontSize = 10.sp, color = White) }
        }
    }
}

// ── REUSABLE: Bottom Nav Item ──
@Composable
fun BottomNavItemImage(icon: Painter, label: String, isSelected: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(painter = icon, contentDescription = label,
            modifier = Modifier.size(42.dp), tint = Color.Unspecified)
        Text(label, fontSize = 9.sp,
            color = if (isSelected) MintGreen else SageMuted,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
    }
}

@Preview(showBackground = true)
@Composable
fun MoodSpacePreview() {
    MoodSpaceApp()
}