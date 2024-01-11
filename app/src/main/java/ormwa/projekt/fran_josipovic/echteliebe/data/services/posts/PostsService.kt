package ormwa.projekt.fran_josipovic.echteliebe.data.services.posts

import kotlinx.coroutines.flow.Flow

interface PostsService {
    fun posts(): Flow<List<Post>>
    suspend fun postNewComment(postId: String, comment: Comment): Unit
    suspend fun toggleVote(postId: String, userId: String): Unit
}