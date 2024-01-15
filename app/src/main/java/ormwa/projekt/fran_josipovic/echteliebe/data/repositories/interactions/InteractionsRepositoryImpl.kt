package ormwa.projekt.fran_josipovic.echteliebe.data.repositories.interactions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ormwa.projekt.fran_josipovic.echteliebe.data.services.interactions.InteractionsService
import ormwa.projekt.fran_josipovic.echteliebe.data.services.interactions.models.FanPoolInteraction
import ormwa.projekt.fran_josipovic.echteliebe.data.services.interactions.models.InteractionDetails
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.models.Comment

class InteractionsRepositoryImpl(private val interactionsService: InteractionsService) :
    InteractionsRepository {

    override fun getInteractions(): Flow<List<FanPoolInteraction>> = flow {
        val interactions = interactionsService.getInteractions()
        emit(interactions)
    }

    override suspend fun toggleVote(userId: String, interactionId: String, optionId: String) {
        interactionsService.vote(userId, interactionId, optionId)
    }

    override fun getInteractionDetails(id: String): Flow<InteractionDetails> = flow {
        interactionsService.getInteractionDetails(id).collect {
            emit(it)
        }
    }

    override suspend fun postNewComment(interactionId: String, comment: Comment) {
        interactionsService.postNewComment(interactionId, comment)
    }
}
