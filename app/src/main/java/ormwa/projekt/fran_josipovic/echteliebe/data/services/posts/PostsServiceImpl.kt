package ormwa.projekt.fran_josipovic.echteliebe.data.services.posts

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import ormwa.projekt.fran_josipovic.echteliebe.data.services.mappers.PostsServiceMapper
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.models.Comment
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.models.Post

class PostsServiceImpl(private val postsServiceMapper: PostsServiceMapper) : PostsService {
    private val db = Firebase.firestore
    private val postsCollection = db.collection("Post")

    override fun postsFlow(): Flow<List<Post>> = callbackFlow {
        val listenerRegistration = postsCollection.addSnapshotListener { value, e ->

            if (e != null) {
                Log.e("snapshot Listener", "Error: ${e.message}")
                return@addSnapshotListener
            }

            val postsList = value?.let { postsServiceMapper.toPostsList(it) } ?: emptyList()

            trySend(postsList)
        }
        awaitClose { listenerRegistration.remove() }
    }

    override suspend fun postNewComment(postId: String, comment: Comment) {
        try {
            val postRef = postsCollection.document(postId)

            val currentComments =
                postRef.get().await().get("comments") as? List<HashMap<String, Any>>
                    ?: emptyList()

            val newCommentData = postsServiceMapper.toCommentHash(comment)

            val updatedComments = currentComments.toMutableList().apply {
                add(newCommentData)
            }

            postRef.update("comments", updatedComments).await()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun toggleVote(postId: String, userId: String) {
        val postRef = postsCollection.document(postId)

        val documentSnapshot = postRef.get().await()
        val currentVotes = documentSnapshot.get("votes") as? List<String> ?: emptyList()

        val updatedVotes = if (currentVotes.contains(userId)) {
            FieldValue.arrayRemove(userId)
        } else {
            FieldValue.arrayUnion(userId)
        }

        postRef.update("votes", updatedVotes).await()
    }
}
