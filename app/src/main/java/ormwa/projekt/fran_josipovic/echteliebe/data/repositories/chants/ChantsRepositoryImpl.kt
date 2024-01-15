package ormwa.projekt.fran_josipovic.echteliebe.data.repositories.chants

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ormwa.projekt.fran_josipovic.echteliebe.data.repositories.chants.models.AlbumTrack
import ormwa.projekt.fran_josipovic.echteliebe.data.services.chants.ChantsService
import ormwa.projekt.fran_josipovic.echteliebe.data.services.chants.models.AlbumResponse
import ormwa.projekt.fran_josipovic.echteliebe.data.utils.Formater

class ChantsRepositoryImpl(private val chantsService: ChantsService) : ChantsRepository {

    override fun getTracks(): Flow<List<AlbumTrack>> = flow {
        val albumResponse: AlbumResponse = chantsService.getAalbum()
        val albumTrackLists: List<AlbumTrack> = albumResponse.tracks.items.map { track ->
            AlbumTrack(
                trackNumber = track.trackNumber,
                name = track.trackName,
                author = track.artists.joinToString { it.name }, // Assuming Artist has a 'name' property
                playTime = Formater.formatDuration(track.durationInMs),
                imageUrl = albumResponse.images.find { image -> image.height < 100 }?.url ?: "",
                musicPlayerLink = track.previewUrl
            )
        }
        emit(albumTrackLists)
    }
}
