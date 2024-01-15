package ormwa.projekt.fran_josipovic.echteliebe.data.services.interactions

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import ormwa.projekt.fran_josipovic.echteliebe.data.services.interactions.models.FanPoolInteraction
import ormwa.projekt.fran_josipovic.echteliebe.data.services.interactions.models.InteractionDetails
import ormwa.projekt.fran_josipovic.echteliebe.data.services.mappers.InteractionsServiceMapper
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.models.Comment

class InteractionsServiceImpl(private val interactionsServiceMapper: InteractionsServiceMapper) :
    InteractionsService {
    private val db = Firebase.firestore
    private val interactionsCollection = db.collection("Interaction")

    override suspend fun getInteractions(): List<FanPoolInteraction> {
        val documents = interactionsCollection.get().await()

        return interactionsServiceMapper.toFanPoolInteractionsList(documents)
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
                val interactionHash = value?.data ?: hashMapOf()

                trySend(interactionsServiceMapper.toInteractionDetails(interactionHash))
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

    override suspend fun postNewComment(interactionId: String, comment: Comment) {
        try {
            val postRef = interactionsCollection.document(interactionId)

            val currentComments =
                postRef.get().await().get("comments") as? List<HashMap<String, Any>> ?: emptyList()

            val newCommentData = interactionsServiceMapper.toCommentHash(comment)

            val updatedComments = currentComments.toMutableList().apply {
                add(newCommentData)
            }

            postRef.update("comments", updatedComments).await()
        } catch (e: Exception) {
            throw e
        }
    }
}
