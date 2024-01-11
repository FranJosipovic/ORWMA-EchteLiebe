package ormwa.projekt.fran_josipovic.echteliebe.ui.screens.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ormwa.projekt.fran_josipovic.echteliebe.data.repositories.posts.PostsHomeScreenUiState
import ormwa.projekt.fran_josipovic.echteliebe.data.repositories.posts.PostsRepository

class PostsViewModel(private val postsRepository: PostsRepository) : ViewModel(){
    val postsViewState: StateFlow<PostsHomeScreenUiState> = postsRepository.posts()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PostsHomeScreenUiState.Loading
        )

    fun onPostVote(postId:String,userId:String){
        viewModelScope.launch {
            postsRepository.toggleVote(postId,userId)
        }
    }
}
