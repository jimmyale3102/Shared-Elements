package dev.alejo.sharedelements

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.alejo.sharedelements.ui.theme.SharedElementsTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalSharedTransitionApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SharedElementsTheme {
                val navController = rememberNavController()
                SharedTransitionLayout {
                    NavHost(navController = navController, startDestination = "listScreen") {
                        composable("listScreen") {
                            ListScreen(
                                animatedVisibilityScope = this
                            ) { resId, name ->
                                navController.navigate("detailScreen/$resId/$name")
                            }
                        }
                        composable(
                            route = "detailScreen/{resId}/{name}",
                            arguments = listOf(
                                navArgument("resId") { type = NavType.IntType },
                                navArgument("name") { type = NavType.StringType }
                            )
                        ) {
                            val resId = it.arguments?.getInt("resId") ?: 0
                            val name = it.arguments?.getString("name").orEmpty()
                            DetailScreen(
                                resId = resId,
                                name = name,
                                animatedVisibilityScope = this
                            )
                        }
                    }
                }
            }
        }
    }
}