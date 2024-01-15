package ormwa.projekt.fran_josipovic.echteliebe.data.repositories.posts

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.PostsService
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.models.Comment
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.models.PostDetailsScreen
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.models.PostHomeScreen

class PostsRepositoryImpl(private val postsService: PostsService) : PostsRepository {

    override fun getPosts(): Flow<List<PostHomeScreen>> = flow {
        postsService.postsFlow().collect {
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
            emit(posts)
        }
    }

    override fun getPostDetails(postId: String): Flow<PostDetailsScreen> = flow {
        postsService.postsFlow().collect {
            val post = it.first { post -> post.id == postId }
            emit(
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
        }
    }

    override fun getPostComments(postId: String): Flow<List<Comment>> = flow {
        postsService.postsFlow().collect {
            val post = it.first { post -> post.id == postId }
            emit(post.comments)
        }
    }

    override suspend fun postNewComment(postId: String, comment: Comment) {
        postsService.postNewComment(postId, comment)
    }

    override suspend fun toggleVote(postId: String, userId: String) {
        postsService.toggleVote(postId, userId)
    }
}
