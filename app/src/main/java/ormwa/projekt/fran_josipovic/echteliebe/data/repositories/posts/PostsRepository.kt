package ormwa.projekt.fran_josipovic.echteliebe.data.repositories.posts

import kotlinx.coroutines.flow.Flow
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.models.Comment
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.models.PostDetailsScreen
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.models.PostHomeScreen

interface PostsRepository {
    fun getPosts(): Flow<List<PostHomeScreen>>
    fun getPostDetails(postId: String): Flow<PostDetailsScreen>
    fun getPostComments(postId: String): Flow<List<Comment>>
    suspend fun postNewComment(postId:String,comment: Comment)
    suspend fun toggleVote(postId: String, userId: String): Unit
}
