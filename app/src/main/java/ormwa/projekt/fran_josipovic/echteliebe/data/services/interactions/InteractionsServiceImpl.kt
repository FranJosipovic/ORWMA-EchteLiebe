package ormwa.projekt.fran_josipovic.echteliebe.data.services.interactions

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import ormwa.projekt.fran_josipovic.echteliebe.auth.UserData
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.Comment
import ormwa.projekt.fran_josipovic.echteliebe.ui.screens.interactions.FanPoolInteractionScreen
import ormwa.projekt.fran_josipovic.echteliebe.ui.screens.interactions.details.InteractionDetails
import ormwa.projekt.fran_josipovic.echteliebe.ui.screens.interactions.details.InteractionOption
import java.time.LocalDateTime

class InteractionsServiceImpl : InteractionsService {
    private val db = Firebase.firestore
    private val interactionsCollection = db.collection("Interaction")

    override suspend fun getInteractions(): List<FanPoolInteractionScreen> {
        val documents = interactionsCollection.get().await()

        val pools = mutableListOf<FanPoolInteractionScreen>()

        for (document in documents) {
            val pool = document.data
            pools.add(
                FanPoolInteractionScreen(
                    id = document.id,
                    title = pool["title"] as String,
                    img = pool["img"] as String
                )
            )
        }
        return pools
    }

    override fun getInteractionDetails(id: String): Flow<InteractionDetails> =
        callbackFlow {
            val listener = interactionsCollection.document(id).addSnapshotListener { value, error ->
                if (value == null) {
                    trySend(
                        InteractionDetails(
                            posterImage = "",
                            title = "Error",
                            options = emptyList(),
                            comments = emptyList()
                        )
                    )
                }
                val interactionHash = value!!.data!!
                Log.d("interaction", interactionHash.toString())
                val commentsHash = interactionHash["comments"] as? List<Map<String, Any>>
                val optionsHash = interactionHash["options"] as? List<Map<String, Any>>

                val commentsList = commentsHash?.map {
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
                            user?.get("userId") as? String,
                            user?.get("username") as? String,
                            user?.get("profilePictureUrl") as? String
                        ),
                        text = it["text"] as? String ?: ""
                    )
                } ?: emptyList()

                val optionsList = optionsHash?.map {
                    Log.d("interaction", it["votes"].toString())
                    InteractionOption(
                        id = it["id"] as String,
                        name = it["name"] as String,
                        votes = it["votes"] as? List<String> ?: emptyList()
                    )
                } ?: emptyList()
                Log.d("interaction", optionsList.toString())
                trySend(
                    InteractionDetails(
                        posterImage = interactionHash["posterImg"] as String,
                        title = interactionHash["title"] as String,
                        options = optionsList,
                        comments = commentsList
                    )
                )
            }
            awaitClose { listener.remove() }
        }

    override suspend fun vote(userId: String, interactionId: String, optionId: String) {
        try {
            val interactionDoc = interactionsCollection.document(interactionId).get().await()

            if (interactionDoc.exists()) {
                val interactionHash = interactionDoc.data

                if (interactionHash != null) {
                    val optionsHash = interactionHash["options"] as? List<HashMap<String, Any>>

                    val option = optionsHash?.find { it["id"] == optionId }

                    if (option != null) {
                        val votes = option["votes"] as? MutableList<String> ?: mutableListOf()

                        if (userId in votes) {
                            votes.remove(userId)
                        } else {
                            votes.add(userId)
                        }

                        interactionsCollection.document(interactionId)
                            .update("options", optionsHash.map {
                                if (it["id"] == optionId) {
                                    it.apply { put("votes", votes) }
                                } else {
                                    it
                                }
                            }).await()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun mapToUserData(
        userId: String?,
        username: String?,
        profilePictureUrl: String?
    ): UserData {
        return UserData(userId ?: "", username, profilePictureUrl)
    }

    override suspend fun postNewComment(interactionId: String, comment: Comment) {
        try {
            val postRef = interactionsCollection.document(interactionId)

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
}

