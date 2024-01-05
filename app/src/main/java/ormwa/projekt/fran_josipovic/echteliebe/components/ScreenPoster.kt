package ormwa.projekt.fran_josipovic.echteliebe.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ormwa.projekt.fran_josipovic.echteliebe.R

@Composable
fun ScreenPoster(@DrawableRes img: Int,padding:Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
    ) {
        Image(
            painter = painterResource(id = img),
            contentDescription = "poster image",
            modifier = Modifier
                .padding(padding.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
    }
}
@Preview
@Composable
fun ScreenPosterPreview() {
    ScreenPoster(img = R.drawable.homepage_poster,10)
}