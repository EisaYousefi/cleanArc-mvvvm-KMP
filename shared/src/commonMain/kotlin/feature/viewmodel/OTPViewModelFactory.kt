package feature.viewmodel

import common.Factory
import domain.usecase.GetBirdsImage

internal class BirdsViewModelFactory(
    private val getBirdsImage: GetBirdsImage
) : Factory<BirdsViewModel> {
    override fun create(): BirdsViewModel {
        return BirdsViewModel(
            getBirdsImage
        )
    }
}