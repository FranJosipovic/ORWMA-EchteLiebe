package ormwa.projekt.fran_josipovic.echteliebe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.ktor.util.InternalAPI
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ormwa.projekt.fran_josipovic.echteliebe.data.di.dataModule
import ormwa.projekt.fran_josipovic.echteliebe.data.di.networkModule
import ormwa.projekt.fran_josipovic.echteliebe.ui.screens.chants.chantsModule
import ormwa.projekt.fran_josipovic.echteliebe.ui.theme.EchteLiebeTheme

class MainActivity : ComponentActivity() {
    @OptIn(InternalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(this@MainActivity)
            modules(
                networkModule,
                dataModule,
                chantsModule
            )
        }

        setContent {
            EchteLiebeTheme {
                MainScreen()
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