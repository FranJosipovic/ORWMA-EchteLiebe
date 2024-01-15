package ormwa.projekt.fran_josipovic.echteliebe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.identity.Identity
import io.ktor.util.InternalAPI
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ormwa.projekt.fran_josipovic.echteliebe.auth.GoogleAuthUiClient
import ormwa.projekt.fran_josipovic.echteliebe.auth.signInViewModelModule
import ormwa.projekt.fran_josipovic.echteliebe.data.di.dataModule
import ormwa.projekt.fran_josipovic.echteliebe.data.di.networkModule
import ormwa.projekt.fran_josipovic.echteliebe.ui.screens.chants.chantsModule
import ormwa.projekt.fran_josipovic.echteliebe.ui.screens.interactions.details.interactionDetailsModule
import ormwa.projekt.fran_josipovic.echteliebe.ui.screens.interactions.interactionsModule
import ormwa.projekt.fran_josipovic.echteliebe.ui.screens.posts.details.postDetailsModule
import ormwa.projekt.fran_josipovic.echteliebe.ui.screens.posts.postsModule
import ormwa.projekt.fran_josipovic.echteliebe.ui.theme.EchteLiebeTheme

class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    @OptIn(InternalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(this@MainActivity)
            modules(
                networkModule,
                dataModule,
                chantsModule,
                postsModule,
                postDetailsModule,
                interactionsModule,
                interactionDetailsModule,
                signInViewModelModule
            )
        }

        setContent {
            EchteLiebeTheme {
                MainScreen(googleAuthUiClient, lifecycleScope, applicationContext)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {

    EchteLiebeTheme {
        //MainScreen()
    }
}
