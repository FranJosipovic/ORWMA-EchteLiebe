package ormwa.projekt.fran_josipovic.echteliebe.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ormwa.projekt.fran_josipovic.echteliebe.R
import ormwa.projekt.fran_josipovic.echteliebe.components.CustomTabRow
import ormwa.projekt.fran_josipovic.echteliebe.components.ScreenPoster
import ormwa.projekt.fran_josipovic.echteliebe.components.Subtitle
import ormwa.projekt.fran_josipovic.echteliebe.ui.theme.EchteLiebeTheme

@Composable
fun InfoScreen() {
    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("About BvB", "Signal Iduna Park")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(rememberScrollState())
    ) {
        ScreenPoster(img = R.drawable.infopage_poster, padding = 10)
        CustomTabRow(selectedTabIndex = tabIndex, tabs = tabs, fontSize = 20.sp) { index ->
            tabIndex = index
        }
        when (tabIndex) {
            0 -> AboutBVB()
            1 -> SignalIduna()
        }
    }
}

@Composable
fun AboutBVB() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)

    ) {
        Text(
            text = "About BVB",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )

        Subtitle(
            Modifier.padding(vertical = 30.dp),
            text = "Borussia Dortmund is the intense football experience"
        )
        ScreenPoster(img = R.drawable.subtitle_poster, padding = 0)
        Text(
            text = stringResource(id = R.string.info_about_Bvb_first_paragraph),
            color = Color.White,
            modifier = Modifier.padding(top = 10.dp)
        )

        Subtitle(Modifier.padding(top = 30.dp), text = "Intensity")
        ScreenPoster(img = R.drawable.intensity_poster, padding = 0)
        Text(
            text = stringResource(id = R.string.info_about_Bvb_intensity),
            color = Color.White,
            modifier = Modifier.padding(top = 10.dp)
        )

        Subtitle(modifier = Modifier.padding(top = 30.dp), text = "Authenticity")
        ScreenPoster(img = R.drawable.authenticity_poster, padding = 0)
        Text(
            text = stringResource(id = R.string.info_about_Bvb_authenticity),
            color = Color.White,
            modifier = Modifier.padding(top = 10.dp)
        )

        Subtitle(modifier = Modifier.padding(top = 30.dp), text = "Cohesion")
        ScreenPoster(img = R.drawable.cohesion_poster, padding = 0)
        Text(
            text = stringResource(id = R.string.info_about_Bvb_cohesion),
            color = Color.White,
            modifier = Modifier.padding(top = 10.dp)
        )

        Subtitle(modifier = Modifier.padding(top = 30.dp), text = "Ambition")
        ScreenPoster(img = R.drawable.ambition_poster, padding = 0)
        Text(
            text = stringResource(id = R.string.info_about_Bvb_ambition),
            color = Color.White,
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}

@Composable
fun SignalIduna() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)

    ) {
        Text(
            text = "SIGNAL IDUNA PARK",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 20.dp, bottom = 40.dp)
        )
        ScreenPoster(img = R.drawable.signal_iduna_poster, padding = 0)
        Subtitle(
            text = "Eighty one thousand three hundred and sixty five",
            fontSize = 20.sp,
            modifier = Modifier.padding(vertical = 30.dp)
        )
        Subtitle(
            text = "That's how many fans fit into SIGNAL IDUNA PARK, Germany's largest football stadium",
            fontSize = 20.sp,
        )
        Text(
            text = stringResource(id = R.string.info_SIGNAL_IDUNA_PARK_1p),
            color = Color.White,
            modifier = Modifier.padding(vertical = 10.dp)
        )
        ScreenPoster(img = R.drawable.signal_iduna_poster2, padding = 0)
        Text(
            text = stringResource(id = R.string.info_SIGNAL_IDUNA_PARK_2p),
            color = Color.White,
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = stringResource(id = R.string.info_SIGNAL_IDUNA_PARK_3p),
            color = Color.White,
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = stringResource(id = R.string.info_SIGNAL_IDUNA_PARK_4p),
            color = Color.White,
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = stringResource(id = R.string.info_SIGNAL_IDUNA_PARK_5p),
            color = Color.White,
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = stringResource(id = R.string.info_SIGNAL_IDUNA_PARK_6p),
            color = Color.White,
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = stringResource(id = R.string.info_SIGNAL_IDUNA_PARK_7p),
            color = Color.White,
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = stringResource(id = R.string.info_SIGNAL_IDUNA_PARK_8p),
            color = Color.White,
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = stringResource(id = R.string.info_SIGNAL_IDUNA_PARK_9p),
            color = Color.White,
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = stringResource(id = R.string.info_SIGNAL_IDUNA_PARK_10p),
            color = Color.White,
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}

@Preview
@Composable
fun InfoScreenPreview() {
    EchteLiebeTheme {
        InfoScreen()
    }
}