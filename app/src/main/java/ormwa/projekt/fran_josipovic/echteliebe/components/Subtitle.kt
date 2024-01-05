package ormwa.projekt.fran_josipovic.echteliebe.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun Subtitle(modifier: Modifier = Modifier,text: String, fontSize: TextUnit = 26.sp) {
    Text(
        text = text,
        modifier = modifier,
        color = Color.White,
        fontSize = fontSize,
        fontWeight = FontWeight.Bold
    )
}
@Preview
@Composable
fun SubtitlePrev() {
    Subtitle(text = "Borussia Dortmund is the intense football experience")
}