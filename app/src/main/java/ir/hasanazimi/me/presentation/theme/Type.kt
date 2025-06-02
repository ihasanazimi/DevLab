package ir.hasanazimi.me.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.sp
import ir.hasanazimi.me.R

val Yekan = FontFamily(
    Font(R.font.yekan, FontWeight.Normal),
    Font(R.font.yekan, FontWeight.Bold)
)

// Define custom typography based on Material Design 3
val CustomTypography = Typography(
    // Display styles (for large, decorative text)
    displayLarge = TextStyle(
        fontFamily = Yekan,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        textDirection = TextDirection.Rtl,
        textAlign = TextAlign.Start
    ),
    displayMedium = TextStyle(
        fontFamily = Yekan,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        textDirection = TextDirection.Rtl,
        textAlign = TextAlign.Start
    ),
    displaySmall = TextStyle(
        fontFamily = Yekan,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        textDirection = TextDirection.Rtl,
        textAlign = TextAlign.Start
    ),

    // Headline styles (for prominent headings)
    headlineLarge = TextStyle(
        fontFamily = Yekan,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        textDirection = TextDirection.Rtl,
        textAlign = TextAlign.Start
    ),
    headlineMedium = TextStyle(
        fontFamily = Yekan,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        textDirection = TextDirection.Rtl,
        textAlign = TextAlign.Start
    ),
    headlineSmall = TextStyle(
        fontFamily = Yekan,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        textDirection = TextDirection.Rtl,
        textAlign = TextAlign.Start
    ),

    // Title styles (for medium headings)
    titleLarge = TextStyle(
        fontFamily = Yekan,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        textDirection = TextDirection.Rtl,
        textAlign = TextAlign.Start
    ),
    titleMedium = TextStyle(
        fontFamily = Yekan,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        textDirection = TextDirection.Rtl,
        textAlign = TextAlign.Start
    ),
    titleSmall = TextStyle(
        fontFamily = Yekan,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        textDirection = TextDirection.Rtl,
        textAlign = TextAlign.Start
    ),

    // Body styles (for main content)
    bodyLarge = TextStyle(
        fontFamily = Yekan,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        textDirection = TextDirection.Rtl,
        textAlign = TextAlign.Justify
    ),
    bodyMedium = TextStyle(
        fontFamily = Yekan,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        textDirection = TextDirection.Rtl,
        textAlign = TextAlign.Justify
    ),
    bodySmall = TextStyle(
        fontFamily = Yekan,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        textDirection = TextDirection.Rtl,
        textAlign = TextAlign.Justify
    ),

    // Label styles (for buttons, captions, and small text)
    labelLarge = TextStyle(
        fontFamily = Yekan,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        textDirection = TextDirection.Rtl,
        textAlign = TextAlign.Justify
    ),
    labelMedium = TextStyle(
        fontFamily = Yekan,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        textDirection = TextDirection.Rtl,
        textAlign = TextAlign.Justify
    ),
    labelSmall = TextStyle(
        fontFamily = Yekan,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        textDirection = TextDirection.Rtl,
        textAlign = TextAlign.Justify
    )
)