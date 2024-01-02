package ormwa.projekt.fran_josipovic.echteliebe.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ormwa.projekt.fran_josipovic.echteliebe.navigation.navigationItems
import ormwa.projekt.fran_josipovic.echteliebe.ui.theme.EchteLiebeTheme

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.background) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        navigationItems.forEach { screen ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = { navController.navigate(screen.route) },
                icon = {
                    Icon(
                        painter = painterResource(id = screen.vectorAsset),
                        contentDescription = null,
                        tint = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    EchteLiebeTheme {
        Scaffold(bottomBar = { BottomNavigationBar(rememberNavController()) }) { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)

            ) {
            }
        }
    }
}