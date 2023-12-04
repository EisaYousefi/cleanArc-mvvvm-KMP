package feature.viewmodel

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import data.model.remorte.BirdImageRemote
import data.model.remorte.mapToBirdsImage
import domain.usecase.GetBirdsImage

data class BirdsUiState(
    val images: List<BirdImageRemote> = emptyList(),
    val selectedCategory: String? = null
) {
    val categories = images.map { it.category }.toSet()
    val selectedImages = if (selectedCategory.isNullOrEmpty()) images
    else images.filter { it.category == selectedCategory }
}

class BirdsViewModel(
    private val getBirdsImage: GetBirdsImage
) : ViewModel() {

    private val _uiState: MutableStateFlow<BirdsUiState> = MutableStateFlow(BirdsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        updateImages()
    }

    private fun updateImages() {
        viewModelScope.launch {
            val images = getBirdsImage.invoke().mapToBirdsImage()
            _uiState.update {
                it.copy(
                    images = images
                )
            }
        }
    }

    fun selectedCategory(category: String) {
        _uiState.update {
            it.copy(selectedCategory = category)
        }
    }


    override fun onCleared() {
      //  httpClient.close()
    }
}