package ormwa.projekt.fran_josipovic.echteliebe.data.services.chants

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.util.InternalAPI
import ormwa.projekt.fran_josipovic.echteliebe.data.services.models.AccessTokenResponse
import ormwa.projekt.fran_josipovic.echteliebe.data.services.models.AlbumResponse
import ormwa.projekt.fran_josipovic.echteliebe.data.services.models.TrackDetailsResponse
import java.time.LocalDateTime

class ChantsServiceImpl(val httpClient: HttpClient) : ChantsService {

    private val albumTrackId: String = "3tcHT8rV6NYBjHErOhTV8m"
    private val albumTrackMarket: String = "HR"

    private var expireDateTime: LocalDateTime = LocalDateTime.now()
    private var accessToken: String? = null

    suspend fun UpdateeAccessToken(): Unit {
        val accessTokenResponse: AccessTokenResponse = GetAccessToken()
        expireDateTime = LocalDateTime.now().plusSeconds(accessTokenResponse.expiresIn)
        accessToken = accessTokenResponse.accessToken
    }

    @OptIn(InternalAPI::class)
    private suspend fun GetAccessToken(): AccessTokenResponse {
        val res = httpClient.request("https://accounts.spotify.com/api/token") {
            method = HttpMethod.Post
            headers {
                append(HttpHeaders.ContentType, "application/x-www-form-urlencoded")
            }
            body = FormDataContent(Parameters.build {
                append("grant_type", "client_credentials")
                append("client_id", "ba1b1c5caa3847e79629d0724e4edb2b")
                append("client_secret", "aa55a23cdd704c3cbd9ae8b5a538b227")
            })
        }
        return res.body()
    }

    override suspend fun getAalbum(): AlbumResponse {
        if (expireDateTime < LocalDateTime.now() || accessToken == null) {
            UpdateeAccessToken()
        }
        val res =
            httpClient.request("https://api.spotify.com/v1/albums/$albumTrackId?market=$albumTrackMarket") {
                method = HttpMethod.Get
                headers {
                    append(HttpHeaders.Authorization, "Bearer $accessToken")
                }
            }
        return res.body()
    }

    override suspend fun getTracksDetails(id:String): TrackDetailsResponse {
        if (expireDateTime < LocalDateTime.now() || accessToken == null) {
            UpdateeAccessToken()
        }
        val res =
            httpClient.request("https://api.spotify.com/v1/tracks/$id?market=HR") {
                method = HttpMethod.Get
                headers {
                    append(HttpHeaders.Authorization, "Bearer $accessToken")
                }
            }
        return res.body()
    }
}
