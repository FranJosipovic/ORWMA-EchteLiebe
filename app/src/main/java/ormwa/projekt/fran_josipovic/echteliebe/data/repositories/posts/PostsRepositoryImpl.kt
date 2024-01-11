package ormwa.projekt.fran_josipovic.echteliebe.data.repositories.posts

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.Comment
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.PostDetailsScreen
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.PostHomeScreen
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.PostsService

class PostsRepositoryImpl(private val postsService: PostsService) : PostsRepository {

    override fun posts(): Flow<PostsHomeScreenUiState> = flow {
        postsService.posts().collect {
            val posts = it.map { post ->
                PostHomeScreen(
                    post.id,
                    post.img,
                    post.readingTime,
                    post.shortIntro,
                    post.subtitle,
                    post.title,
                    post.votes,
                )
            }
            emit(PostsHomeScreenUiState.Success(posts))
        }
    }

    override fun post(postId: String): Flow<SinglePostUiState> = flow {

        postsService.posts().collect {
            val post = it.first { post -> post.id == postId }
            emit(
                SinglePostUiState.Success(
                    PostDetailsScreen(
                        post.id,
                        post.detailedText,
                        post.img,
                        post.readingTime,
                        post.subtitle,
                        post.title,
                        post.votes
                    )
                )
            )
        }
    }

    override fun postComments(postId: String): Flow<List<Comment>> = flow {

        postsService.posts().collect {
            val post = it.first { post -> post.id == postId }
            emit(post.comments)
        }
    }

    override suspend fun postNewComment(postId: String, comment: Comment) {
        postsService.postNewComment(postId, comment)
    }

    override suspend fun toggleVote(postId: String, userId: String) {
        Log.d("toggle vote", "triggered")
        postsService.toggleVote(postId, userId)
    }
}

sealed class PostsHomeScreenUiState() {
    data class Success(val posts: List<PostHomeScreen>) : PostsHomeScreenUiState()
    data object Loading : PostsHomeScreenUiState()
}

sealed class SinglePostUiState() {
    data class Success(val post: PostDetailsScreen) : SinglePostUiState()
    data object Loading : SinglePostUiState()
}