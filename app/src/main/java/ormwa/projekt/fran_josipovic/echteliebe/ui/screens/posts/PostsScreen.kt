package ormwa.projekt.fran_josipovic.echteliebe.ui.screens.posts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ormwa.projekt.fran_josipovic.echteliebe.R
import ormwa.projekt.fran_josipovic.echteliebe.auth.UserData
import ormwa.projekt.fran_josipovic.echteliebe.data.repositories.posts.PostsHomeScreenUiState
import ormwa.projekt.fran_josipovic.echteliebe.ui.components.Post
import ormwa.projekt.fran_josipovic.echteliebe.ui.components.ScreenPoster

@Composable
fun PostsScreen(viewModel: PostsViewModel, user: UserData?, onNavigate: (String) -> Unit) {
    val postsUiState by viewModel.postsViewState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ScreenPoster(img = R.drawable.homepage_poster, padding = 10)
        when (postsUiState) {
            is PostsHomeScreenUiState.Loading -> {
                Text(text = "Loading", color = Color.White)
            }

            is PostsHomeScreenUiState.Success -> {
                (postsUiState as PostsHomeScreenUiState.Success).posts.forEach {
                    Post(
                        modifier = Modifier.padding(10.dp),
                        post = it,
                        disableVote = user == null,
                        userId = user?.userId ?: "",
                        onNavigate = { onNavigate(it.id) }
                    ) {
                        viewModel.onPostVote(it.id, user?.userId!!)
                    }
                }
            }
        }
    }
}
