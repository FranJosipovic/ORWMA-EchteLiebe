package ormwa.projekt.fran_josipovic.echteliebe.ui.screens.interactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ormwa.projekt.fran_josipovic.echteliebe.data.repositories.interactions.InteractionsRepository

class InteractionsViewModel(private val interactionsRepository: InteractionsRepository) :
    ViewModel() {
    val interactionsScreenViewState: StateFlow<InteractionScreenState> =
        interactionsRepository.getInteractions().map {
            InteractionScreenState.Success(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = InteractionScreenState.Loading
        )
}

sealed class InteractionScreenState {
    data class Success(val data: List<FanPoolInteractionScreen>) : InteractionScreenState()
    data object Loading : InteractionScreenState()
}