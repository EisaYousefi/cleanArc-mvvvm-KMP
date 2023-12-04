package domain.usecase

import domain.repository.BirdsImageRepository

class GetBirdsImage(private val repository: BirdsImageRepository) {
    suspend fun invoke() = repository.getBirdImages()
}