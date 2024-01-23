package ormwa.projekt.fran_josipovic.echteliebe.ui.screens.interactions.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import ormwa.projekt.fran_josipovic.echteliebe.auth.UserData
import ormwa.projekt.fran_josipovic.echteliebe.data.services.interactions.models.InteractionOption
import ormwa.projekt.fran_josipovic.echteliebe.ui.screens.posts.details.CommentsSection



@Composable
fun InteractionsDetailsScreen(
    interactionDetailsViewModel: InteractionDetailsViewModel,
    userData: UserData
) {

    val state by interactionDetailsViewModel.interactionDetails.collectAsState()
    when (state) {
        is InteractionsScreenUiState.Loading -> {
            Text(text = "Loading", color = Color.White)
        }

        is InteractionsScreenUiState.Success -> {
            val interactionDetails = (state as InteractionsScreenUiState.Success).details

            val totalVotes = interactionDetails.options.flatMap { it.votes }.count()

            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(330.dp),
                    model = interactionDetails.posterImage,
                    contentDescription = "img",
                    loading = { CircularProgressIndicator() },
                    contentScale = ContentScale.FillBounds
                )
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {
                    Text(
                        text = interactionDetails.title,
                        modifier = Modifier.padding(vertical = 15.dp),
                        color = Color.White
                    )
                    Text(
                        text = "Who was your MOTM?",
                        modifier = Modifier.padding(vertical = 15.dp),
                        color = Color.White
                    )
                    Text(
                        text = "Vote",
                        modifier = Modifier.padding(vertical = 15.dp),
                        color = Color.White
                    )
                    interactionDetails.options.forEach {
                        VoteOption(it, totalVotes) {
                            interactionDetailsViewModel.toggleVote(userData.userId, it.id)
                        }
                    }
                    CommentsSection(comments = interactionDetails.comments) {
                        interactionDetailsViewModel.postNewComment(
                            userData,
                            it
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun VoteOption(option: InteractionOption, totalVotes: Int, onVote: () -> Unit) {

    val progress =
        (option.votes.size.toFloat() / totalVotes).coerceIn(0f, 1f)

    Column(Modifier.fillMaxWidth()) {
        Text(text = option.name, color = Color.White, modifier = Modifier.padding(vertical = 3.dp))
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LinearProgressIndicator(
                modifier = Modifier
                    .weight(1f)
                    .height(20.dp),
                progress = progress,
                strokeCap = StrokeCap.Round
            )
            Text(text = String.format("%.2f%%", progress * 100), color = Color.White)
            Button(onClick = { onVote() }) {
                Text(text = "Vote")
            }
        }
    }
}
