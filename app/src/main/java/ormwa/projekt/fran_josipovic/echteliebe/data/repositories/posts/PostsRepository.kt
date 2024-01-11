package ormwa.projekt.fran_josipovic.echteliebe.data.repositories.posts

import kotlinx.coroutines.flow.Flow
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.Comment

interface PostsRepository {
    fun posts(): Flow<PostsHomeScreenUiState>
    fun post(postId:String):Flow<SinglePostUiState>
    fun postComments(postId: String): Flow<List<Comment>>

    suspend fun postNewComment(postId:String,comment: Comment)
    suspend fun toggleVote(postId: String, userId: String): Unit
}