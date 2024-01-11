package ormwa.projekt.fran_josipovic.echteliebe.data.repositories.interactions

import kotlinx.coroutines.flow.Flow
import ormwa.projekt.fran_josipovic.echteliebe.ui.screens.interactions.FanPoolInteractionScreen

interface InteractionsRepository {
    fun getInteractions(): Flow<List<FanPoolInteractionScreen>>
}