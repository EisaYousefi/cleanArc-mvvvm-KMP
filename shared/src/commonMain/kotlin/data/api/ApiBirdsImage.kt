package data.api

import data.model.remorte.BirdImageRemote

interface ApiBirdsImage {
    suspend fun getBirdImages():List<BirdImageRemote>
}