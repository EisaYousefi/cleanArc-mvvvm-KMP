package domain.usecase

import domain.repository.BirdsImageRepository

class GetBirdImages(private val repository: BirdsImageRepository) {
    suspend fun invoke() = repository.getBirdImages()
}