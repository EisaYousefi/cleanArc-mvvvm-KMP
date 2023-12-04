package data.api

import data.model.remorte.BirdImageRemote
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

const val BASE_URL = "https://sebi.io/demo-image-api/"
const val IMAGES = "pictures.json"

class ApiBirdsImageImpl(private val httpClient: HttpClient) : ApiBirdsImage {
    override suspend fun getBirdImages(): List<BirdImageRemote> =
        httpClient.get(IMAGES).body()
}