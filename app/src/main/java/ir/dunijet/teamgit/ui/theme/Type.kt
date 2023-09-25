package ir.dunijet.teamgit.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ir.dunijet.teamgit.R

val VazirFont = FontFamily(Font(R.font.vazir_regular))

val Typography = Typography(

    // detail text
    body1 = TextStyle(
        fontFamily = VazirFont,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 32.sp
    ) ,

    // filtering detail text , navigation drawer section title
    body2 = TextStyle(
        fontFamily = VazirFont,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        lineHeight = 22.sp
    ),

    // tags in filtering
    caption = TextStyle(
        fontFamily = VazirFont,
        fontWeight = FontWeight.W500,
        fontSize = 13.sp,
        lineHeight = 32.sp
    ),

    // story insta
    subtitle1 = TextStyle(
        fontFamily = VazirFont,
        fontWeight = FontWeight.W700,
        fontSize = 14.sp ,
        lineHeight = 22.sp
    ),

    // navigation drawer title text
    subtitle2 = TextStyle(
        fontFamily = VazirFont,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        lineHeight = 22.sp
    ),

    // title article , no article
    h5 = TextStyle(
        fontFamily = VazirFont,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp ,
        lineHeight = 26.sp
    ),

    // toolbar
    h6 = TextStyle(
        fontFamily = VazirFont,
        fontWeight = FontWeight.W700,
        fontSize = 18.sp ,
        lineHeight = 28.sp
    ),

    // upper image text , filtering , category
    overline = TextStyle(
        fontFamily = VazirFont,
        fontWeight = FontWeight.W500,
        fontSize = 12.sp ,
        lineHeight = 16.sp
    ),

    // button and toast text
    button = TextStyle(
        fontFamily = VazirFont,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp ,
        lineHeight = 20.sp
    )

    //
    , h4 = TextStyle(
        fontFamily = VazirFont,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp ,
        lineHeight = 25.sp
    )

)