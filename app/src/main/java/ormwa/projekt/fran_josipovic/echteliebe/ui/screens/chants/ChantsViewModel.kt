package ormwa.projekt.fran_josipovic.echteliebe.ui.screens.chants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ormwa.projekt.fran_josipovic.echteliebe.data.repositories.chants.ChantsRepository
import ormwa.projekt.fran_josipovic.echteliebe.data.repositories.chants.ChantsUiState

class ChantsViewModel(private val repository: ChantsRepository) : ViewModel() {

    val tracksViewState: StateFlow<ChantsUiState> = repository.getTracks()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ChantsUiState.Loading
        )

    // Call this function to fetch tracks
    fun fetchTracks() {
        viewModelScope.launch {
            repository.getTracks()
        }
    }
}