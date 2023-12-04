package data.repository

import data.api.ApiBirdsImage
import data.model.remorte.mapToBirdsImageRemote
import domain.model.BirdImage
import domain.repository.BirdsImageDataSource

class BirdsImageImageDataSourceRemoteImpl(private val apiBirdImages: ApiBirdsImage) :BirdsImageDataSource.BirdsImageDataSourceRemote{
    override suspend fun getBirdImages(): List<BirdImage> =
        apiBirdImages.getBirdImages().mapToBirdsImageRemote()
}