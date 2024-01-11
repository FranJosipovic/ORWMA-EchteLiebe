package ormwa.projekt.fran_josipovic.echteliebe.data.services.interactions

import ormwa.projekt.fran_josipovic.echteliebe.ui.screens.interactions.FanPoolInteractionScreen

interface InteractionsService {
    suspend fun getInteractions(): List<FanPoolInteractionScreen>
}