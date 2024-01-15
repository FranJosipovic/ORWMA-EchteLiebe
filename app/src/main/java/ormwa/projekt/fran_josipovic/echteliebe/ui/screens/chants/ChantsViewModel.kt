package ormwa.projekt.fran_josipovic.echteliebe.ui.screens.chants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ormwa.projekt.fran_josipovic.echteliebe.data.repositories.chants.ChantsRepository
import ormwa.projekt.fran_josipovic.echteliebe.data.repositories.chants.models.AlbumTrack

class ChantsViewModel(repository: ChantsRepository) : ViewModel() {
    val tracksViewState: StateFlow<ChantsUiState> = repository.getTracks()
        .map {
            ChantsUiState.Success(it)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ChantsUiState.Loading
        )
}

sealed class ChantsUiState {
    data class Success(val albumTracks: List<AlbumTrack>) : ChantsUiState()
    data object Loading : ChantsUiState()
}
