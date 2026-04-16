package com.example.a216765_wan_lab1.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// LIGHT COLOR SCHEME
private val LightColorScheme = lightColorScheme(
    primary          = Green40,
    onPrimary        = Grey99,
    primaryContainer = Green90,
    onPrimaryContainer = Green10,

    secondary        = Sage40,
    onSecondary      = Grey99,
    secondaryContainer = Sage90,
    onSecondaryContainer = Sage10,

    tertiary         = Pink40,
    onTertiary       = Grey99,
    tertiaryContainer = Pink90,
    onTertiaryContainer = Pink10,

    background       = Grey99,
    onBackground     = Grey10,
    surface          = Grey99,
    onSurface        = Grey10,
    surfaceVariant   = Sage90,
    onSurfaceVariant = Sage30,

    error            = Red40,
    onError          = Grey99,
    errorContainer   = Red90,
    onErrorContainer = Red10,
)

// DARK COLOR SCHEME
private val DarkColorScheme = darkColorScheme(
    primary          = Green80,
    onPrimary        = Green20,
    primaryContainer = Green30,
    onPrimaryContainer = Green90,

    secondary        = Sage80,
    onSecondary      = Sage20,
    secondaryContainer = Sage30,
    onSecondaryContainer = Sage90,

    tertiary         = Pink80,
    onTertiary       = Pink20,
    tertiaryContainer = Pink30,
    onTertiaryContainer = Pink90,

    background       = Grey10,
    onBackground     = Grey90,
    surface          = Grey10,
    onSurface        = Grey90,
)

//  TYPOGRAPHY
// Define text styles for your app
val MoodSpaceTypography = androidx.compose.material3.Typography(
    // Used for big headings like "Good morning, Adlyna!"
    headlineMedium = androidx.compose.ui.text.TextStyle(
        fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        fontSize = androidx.compose.ui.unit.TextUnit(22f,
            androidx.compose.ui.unit.TextUnitType.Sp)
    ),
    // Used for section headers like "DAILY GOALS"
    titleSmall = androidx.compose.ui.text.TextStyle(
        fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        fontSize = androidx.compose.ui.unit.TextUnit(12f,
            androidx.compose.ui.unit.TextUnitType.Sp),
        letterSpacing = androidx.compose.ui.unit.TextUnit(0.5f,
            androidx.compose.ui.unit.TextUnitType.Sp)
    ),
    // Used for body text like reminder messages
    bodyMedium = androidx.compose.ui.text.TextStyle(
        fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
        fontSize = androidx.compose.ui.unit.TextUnit(13f,
            androidx.compose.ui.unit.TextUnitType.Sp)
    ),
    // Used for small labels like bottom nav text
    labelSmall = androidx.compose.ui.text.TextStyle(
        fontFamily = androidx.compose.ui.text.font.FontFamily.Default,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Normal,
        fontSize = androidx.compose.ui.unit.TextUnit(9f,
            androidx.compose.ui.unit.TextUnitType.Sp)
    )
)

// SHAPES
val MoodSpaceShapes = androidx.compose.material3.Shapes(
    small  = androidx.compose.foundation.shape.RoundedCornerShape(8),
    medium = androidx.compose.foundation.shape.RoundedCornerShape(16),
    large  = androidx.compose.foundation.shape.RoundedCornerShape(20)
)

//  MAIN THEME COMPOSABLE
@Composable
fun MoodSpaceTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view)
                .isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = MoodSpaceTypography,
        shapes      = MoodSpaceShapes,
        content     = content
    )
}