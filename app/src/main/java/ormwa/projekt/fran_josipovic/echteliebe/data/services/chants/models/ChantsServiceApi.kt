package ormwa.projekt.fran_josipovic.echteliebe.data.services.chants.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenResponse(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("token_type")
    val tokenType: String,
    @SerialName("expires_in")
    val expiresIn: Long
)

@Serializable
data class AlbumResponse(
    @SerialName("total_tracks")
    val totalItems: Int,
    @SerialName("tracks")
    val tracks: Tracks,
    @SerialName("images")
    val images: List<TrackImage>
)

@Serializable
data class Tracks(
    @SerialName("items")
    val items: List<Track>
)
@Serializable
data class Track(
    @SerialName("artists")
    val artists: List<Artist>,
    @SerialName("duration_ms")
    val durationInMs: Long,
    @SerialName("external_urls")
    val externalUrl: ExternalUrl,
    @SerialName("name")
    val trackName: String,
    @SerialName("preview_url")
    val previewUrl: String,
    @SerialName("track_number")
    val trackNumber: Int,
    @SerialName("id")
    val trackId: String
)

@Serializable
data class Artist(
    @SerialName("name")
    val name: String
)

@Serializable
data class ExternalUrl(
    @SerialName("spotify")
    val url: String
)

@Serializable
data class TrackDetailsResponse(
    @SerialName("album")
    val album: Album,
)

@Serializable
data class Album(
    @SerialName("images")
    val images: List<TrackImage>
)

@Serializable
data class TrackImage(
    @SerialName("url")
    val url: String,
    @SerialName("height")
    val height: Int,
    @SerialName("width")
    val width: Int
)
