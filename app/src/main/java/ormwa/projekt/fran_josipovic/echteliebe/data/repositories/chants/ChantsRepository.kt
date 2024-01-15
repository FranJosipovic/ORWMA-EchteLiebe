package ormwa.projekt.fran_josipovic.echteliebe.data.repositories.chants

import kotlinx.coroutines.flow.Flow
import ormwa.projekt.fran_josipovic.echteliebe.data.repositories.chants.models.AlbumTrack

interface ChantsRepository {
    fun getTracks(): Flow<List<AlbumTrack>>
}
