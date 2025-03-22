package dev.alejo.sharedelements

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

data class Character(
    val name: String,
    @DrawableRes val image: Int
)

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ListScreen(
    animatedVisibilityScope: AnimatedVisibilityScope,
    onItemClick: (Int, String) -> Unit
) {
    val characters = listOf(
        Character(name = "Goku", image = R.drawable.goku),
        Character(name = "Vegeta", image = R.drawable.vegeta),
        Character(name = "Picolo", image = R.drawable.picolo)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(characters) { index, character ->
            CharacterItem(
                name = character.name,
                image = character.image,
                animatedVisibilityScope = animatedVisibilityScope,
                onItemClick = { onItemClick(character.image, character.name) }
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.CharacterItem(
    name: String,
    @DrawableRes image: Int,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onItemClick: () -> Unit
) {
    Card(onClick = onItemClick) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(16 / 9f)
                    .weight(1f)
                    .sharedElement(
                        /*
                        The key should be unique for each element
                        In this case this id should be the same for the element in the detail screen
                        */
                        state = rememberSharedContentState(key = "image/$image"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(1000)
                        }
                    )
            )
            Text(
                text = name,
                modifier = Modifier
                    .weight(1f)
                    .sharedElement(
                        /*
                        The key should be unique for each element
                        In this case this id should be the same for the element in the detail screen
                        */
                        state = rememberSharedContentState(key = "text/$name"),
                        animatedVisibilityScope = animatedVisibilityScope,
                        boundsTransform = { _, _ ->
                            tween(1000)
                        }
                    )
            )
        }
    }
}