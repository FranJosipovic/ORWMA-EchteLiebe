package ormwa.projekt.fran_josipovic.echteliebe.data.services.posts

import kotlinx.coroutines.flow.Flow
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.models.Comment
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.models.Post

interface PostsService {
    fun postsFlow(): Flow<List<Post>>
    suspend fun postNewComment(postId: String, comment: Comment): Unit
    suspend fun toggleVote(postId: String, userId: String): Unit
}
