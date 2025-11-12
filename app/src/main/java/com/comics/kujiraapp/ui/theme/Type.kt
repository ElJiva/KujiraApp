package com.rasec.musicapp.ui.theme


import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.comics.kujiraapp.R

val poppinsFontFamily = FontFamily(
    Font(R.font.poppins_regular),
    Font(R.font.poppins_light, FontWeight.W300),
    Font(R.font.poppins_bold, FontWeight.W700)
)

val poppinsTypo = Typography(
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontFamily = poppinsFontFamily,
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontFamily = poppinsFontFamily,
    ),
    bodySmall = TextStyle(
        fontWeight = FontWeight.Light,
        fontFamily = poppinsFontFamily,
    ),
)