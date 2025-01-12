package org.koolda.files.server

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.http.Body
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.POST
import de.jensklingenberg.ktorfit.http.Path
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koolda.files.serializable.CatalogItem
import java.io.File

fun createApi(ip: String = "createApi"): Api {
    val ktorClient = HttpClient {
        install(ContentNegotiation) {
            json(Json { isLenient = true; ignoreUnknownKeys = true })
        }
    }
    return Ktorfit.Builder().baseUrl(url = "http://$ip/").httpClient(client = ktorClient)
        .build().createApi()
}

interface Api {
    @GET("connect")
    suspend fun getConnectStatus(): String

    @GET("disks")
    suspend fun getDisks(): List<CatalogItem>

    @GET("{disk}/{path}")
    suspend fun getCatalogFromPath(@Path("disk") disk: String, @Path("path") path: String): List<CatalogItem>

    @GET("{disk}/{path}")
    suspend fun getFileFromPath(@Path("disk") disk: String, @Path("path") path: String): File

    @GET("{disk}/{path}/d")
    suspend fun getFileDownloadFromPath(@Path("disk") disk: String, @Path("path") path: String): File

    @POST("upload")
    suspend fun upload(@Body data: String)
}