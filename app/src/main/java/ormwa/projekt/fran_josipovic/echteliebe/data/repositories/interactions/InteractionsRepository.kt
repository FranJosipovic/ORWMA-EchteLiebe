package ormwa.projekt.fran_josipovic.echteliebe.data.repositories.interactions

import kotlinx.coroutines.flow.Flow
import ormwa.projekt.fran_josipovic.echteliebe.data.services.interactions.models.FanPoolInteraction
import ormwa.projekt.fran_josipovic.echteliebe.data.services.interactions.models.InteractionDetails
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.models.Comment

interface InteractionsRepository {
    fun getInteractions(): Flow<List<FanPoolInteraction>>
    fun getInteractionDetails(id: String): Flow<InteractionDetails>
    suspend fun toggleVote(userId:String,interactionId:String,optionId:String)
    suspend fun postNewComment(interactionId:String,comment: Comment)
}
