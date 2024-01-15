package ormwa.projekt.fran_josipovic.echteliebe.ui.screens.chants

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import ormwa.projekt.fran_josipovic.echteliebe.R
import ormwa.projekt.fran_josipovic.echteliebe.ui.components.MusicPlayer
import ormwa.projekt.fran_josipovic.echteliebe.ui.components.ScreenPoster
import ormwa.projekt.fran_josipovic.echteliebe.ui.theme.EchteLiebeTheme

@Composable
fun ChantsScreen(viewModel: ChantsViewModel) {
    val chantsUiState: ChantsUiState by viewModel.tracksViewState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ScreenPoster(img = R.drawable.chantspage_poster, padding = 10)
        when (chantsUiState) {
            is ChantsUiState.Loading -> {
                Text(text = "Loading", modifier = Modifier.padding(10.dp), color = Color.White)
            }

            is ChantsUiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    (chantsUiState as ChantsUiState.Success).albumTracks.forEach {
                        ChantCard(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                            orderNumber = it.trackNumber,
                            name = it.name,
                            author = it.author,
                            playTime = it.playTime,
                            imageUrl = it.imageUrl,
                            musicPlayerLink = it.musicPlayerLink
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChantCard(
    modifier: Modifier,
    orderNumber: Int,
    name: String,
    author: String,
    playTime: String,
    imageUrl: String,
    musicPlayerLink: String
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(
            //contentColor = MaterialTheme.colorScheme.background,
            containerColor = MaterialTheme.colorScheme.tertiary
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = orderNumber.toString() + ".",
                color = Color.White,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            SubcomposeAsyncImage(
                modifier = Modifier
                    .size(width = 64.dp, height = 64.dp)
                    .padding(horizontal = 10.dp),
                model = imageUrl,
                contentDescription = "img",
                loading = { CircularProgressIndicator() },
                contentScale = ContentScale.Fit
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(text = name, color = Color.White)
                Text(text = author, color = Color.LightGray, fontSize = 13.sp)
            }
            Text(
                text = playTime,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            MusicPlayer(url = musicPlayerLink)
        }
    }
}

@Preview(showBackground = false)
@Composable
fun ChantCardPreview() {
    EchteLiebeTheme {
        ChantCard(
            modifier = Modifier.padding(10.dp, 20.dp),
            orderNumber = 1,
            name = "klapen klapne",
            author = "BVB Ultras",
            playTime = "1:17",
            imageUrl = "https://i.scdn.co/image/ab67616d00004851dba3bcf65476f720855c8422",
            musicPlayerLink = ""
        )
    }
}
