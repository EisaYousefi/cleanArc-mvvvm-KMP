package domain.repository

import domain.model.BirdImage

interface BirdsImageRepository {
    suspend fun getBirdImages():List<BirdImage>
}