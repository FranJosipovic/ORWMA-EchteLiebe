package ormwa.projekt.fran_josipovic.echteliebe

import android.app.Activity.RESULT_OK
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import ormwa.projekt.fran_josipovic.echteliebe.auth.GoogleAuthUiClient
import ormwa.projekt.fran_josipovic.echteliebe.auth.SignInViewModel
import ormwa.projekt.fran_josipovic.echteliebe.auth.UserData
import ormwa.projekt.fran_josipovic.echteliebe.navigation.InteractionDetailsDestination
import ormwa.projekt.fran_josipovic.echteliebe.navigation.NavigationItem
import ormwa.projekt.fran_josipovic.echteliebe.navigation.PostDetailsDestination
import ormwa.projekt.fran_josipovic.echteliebe.ui.components.BottomNavigationBar
import ormwa.projekt.fran_josipovic.echteliebe.ui.components.TopBar
import ormwa.projekt.fran_josipovic.echteliebe.ui.screens.InfoScreen
import ormwa.projekt.fran_josipovic.echteliebe.ui.screens.chants.ChantsScreen
import ormwa.projekt.fran_josipovic.echteliebe.ui.screens.interactions.InteractionsScreen
import ormwa.projekt.fran_josipovic.echteliebe.ui.screens.interactions.details.InteractionsDetailsScreen
import ormwa.projekt.fran_josipovic.echteliebe.ui.screens.posts.PostsScreen
import ormwa.projekt.fran_josipovic.echteliebe.ui.screens.posts.details.PostDetailsScreen

@Composable
fun MainScreen(
    googleAuthUiClient: GoogleAuthUiClient,
    lifecycleScope: LifecycleCoroutineScope,
    applicationContext: Context
) {

    var userData: UserData? by remember {
        mutableStateOf(null)
    }
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

    val viewModel: SignInViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        if (googleAuthUiClient.getSignedInUser() != null) {
            userData = googleAuthUiClient.getSignedInUser()
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == RESULT_OK) {
                lifecycleScope.launch {
                    val signInResult = googleAuthUiClient.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                    viewModel.onSignInResult(signInResult)
                }
            }
        }
    )

    LaunchedEffect(key1 = state.isSignInSuccessful) {
        if (state.isSignInSuccessful) {
            Toast.makeText(
                applicationContext,
                "Sign in successful",
                Toast.LENGTH_LONG
            ).show()

            userData = googleAuthUiClient.getSignedInUser()
            viewModel.resetState()
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                shouldNavigateBack = shouldNavigateBack,
                onNavigateBack = { navController.popBackStack() },
                user = userData,
                onLogout = {
                    lifecycleScope.launch {
                        googleAuthUiClient.signOut()
                        userData = null
                    }
                }) {
                lifecycleScope.launch {
                    val signInIntentSender = googleAuthUiClient.signIn()
                    launcher.launch(
                        IntentSenderRequest.Builder(
                            signInIntentSender ?: return@launch
                        ).build()
                    )
                }
            }
        },
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
                    PostsScreen(viewModel = koinViewModel(), user = userData, onNavigate = {
                        navController.navigate(
                            PostDetailsDestination.createNavigationRoute(it)
                        )
                    })
                }
                composable(NavigationItem.Interaction.route) {
                    InteractionsScreen(
                        user = userData,
                        interactionsViewModel = koinViewModel(),
                        onNavigate = {
                            navController.navigate(
                                InteractionDetailsDestination.createNavigationRoute(it)
                            )
                        }) {
                        lifecycleScope.launch {
                            val signInIntentSender = googleAuthUiClient.signIn()
                            launcher.launch(
                                IntentSenderRequest.Builder(
                                    signInIntentSender ?: return@launch
                                ).build()
                            )
                        }
                    }
                }
                composable(route = InteractionDetailsDestination.route, arguments = listOf(
                    navArgument("interactionId") { type = NavType.StringType }
                )) {
                    val interactionId = it.arguments?.getString("interactionId")
                    InteractionsDetailsScreen(interactionDetailsViewModel = koinViewModel(
                        parameters = {
                            parametersOf(
                                interactionId
                            )
                        }
                    ), userData = userData!!)
                }
                composable(NavigationItem.Info.route) {
                    InfoScreen()
                }
                composable(route = NavigationItem.Chants.route) {
                    ChantsScreen(viewModel = koinViewModel())
                }
                composable(route = PostDetailsDestination.route, arguments = listOf(
                    navArgument("postId") { type = NavType.StringType }
                )) {
                    val postId = it.arguments?.getString("postId")
                    PostDetailsScreen(postDetailsViewModel = koinViewModel(parameters = {
                        parametersOf(
                            postId
                        )
                    }), userData = userData)
                }
            }
        }
    }
}
