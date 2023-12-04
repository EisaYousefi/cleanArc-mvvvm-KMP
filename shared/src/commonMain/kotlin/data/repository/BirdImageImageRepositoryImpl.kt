package data.repository

import domain.model.BirdImage
import domain.repository.BirdsImageDataSource
import domain.repository.BirdsImageRepository
import kotlinx.coroutines.CoroutineDispatcher

class BirdImageImageRepositoryImpl(
    private val birdsImageDataSourceRemote: BirdsImageDataSource.BirdsImageDataSourceRemote,
    private val dispatcher: CoroutineDispatcher
) : BirdsImageRepository {
    override suspend fun getBirdImages(): List<BirdImage> =
        birdsImageDataSourceRemote.getBirdImages()
}