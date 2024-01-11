package ormwa.projekt.fran_josipovic.echteliebe.data.repositories.interactions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ormwa.projekt.fran_josipovic.echteliebe.data.services.interactions.InteractionsService
import ormwa.projekt.fran_josipovic.echteliebe.ui.screens.interactions.FanPoolInteractionScreen

class InteractionsRepositoryImpl(private val interactionsService: InteractionsService) :
    InteractionsRepository {

    override fun getInteractions(): Flow<List<FanPoolInteractionScreen>> = flow {
        val interactions = interactionsService.getInteractions()
        emit(interactions)
    }
}