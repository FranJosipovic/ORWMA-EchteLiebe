package ormwa.projekt.fran_josipovic.echteliebe.data.services.interactions

import kotlinx.coroutines.flow.Flow
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.Comment
import ormwa.projekt.fran_josipovic.echteliebe.ui.screens.interactions.FanPoolInteractionScreen
import ormwa.projekt.fran_josipovic.echteliebe.ui.screens.interactions.details.InteractionDetails

interface InteractionsService {
    suspend fun getInteractions(): List<FanPoolInteractionScreen>
    fun getInteractionDetails(id: String): Flow<InteractionDetails>
    suspend fun vote(userId: String, interactionId: String, optionId: String)
    suspend fun postNewComment(interactionId: String, comment: Comment)
}