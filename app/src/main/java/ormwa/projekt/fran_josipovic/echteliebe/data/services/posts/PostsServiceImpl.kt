package ormwa.projekt.fran_josipovic.echteliebe.data.services.posts

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import ormwa.projekt.fran_josipovic.echteliebe.auth.UserData
import java.time.LocalDateTime


class PostsServiceImpl : PostsService {
    private val db = Firebase.firestore
    private val postsCollection = db.collection("Post")

    override fun posts(): Flow<List<Post>> = callbackFlow {
        val listenerRegistration = postsCollection.addSnapshotListener { value, e ->

            if (e != null) {
                Log.e("snapshot Listener", "Error: ${e.message}")
                return@addSnapshotListener
            }

            Log.d("snapshot Listener", "triggered")

            val postsList = mutableListOf<Post>()

            for (document in value!!) {
                val detailedText =
                    document.get("detailedText") as? List<HashMap<String, Any>> ?: emptyList()

                val detailedTextList = detailedText.map {
                    DetailedText(
                        it["subtitle"] as? String ?: "",
                        it["text"] as? String ?: ""
                    )
                }

                val comments = document.get("comments") as? List<Map<String, Any>>

                val commentsList = comments?.map {
                    Log.d("comment", it.toString())

                    val postedAtMap = it["postedAt"] as? Map<String, Any>

                    val postedAt = if (postedAtMap != null) {
                        LocalDateTime.of(
                            (postedAtMap["year"] as Long).toInt(),
                            (postedAtMap["monthValue"] as Long).toInt(),
                            (postedAtMap["dayOfMonth"] as Long).toInt(),
                            (postedAtMap["hour"] as Long).toInt(),
                            (postedAtMap["minute"] as Long).toInt(),
                            (postedAtMap["second"] as Long).toInt()
                        )
                    } else {
                        LocalDateTime.MIN
                    }

                    val user = it["user"] as? Map<*, *>

                    Comment(
                        id = it["id"] as? String ?: "",
                        postedAt = postedAt,
                        user = mapToUserData(
                            user!!["userId"] as? String,
                            user["username"] as? String,
                            user["profilePictureUrl"] as? String
                        ),
                        text = it["text"] as? String ?: ""
                    )
                }

                val img = document.getString("img") ?: ""
                val readingTime = document.getString("readingTime") ?: ""
                val shortIntro = document.getString("shortIntro") ?: ""
                val subtitle = document.getString("subtitle") ?: ""
                val title = document.getString("title") ?: ""
                val votes = document.get("votes") as? List<String> ?: emptyList()


                val post = commentsList?.let {
                    Post(
                        id = document.id,
                        it,
                        detailedTextList,
                        img,
                        readingTime,
                        shortIntro,
                        subtitle,
                        title,
                        votes
                    )
                }
                Log.d("Post", post.toString())
                if (post != null) {
                    postsList.add(post)
                }
            }

            trySend(postsList)
        }
        awaitClose { listenerRegistration.remove() }
    }

    override suspend fun postNewComment(postId: String, comment: Comment) {
        try {
            val postRef = postsCollection.document(postId)

            val currentComments =
                postRef.get().await().get("comments") as? List<HashMap<String, Any>> ?: emptyList()

            val newCommentData = hashMapOf(
                "id" to comment.id,
                "postedAt" to comment.postedAt,
                "user" to comment.user,
                "text" to comment.text
            )

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

    private fun mapToUserData(
        userId: String?,
        username: String?,
        profilePictureUrl: String?
    ): UserData {
        return UserData(userId ?: "", username, profilePictureUrl)
    }
}

