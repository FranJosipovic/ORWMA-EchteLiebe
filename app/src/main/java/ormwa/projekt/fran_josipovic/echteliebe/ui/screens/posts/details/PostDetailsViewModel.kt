package ormwa.projekt.fran_josipovic.echteliebe.ui.screens.posts.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ormwa.projekt.fran_josipovic.echteliebe.auth.UserData
import ormwa.projekt.fran_josipovic.echteliebe.data.repositories.posts.PostsRepository
import ormwa.projekt.fran_josipovic.echteliebe.data.repositories.posts.SinglePostUiState
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.Comment
import java.time.LocalDateTime
import java.util.UUID

class PostDetailsViewModel(
    private val postsRepository: PostsRepository,
    private val postId: String
) : ViewModel() {
    val postDetailsViewState: StateFlow<SinglePostUiState> = postsRepository.post(postId).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SinglePostUiState.Loading
    )

    val postComments: StateFlow<List<Comment>> = postsRepository.postComments(postId).stateIn(
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