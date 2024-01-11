package ormwa.projekt.fran_josipovic.echteliebe.data.repositories.chants

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ormwa.projekt.fran_josipovic.echteliebe.data.services.chants.ChantsService
import ormwa.projekt.fran_josipovic.echteliebe.data.services.models.AlbumResponse

data class TrackViewState(
    val trackNumber: Int,
    val name: String,
    val author: String,
    val playTime: String,
    val imageUrl:String,
    val musicPlayerLink: String
)

class ChantsRepositoryImpl(private val chantsService: ChantsService) : ChantsRepository {

    override fun getTracks(): Flow<ChantsUiState> = flow {
        val albumResponse: AlbumResponse = chantsService.getAalbum()
        val trackViewStateList: List<TrackViewState> = albumResponse.tracks.items.map { track ->

            TrackViewState(
                trackNumber = track.trackNumber,
                name = track.trackName,
                author = track.artists.joinToString { it.name }, // Assuming Artist has a 'name' property
                playTime = formatDuration(track.durationInMs),
                imageUrl = albumResponse.images.find { image -> image.height < 100 }?.url ?: "",
                musicPlayerLink = track.previewUrl
            )
        }
        emit(ChantsUiState.Success(trackViewStateList))
    }

    private fun formatDuration(durationInMs: Long): String {
        val seconds = durationInMs / 1000
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return "$minutes:${String.format("%02d", remainingSeconds)}"
    }
}

sealed class ChantsUiState {
    data class Success(val tracks: List<TrackViewState>) : ChantsUiState()
    data object Loading : ChantsUiState()
}