package ormwa.projekt.fran_josipovic.echteliebe.data.services.chants

import ormwa.projekt.fran_josipovic.echteliebe.data.services.chants.models.AlbumResponse
import ormwa.projekt.fran_josipovic.echteliebe.data.services.chants.models.TrackDetailsResponse

interface ChantsService {
    suspend fun getAalbum(): AlbumResponse;
    suspend fun getTracksDetails(id:String): TrackDetailsResponse;
}
