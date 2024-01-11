package ormwa.projekt.fran_josipovic.echteliebe.data.repositories.interactions

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ormwa.projekt.fran_josipovic.echteliebe.data.services.interactions.InteractionsService
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.Comment
import ormwa.projekt.fran_josipovic.echteliebe.ui.screens.interactions.FanPoolInteractionScreen
import ormwa.projekt.fran_josipovic.echteliebe.ui.screens.interactions.details.InteractionDetails

class InteractionsRepositoryImpl(private val interactionsService: InteractionsService) :
    InteractionsRepository {

    override fun getInteractions(): Flow<List<FanPoolInteractionScreen>> = flow {
        val interactions = interactionsService.getInteractions()
        emit(interactions)
    }

    override suspend fun toggleVote(userId:String, interactionId:String, optionId:String){
        interactionsService.vote(userId, interactionId, optionId)
    }

    override fun getInteractionDetails(id: String): Flow<InteractionDetails> = flow {
        interactionsService.getInteractionDetails(id).collect {
            Log.d("interactions repository","okinuo")
            emit(it)
        }
    }

    override suspend fun postNewComment(interactionId:String,comment: Comment){
        interactionsService.postNewComment(interactionId,comment)
    }
}
