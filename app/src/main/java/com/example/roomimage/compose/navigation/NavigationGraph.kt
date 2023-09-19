package com.example.roomimage.compose.navigation

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.roomimage.compose.FormScreen
import com.example.roomimage.compose.ProductListScreen
import com.example.roomimage.viewmodel.ProductViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

interface Destination {
    val icon: ImageVector
    val route: String
}

object Form : Destination {
    override val icon: ImageVector = Icons.Filled.PieChart
    override val route: String = "FormScreen"
}

object ProductList : Destination {
    override val icon: ImageVector = Icons.Filled.PieChart
    override val route: String = "ProductListScreen"
}

val listOfIdDestination= listOf(Form)

fun NavGraphBuilder.mainGraph(navController: NavHostController) {
    // Navegar a la lista de productos
    composable(Form.route) {
        val viewModel = hiltViewModel<ProductViewModel>()
        FormScreen(viewModel) {
            Log.d("mainGraph","FormScreen onClickNavigateTo ProductListScreen")
            navController.navigateSingleTopTo(ProductList.route)
        }
    }
    // Navegar de la lista de productos al inicio de nuevo
    composable(ProductList.route) {
        val viewModel = hiltViewModel<ProductViewModel>()
        ProductListScreen(viewModel) {
            Log.d("mainGraph","ProductListScreen onClickNavigateTo FormScreen")
            navController.navigateSingleTopTo(Form.route)
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
}