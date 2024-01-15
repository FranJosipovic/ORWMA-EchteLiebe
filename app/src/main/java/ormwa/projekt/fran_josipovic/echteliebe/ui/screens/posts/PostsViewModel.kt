package ormwa.projekt.fran_josipovic.echteliebe.ui.screens.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ormwa.projekt.fran_josipovic.echteliebe.data.repositories.posts.PostsRepository
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.models.PostHomeScreen

class PostsViewModel(private val postsRepository: PostsRepository) : ViewModel() {
    val postsViewState: StateFlow<PostsHomeScreenUiState> = postsRepository.getPosts().map {
        PostsHomeScreenUiState.Success(it)
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PostsHomeScreenUiState.Loading
        )


    fun onPostVote(postId: String, userId: String) {
        viewModelScope.launch {
            postsRepository.toggleVote(postId, userId)
        }
    }
}

sealed class PostsHomeScreenUiState() {
    data class Success(val posts: List<PostHomeScreen>) : PostsHomeScreenUiState()
    data object Loading : PostsHomeScreenUiState()
}
