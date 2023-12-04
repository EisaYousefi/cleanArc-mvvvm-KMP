import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import data.api.BASE_URL
import data.model.remorte.BirdImageRemote
import di.ServiceLocator
import feature.viewmodel.BirdsViewModel
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource

@Composable
fun BirdAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = MaterialTheme.colors.copy(primary = Color.Black),
        shapes = MaterialTheme.shapes.copy(
            small = AbsoluteRoundedCornerShape(10.dp),
            medium = AbsoluteRoundedCornerShape(20.dp),
            large = AbsoluteRoundedCornerShape(30.dp),
        )
    ) {
        content()
    }
}

@Composable
fun App() {
    BirdAppTheme {
        val viewModel = ServiceLocator.birdsViewModelFactory.create()
        //  val viewModel = getViewModel(Unit, viewModelFactory { BirdsViewModel() })
        BirdPage(viewModel)
    }
}

@Composable
fun BirdPage(birdsViewModel: BirdsViewModel) {
    val uiState by birdsViewModel.uiState.collectAsState()
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            Modifier.fillMaxWidth().padding(5.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            for (category in uiState.categories) {
                Button(
                    onClick = {
                        birdsViewModel.selectedCategory(category)
                    },
                    modifier = Modifier.aspectRatio(1.95f).fillMaxSize().weight(1.0f),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 0.dp,
                        focusedElevation = 0.dp
                    )
                ) {
                    Text(category)
                }
            }
        }
        AnimatedVisibility(uiState.selectedImages.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.fillMaxSize().padding(horizontal = 5.dp),
                content = {
                    items(uiState.selectedImages) {
                        BirdImageCall(it)
                    }
                }
            )
        }
    }
}

@Composable
fun BirdImageCall(birdImageRemote: BirdImageRemote) {
    KamelImage(
        asyncPainterResource("$BASE_URL${birdImageRemote.path}"),
        "${birdImageRemote.author} by ${birdImageRemote.category}",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxWidth().aspectRatio(1.0f)
    )
}


expect fun getPlatformName(): String