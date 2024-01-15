package ormwa.projekt.fran_josipovic.echteliebe.data.services.mappers

import android.util.Log
import com.google.firebase.firestore.QuerySnapshot
import ormwa.projekt.fran_josipovic.echteliebe.data.services.interactions.models.FanPoolInteraction
import ormwa.projekt.fran_josipovic.echteliebe.data.services.interactions.models.InteractionDetails
import ormwa.projekt.fran_josipovic.echteliebe.data.services.interactions.models.InteractionOption

class InteractionsServiceMapper : BaseMapper() {

    private fun toInteractionOptionsList(optionsHash: List<Map<String, Any>>): List<InteractionOption> {
        return optionsHash.map {
            Log.d("interaction", it["votes"].toString())
            InteractionOption(
                id = it["id"] as String,
                name = it["name"] as String,
                votes = it["votes"] as? List<String> ?: emptyList()
            )
        }
    }

    fun toInteractionDetails(interactionHash: Map<String, Any>): InteractionDetails {
        val commentsHash = interactionHash["comments"] as? List<Map<String, Any>>
        val optionsHash = interactionHash["options"] as? List<Map<String, Any>>

        val commentsList = commentsHash?.let { toCommentsList(it) } ?: emptyList()

        val optionsList = optionsHash?.let { toInteractionOptionsList(it) } ?: emptyList()
        return InteractionDetails(
            posterImage = interactionHash["posterImg"] as String,
            title = interactionHash["title"] as String,
            options = optionsList.sortedByDescending { it.votes.size },
            comments = commentsList
        )
    }

    fun toFanPoolInteractionsList(documents: QuerySnapshot): List<FanPoolInteraction> {
        return documents.map {
            val pool = it.data
            FanPoolInteraction(
                id = it.id,
                title = pool["title"] as String,
                img = pool["img"] as String
            )
        }
    }
}
