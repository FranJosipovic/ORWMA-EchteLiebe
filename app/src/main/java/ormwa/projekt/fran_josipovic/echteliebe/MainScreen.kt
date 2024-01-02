package ormwa.projekt.fran_josipovic.echteliebe

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ormwa.projekt.fran_josipovic.echteliebe.components.BottomNavigationBar
import ormwa.projekt.fran_josipovic.echteliebe.components.TopBar
import ormwa.projekt.fran_josipovic.echteliebe.navigation.NavigationItem
import ormwa.projekt.fran_josipovic.echteliebe.ui.theme.EchteLiebeTheme

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val shouldNavigateBack by remember {
        derivedStateOf {
            when (navBackStackEntry?.destination?.route) {
                NavigationItem.Home.route -> false
                NavigationItem.Interaction.route -> false
                NavigationItem.Info.route -> false
                NavigationItem.Chants.route -> false
                else -> true
            }
        }
    }
    Scaffold(
        topBar = { TopBar(shouldNavigateBack = shouldNavigateBack, user = null) },
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { padding ->
        Surface(
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.fillMaxSize()
        ) {
            NavHost(
                navController = navController,
                startDestination = NavigationItem.Home.route,
                modifier = Modifier.padding(padding)
            ) {
                composable(NavigationItem.Home.route) {
                    Text(text = NavigationItem.Home.label)
                }
                composable(NavigationItem.Interaction.route) {
                    Text(text = NavigationItem.Interaction.label)
                }
                composable(NavigationItem.Info.route) {
                    Text(text = NavigationItem.Info.label)
                }
                composable(route = NavigationItem.Chants.route) {
                    Text(text = NavigationItem.Chants.label)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    EchteLiebeTheme {
        MainScreen()
    }
}