package domain.repository

import domain.model.BirdImage

interface BirdsImageDataSource {

    interface BirdsImageDataSourceRemote{
        suspend fun getBirdImages():List<BirdImage>
    }
    interface BirdsImageDataSourceLocal{

    }
}