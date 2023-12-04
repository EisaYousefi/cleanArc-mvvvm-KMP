package data.model.remorte


import domain.model.BirdImage
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BirdImageRemote(
    @SerialName("author")
    val author: String,
    @SerialName("category")
    val category: String,
    @SerialName("path")
    val path: String
)

fun List<BirdImageRemote>.mapToBirdsImageRemote() =
    map {
       BirdImage(
           author = it.author,
           category = it.category,
           path = it.path
       )
    }

fun List<BirdImage>.mapToBirdsImage() =
    map {
        BirdImageRemote(
            author = it.author,
            category = it.category,
            path = it.path
        )
    }