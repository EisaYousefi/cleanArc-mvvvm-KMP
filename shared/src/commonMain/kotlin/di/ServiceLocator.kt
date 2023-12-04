package di

import data.api.ApiBirdsImageImpl
import data.api.BASE_URL
import data.repository.BirdImageImageRepositoryImpl
import data.repository.BirdsImageImageDataSourceRemoteImpl
import domain.usecase.GetBirdsImage
import feature.viewmodel.BirdsViewModelFactory
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO


internal object ServiceLocator {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO


    private val httpClient: HttpClient by lazy {

        HttpClient() {

            expectSuccess = true

            //config Client Serialization
            install(ContentNegotiation) {
                json(kotlinx.serialization.json.Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    encodeDefaults = false
                })
            }

            //config client logging
               install(Logging) {
                    level = LogLevel.ALL
                }

            //Config timeout
               install(HttpTimeout) {
                   requestTimeoutMillis = 30 * 1000L
                   connectTimeoutMillis = 30 * 1000L
               }

            //TODO check This Later
            //Config Base Url
            defaultRequest {
                url(BASE_URL)
                /*  headers {
                      header(HEADER_AGENT, AGENT_ANDROID)
                      header(HEADER_DIGIPAY_VERSION, DIGIPAY_VERSION)
                  }*/
            }
        }
    }


    //Apis
    private val apiBirdsImage by lazy {
        ApiBirdsImageImpl(httpClient)
    }

    //Remote Data Sources
    private val birdsImageDataSourceRemote by lazy {
        BirdsImageImageDataSourceRemoteImpl(apiBirdsImage)
    }


    //Repository
    private val birdsImageRepository by lazy {
        BirdImageImageRepositoryImpl(birdsImageDataSourceRemote, dispatcher)
    }


    //Local
    /*  private val methodCache: MethodCache by lazy {
          MethodCacheImpl()
      }*/


    //UseCases
    private val getBirdsImage by lazy {
        GetBirdsImage(birdsImageRepository)
    }


    //ViewModels
    val birdsViewModelFactory: BirdsViewModelFactory by lazy {
        BirdsViewModelFactory(
            getBirdsImage
        )
    }

}