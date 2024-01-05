package ormwa.projekt.fran_josipovic.echteliebe.data.repositories

import kotlinx.coroutines.flow.Flow

interface ChantsRepository {
    //val tracksViewStateFlow: Flow<List<TrackViewState>>;
    fun getTracks():Flow<ChantsUiState>;
}