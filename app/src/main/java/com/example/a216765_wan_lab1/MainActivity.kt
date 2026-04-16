package com.example.a216765_wan_lab1

import android.os.Bundle
import java.util.Calendar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
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
import com.example.a216765_wan_lab1.ui.theme.MoodSpaceTheme
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring

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
        setContent {
            MoodSpaceTheme {
                MoodSpaceApp()
            }
        }
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

// ── Gets current date and time as a formatted string ──
fun getCurrentDateTime(): String {
    val sdf = SimpleDateFormat("MMM d yyyy, h:mm a", Locale.getDefault())
    return sdf.format(Date())
}

@Composable
fun MoodSpaceApp() {
    val scrollState = rememberScrollState()

    // ── STATE VARIABLES ──
    var nameInput          by remember { mutableStateOf("") }
    var displayedName      by remember { mutableStateOf("") }
    var showMoodDialog     by remember { mutableStateOf(false) }
    var moodDone           by remember { mutableStateOf(false) }
    var gratitudeDone      by remember { mutableStateOf(false) }  // NEW
    var selectedMood       by remember { mutableStateOf("") }
    var showGratitudePage  by remember { mutableStateOf(false) }  // NEW: controls which page shows

    // ── If Gratitude page is showing, show that instead of home ──
    if (showGratitudePage) {
        GratitudePage(
            onSave = {
                gratitudeDone = true           // green tick on Gratitude card
                showGratitudePage = false      // go back to home
            },
            onBack = {
                showGratitudePage = false      // go back without saving
            }
        )
        return  // stop rendering the home screen
    }

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

    if (showMoodDialog) {
        MoodDialog(
            currentMood = selectedMood,
            onSave = { chosenMood ->
                selectedMood = chosenMood
                moodDone = true
                showMoodDialog = false
            },
            onDismiss = { showMoodDialog = false }
        )
    }

    // ── Calculate daily goals % dynamically ──
    val doneCount = listOf(moodDone, gratitudeDone).count { it }
    val totalGoals = 3
    val completedGoals = doneCount
    val percentage = ((completedGoals.toFloat() / totalGoals) * 100).toInt()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = GoalPinkTxt)
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
            Text(text = "How are you feeling today?", fontSize = 13.sp, color = SageMuted)
        }

        // ── NAME INPUT CARD ──
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp),
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
                    placeholder = { Text("Enter your name...", fontSize = 13.sp, color = SageMuted) },
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
            modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            border = CardDefaults.outlinedCardBorder().copy(width = 0.5.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("MOOD STREAK", fontSize = 10.sp, color = SageMuted,
                        fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp)
                    Text("1", fontSize = 36.sp, fontWeight = FontWeight.Bold, color = MintGreen)
                    Text("day in a row", fontSize = 11.sp, color = SageMuted)
                }
                Box(modifier = Modifier.width(0.5.dp).height(70.dp).background(MintBorder))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("DAILY GOALS", fontSize = 10.sp, color = SageMuted,
                        fontWeight = FontWeight.Bold, letterSpacing = 0.5.sp)
                    // ── Updates dynamically as user completes goals ──
                    Text("$percentage%", fontSize = 36.sp,
                        fontWeight = FontWeight.Bold, color = MintGreen)
                    Text("$completedGoals of $totalGoals", fontSize = 11.sp, color = SageMuted)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ── DAILY GOALS HEADER ──
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("DAILY GOALS", fontSize = 12.sp, fontWeight = FontWeight.Bold,
                color = SageDark, letterSpacing = 0.5.sp)
            Box(
                modifier = Modifier.clip(RoundedCornerShape(20.dp))
                    .background(MintSurface).padding(horizontal = 12.dp, vertical = 4.dp)
            ) { Text("+ add", fontSize = 12.sp, color = MintGreen) }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // ── GOAL CARDS GRID ──
        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            border = CardDefaults.outlinedCardBorder().copy(width = 0.5.dp)
        ) {
            Column(modifier = Modifier.padding(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    GoalCardImage(
                        icon = smileFaceIcon, label = "Mood",
                        bgColor = GoalBlue, textColor = GoalBlueTxt, isDone = moodDone,
                        modifier = Modifier.weight(1f).clickable { showMoodDialog = true }
                    )
                    // ── Gratitude card — tapping opens Gratitude page ──
                    GoalCardImage(
                        icon = heartIcon, label = "Gratitude",
                        bgColor = GoalPink, textColor = GoalPinkTxt, isDone = gratitudeDone,
                        modifier = Modifier.weight(1f).clickable { showGratitudePage = true }
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
                        modifier = Modifier.weight(1f).aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp)).background(GoalEmpty)
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
            modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("REMINDERS", fontSize = 12.sp, fontWeight = FontWeight.Bold,
                color = SageDark, letterSpacing = 0.5.sp)
            Box(
                modifier = Modifier.clip(RoundedCornerShape(20.dp))
                    .background(MintSurface).padding(horizontal = 12.dp, vertical = 4.dp)
            ) { Text("+ add", fontSize = 12.sp, color = MintGreen) }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // ── REMINDER CARD WITH EXPAND ANIMATION ──
        var isReminderExpanded by remember { mutableStateOf(false) }

        Card(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp)
                .clickable { isReminderExpanded = !isReminderExpanded },
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            border = CardDefaults.outlinedCardBorder().copy(width = 0.5.dp)
        ) {
            Column(
                modifier = Modifier.padding(14.dp).animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
            ) {
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
                if (isReminderExpanded) {
                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = MintBorder, thickness = 0.5.dp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("💡 Tip of the day", fontSize = 12.sp,
                        fontWeight = FontWeight.Bold, color = SageDark)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Taking a few minutes each day to check in with your emotions can significantly improve your mental well-being. You are doing great! 🌿",
                        fontSize = 12.sp, color = SageMid, lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Tap to collapse ▲", fontSize = 11.sp, color = SageMuted,
                        modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                } else {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Tap to read more ▼", fontSize = 11.sp, color = SageMuted,
                        modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("A216765", fontSize = 11.sp, color = SageMuted,
            modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(80.dp))
    }

    // ── BOTTOM NAV BAR ──
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Row(
            modifier = Modifier.fillMaxWidth().background(White)
                .border(0.5.dp, MintBorder).padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItemImage(icon = homeIcon, label = "Home", isSelected = true)
            BottomNavItemImage(icon = inboxIcon, label = "Inbox", isSelected = false)
            Box(
                modifier = Modifier.size(44.dp).clip(CircleShape).background(MintGreen),
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

// ══════════════════════════════════════════════════
// ── GRATITUDE PAGE ──
// ══════════════════════════════════════════════════
@Composable
fun GratitudePage(
    onSave: () -> Unit,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()

    // ── List of available prompts ──
    val promptOptions = listOf(
        "What are you grateful for?",
        "What are you looking forward to in the future?",
        "What accomplishments (big or small) did you have today?",
        "What about today has been better than yesterday?",
        "What were the best moments from today (even if they were challenging)?",
        "What are very small things you're grateful for?",
        "What are things about you that you're grateful for?",
        "What made you smile or laugh today?",
        "Who has been helpful to you?",
        "What could have gone worse today but didn't?"
    )

    // ── State for selected prompt ──
    var selectedPrompt by remember { mutableStateOf(promptOptions[0]) }

    // ── State for showing prompt picker popup ──
    var showPromptPicker by remember { mutableStateOf(false) }

    // ── State for the 3 gratitude text fields ──
    var grateful1 by remember { mutableStateOf("") }
    var grateful2 by remember { mutableStateOf("") }
    var grateful3 by remember { mutableStateOf("") }

    // ── State for mood effect selection ──
    var selectedEffect by remember { mutableStateOf("") }

    // ── State for About Gratitude section visibility ──
    var showAboutGratitude by remember { mutableStateOf(true) }

    // ── State for unsaved changes popup ──
    var showUnsavedDialog by remember { mutableStateOf(false) }

    // ── Check if user has made any changes ──
    val hasChanges = grateful1.isNotEmpty() || grateful2.isNotEmpty() ||
            grateful3.isNotEmpty() || selectedEffect.isNotEmpty()

    // ── Prompt Picker Popup ──
    if (showPromptPicker) {
        Dialog(onDismissRequest = { showPromptPicker = false }) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    // ── Header ──
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(onClick = { showPromptPicker = false }) {
                            Text("＜", fontSize = 16.sp, color = SageDark)
                        }
                        Text(
                            "Gratitude Prompts",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Black,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(40.dp))
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider(color = MintBorder, thickness = 0.5.dp)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "Select a prompt to enter gratitude about.",
                        fontSize = 12.sp,
                        color = SageMuted
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // ── Scrollable list of prompts ──
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 400.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        promptOptions.forEach { prompt ->
                            HorizontalDivider(color = MintBorder, thickness = 0.5.dp)
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        // ── Only clear fields if a DIFFERENT prompt is chosen ──
                                        if (prompt != selectedPrompt) {
                                            grateful1 = ""
                                            grateful2 = ""
                                            grateful3 = ""
                                            selectedPrompt = prompt
                                        }
                                        showPromptPicker = false
                                    }
                                    .padding(vertical = 14.dp, horizontal = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("💡", fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = prompt,
                                    fontSize = 13.sp,
                                    color = SageDark,
                                    lineHeight = 18.sp
                                )
                            }
                        }
                        HorizontalDivider(color = MintBorder, thickness = 0.5.dp)
                    }
                }
            }
        }
    }

    // ── Unsaved Changes Popup ──
    if (showUnsavedDialog) {
        Dialog(onDismissRequest = { showUnsavedDialog = false }) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("🖐 Unsaved Changes", fontSize = 16.sp,
                        fontWeight = FontWeight.Bold, color = Black)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Do you want to save them?", fontSize = 13.sp, color = SageMuted)
                    Spacer(modifier = Modifier.height(20.dp))
                    TextButton(
                        onClick = { showUnsavedDialog = false; onSave() },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Save", fontSize = 15.sp, color = MintGreen) }
                    HorizontalDivider(color = MintBorder, thickness = 0.5.dp)
                    TextButton(
                        onClick = { showUnsavedDialog = false; onBack() },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Don't Save", fontSize = 15.sp, color = Color(0xFFE57373)) }
                    HorizontalDivider(color = MintBorder, thickness = 0.5.dp)
                    TextButton(
                        onClick = { showUnsavedDialog = false },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Cancel", fontSize = 15.sp, color = SageMuted) }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = GoalPinkTxt)
    ) {

        // ── TOP BAR ──
        Row(
            modifier = Modifier.fillMaxWidth().background(White)
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = {
                if (hasChanges) showUnsavedDialog = true else onBack()
            }) {
                Text("✕", fontSize = 20.sp, color = Black, fontWeight = FontWeight.Bold)
            }
            Text("Gratitude", fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Black)
            TextButton(onClick = { onSave() }) {
                Text("Save", fontSize = 15.sp, color = Black, fontWeight = FontWeight.Bold)
            }
        }

        // ── JOURNAL TAB ──
        Row(
            modifier = Modifier.fillMaxWidth().background(White).padding(bottom = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                "JOURNAL",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = SageDark,
                modifier = Modifier
                    .border(width = 0.5.dp, color = MintBorder,
                        shape = RoundedCornerShape(4.dp))
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            )
        }

        // ── SCROLLABLE CONTENT ──
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(scrollState)
        ) {

            Spacer(modifier = Modifier.height(12.dp))

            // ── MAIN JOURNAL CARD ──
            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    // ── Header: prompt question + "Select prompt" button ──
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            // ── Shows the currently selected prompt ──
                            text = selectedPrompt,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Black,
                            modifier = Modifier.weight(1f),
                            lineHeight = 20.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        // ── "Select prompt" is now a clickable TextButton ──
                        TextButton(onClick = { showPromptPicker = true }) {
                            Text("Select prompt", fontSize = 12.sp, color = SageMuted)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = MintBorder, thickness = 0.5.dp)

                    // ── Date and Time (current) ──
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("📅", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Date", fontSize = 13.sp, color = SageDark)
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = getCurrentDateTime(), fontSize = 12.sp, color = SageMuted)
                    }

                    HorizontalDivider(color = MintBorder, thickness = 0.5.dp)
                    Spacer(modifier = Modifier.height(12.dp))

                    // ── Grateful Field 1 ──
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("🤍", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            value = grateful1,
                            onValueChange = { grateful1 = it },
                            placeholder = {
                                Text("I'm grateful for...", fontSize = 13.sp, color = SageMuted)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MintGreen,
                                unfocusedBorderColor = Color.Transparent
                            ),
                            singleLine = true
                        )
                    }

                    HorizontalDivider(color = MintBorder, thickness = 0.5.dp)
                    Spacer(modifier = Modifier.height(8.dp))

                    // ── Grateful Field 2 ──
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("🤍", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            value = grateful2,
                            onValueChange = { grateful2 = it },
                            placeholder = {
                                Text("I'm grateful for...", fontSize = 13.sp, color = SageMuted)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MintGreen,
                                unfocusedBorderColor = Color.Transparent
                            ),
                            singleLine = true
                        )
                    }

                    HorizontalDivider(color = MintBorder, thickness = 0.5.dp)
                    Spacer(modifier = Modifier.height(8.dp))

                    // ── Grateful Field 3 ──
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("🤍", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            value = grateful3,
                            onValueChange = { grateful3 = it },
                            placeholder = {
                                Text("I'm grateful for...", fontSize = 13.sp, color = SageMuted)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MintGreen,
                                unfocusedBorderColor = Color.Transparent
                            ),
                            singleLine = true
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // ── ADD ITEM button ──
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("➕", fontSize = 16.sp, color = GoalPinkTxt)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("ADD ITEM", fontSize = 13.sp,
                            fontWeight = FontWeight.Bold, color = GoalPinkTxt)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ── MOOD EFFECT CARD ──
            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("🙂", fontSize = 16.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Mood Effect", fontSize = 13.sp,
                            fontWeight = FontWeight.Bold, color = Black)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("The effect on your mood", fontSize = 11.sp, color = SageMuted)
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    val effects = listOf("Very\nPositive", "Positive", "Neutral",
                        "Negative", "Very\nNegative")
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        effects.forEach { effect ->
                            val isSelected = selectedEffect == effect
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(if (isSelected) MintGreen else Color(0xFFEEEEEE))
                                    .clickable { selectedEffect = effect }
                                    .padding(horizontal = 8.dp, vertical = 6.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = effect,
                                    fontSize = 10.sp,
                                    color = if (isSelected) White else Black,
                                    fontWeight = if (isSelected) FontWeight.Bold
                                    else FontWeight.Normal,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ── SHARE BUTTON ──
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                TextButton(onClick = {}) {
                    Text("➡ SHARE", fontSize = 13.sp,
                        fontWeight = FontWeight.Bold, color = SageMuted)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // ── ABOUT GRATITUDE SECTION ──
            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp).animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    )
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("About Gratitude", fontSize = 14.sp,
                            fontWeight = FontWeight.Bold, color = Black)
                        TextButton(onClick = { showAboutGratitude = !showAboutGratitude }) {
                            Text(
                                if (showAboutGratitude) "hide" else "show",
                                fontSize = 12.sp, color = SageMuted
                            )
                        }
                    }
                    if (showAboutGratitude) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Keeping a daily gratitude journal has been shown to increase positive emotions. The process of mentally seeking things you are grateful for can literally rewire how your brain processes events and begins to call attention to more and more of the positives in life.",
                            fontSize = 12.sp, color = SageMid, lineHeight = 18.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Expressing why you're grateful for these items gives your brain additional opportunity to build a gratitude mindset. Saying, \"Lunch with a friend\" is an example gratitude entry. Saying, \"Lunch with a friend because it was nice to feel supported during this challenging time\" is an even better entry.",
                            fontSize = 12.sp, color = SageMid, lineHeight = 18.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Spending time savouring the items that you're grateful for gives your brain even more opportunity to build a gratitude mindset. After you make your gratitude entry, MoodSpace will give you the opportunity to savour your entries by playing them back to you in a short slideshow.",
                            fontSize = 12.sp, color = SageMid, lineHeight = 18.sp
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Finally, sending expressions of gratitude to others increases positive emotions for both the sender and receiver.",
                            fontSize = 12.sp, color = SageMid, lineHeight = 18.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

// ── MOOD POPUP DIALOG ──
@Composable
fun MoodDialog(
    currentMood: String,
    onSave: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var tempMood by remember { mutableStateOf(currentMood) }

    val greatemoji: Painter = painterResource(id = R.drawable.great_emoji)
    val goodemoji:  Painter = painterResource(id = R.drawable.good_emoji)
    val okemoji:    Painter = painterResource(id = R.drawable.ok_emoji)
    val pooremoji:  Painter = painterResource(id = R.drawable.poor_emoji)
    val bademoji:   Painter = painterResource(id = R.drawable.bad_emoji)

    val moods = listOf(
        greatemoji to "Great",
        goodemoji  to "Good",
        okemoji    to "OK",
        pooremoji  to "Poor",
        bademoji   to "Bad"
    )

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("✕", fontSize = 18.sp, color = SageDark)
                    }
                    Text("Mood", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SageDark)
                    TextButton(onClick = { if (tempMood.isNotEmpty()) onSave(tempMood) }) {
                        Text("Save", fontSize = 14.sp,
                            fontWeight = FontWeight.Bold, color = MintGreen)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text("What is your mood?", fontSize = 14.sp,
                    fontWeight = FontWeight.Bold, color = SageDark)
                Spacer(modifier = Modifier.height(16.dp))
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
                                .background(if (isSelected) MintGreen else Color.Transparent)
                                .clickable { tempMood = label }
                                .padding(10.dp)
                        ) {
                            Icon(painter = icon, contentDescription = label,
                                modifier = Modifier.size(36.dp), tint = Color.Unspecified)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(label, fontSize = 11.sp,
                                color = if (isSelected) White else Black,
                                fontWeight = if (isSelected) FontWeight.Bold
                                else FontWeight.Normal)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Text("Comments", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SageDark)
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = {
                        Text("Enter comments about what you're thinking and feeling...",
                            fontSize = 12.sp, color = SageMuted, lineHeight = 18.sp)
                    },
                    modifier = Modifier.fillMaxWidth().height(100.dp),
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
        modifier = modifier.aspectRatio(1f).clip(RoundedCornerShape(12.dp)).background(bgColor),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier.fillMaxWidth(0.75f).fillMaxHeight(0.75f),
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
                modifier = Modifier.align(Alignment.TopEnd).padding(6.dp)
                    .size(18.dp).clip(CircleShape).background(MintGreen),
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