package ormwa.projekt.fran_josipovic.echteliebe.ui.screens.posts.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ormwa.projekt.fran_josipovic.echteliebe.auth.UserData
import ormwa.projekt.fran_josipovic.echteliebe.data.repositories.posts.PostsRepository
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.models.Comment
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.models.PostDetailsScreen
import java.time.LocalDateTime
import java.util.UUID

class PostDetailsViewModel(
    private val postsRepository: PostsRepository,
    private val postId: String
) : ViewModel() {
    val postDetailsViewState: StateFlow<PostDetailsState> = postsRepository.getPostDetails(postId)
        .map {
            PostDetailsState.Success(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PostDetailsState.Loading
        )

    val postComments: StateFlow<List<Comment>> = postsRepository.getPostComments(postId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun postNewComment(user: UserData, text: String) {
        viewModelScope.launch {
            postsRepository.postNewComment(
                postId,
                Comment(UUID.randomUUID().toString(), LocalDateTime.now(), user, text)
            )
        }
    }

    fun onPostVote(userId: String) {
        viewModelScope.launch {
            postsRepository.toggleVote(postId, userId)
        }
    }
}

sealed class PostDetailsState() {
    data class Success(val post: PostDetailsScreen) : PostDetailsState()
    data object Loading : PostDetailsState()
}
