package ormwa.projekt.fran_josipovic.echteliebe.data.repositories.chants

import kotlinx.coroutines.flow.Flow

interface ChantsRepository {
    //val tracksViewStateFlow: Flow<List<TrackViewState>>;
    fun getTracks():Flow<ChantsUiState>;
}