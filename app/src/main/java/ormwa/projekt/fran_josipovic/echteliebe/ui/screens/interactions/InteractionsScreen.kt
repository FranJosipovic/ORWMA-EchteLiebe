package ormwa.projekt.fran_josipovic.echteliebe.ui.screens.interactions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import ormwa.projekt.fran_josipovic.echteliebe.R
import ormwa.projekt.fran_josipovic.echteliebe.auth.UserData
import ormwa.projekt.fran_josipovic.echteliebe.ui.components.CustomTabRow
import ormwa.projekt.fran_josipovic.echteliebe.ui.components.ScreenPoster

@Composable
fun InteractionsScreen(
    user: UserData?,
    interactionsViewModel: InteractionsViewModel,
    onLogin: () -> Unit
) {

    val interactionsScreenState by interactionsViewModel.interactionsScreenViewState.collectAsState()

    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("FAN POOLS")

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ScreenPoster(img = R.drawable.interactionpage_poster, padding = 10)
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (user == null) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(10.dp)
                        ), onClick = { onLogin() }) {
                        Text(text = "Login")
                    }
                    Text(
                        text = "*You need to be registered to interact",
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }

            } else {
                when (interactionsScreenState) {
                    is InteractionScreenState.Loading ->
                        Text(text = "Loading", color = Color.White)

                    is InteractionScreenState.Success -> {
                        val pools = (interactionsScreenState as InteractionScreenState.Success).data
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {
                            CustomTabRow(
                                selectedTabIndex = tabIndex,
                                tabs = tabs,
                                fontSize = 20.sp
                            ) { index ->
                                tabIndex = index
                            }
                            when (tabIndex) {
                                0 -> FanPools(pools)
                            }
                        }
                    }
                }

            }
        }
    }

}

@Composable
fun FanPools(pools: List<FanPoolInteractionScreen>) {
    Column(Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.padding(bottom = 25.dp),
            text = stringResource(id = R.string.fan_pools_text),
            color = Color.White,
            style = TextStyle(
                lineHeight = 20.sp,
                fontSize = 15.sp
            )
        )
        PoolsCarousel(pools)
    }
}

@Composable
fun PoolsCarousel(pools: List<FanPoolInteractionScreen>) {

    var activeIndex by remember {
        mutableStateOf(0)
    }

    var activePool by remember {
        mutableStateOf(pools[activeIndex])
    }

    LaunchedEffect(activeIndex) {
        activePool = pools[activeIndex]
    }

    Column(Modifier.fillMaxWidth()) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            FanPoolCard(image = activePool.img, title = activePool.title)
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "left",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable {
                        if (activeIndex > 0) {
                            activeIndex -= 1
                        }
                    },
                tint = if (activeIndex <= 0) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else MaterialTheme.colorScheme.primary
            )
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "right",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable {
                        if (activeIndex < pools.size - 1) {
                            activeIndex += 1
                        }
                    },
                tint = if (activeIndex >= pools.size - 1) MaterialTheme.colorScheme.primary.copy(
                    alpha = 0.2f
                ) else MaterialTheme.colorScheme.primary
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp),

        ) {
            pools.forEachIndexed { index, value ->
                Box(
                    modifier = Modifier
                        .height(6.dp)
                        .width(40.dp)
                        .background(
                            color = if (index == activeIndex) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(
                                alpha = 0.5f
                            )
                        )
                )
            }
        }
    }
}

data class FanPoolInteractionScreen(
    val id: String,
    val img: String,
    val title: String,
)

@Composable
fun FanPoolCard(image: String, title: String) {
    Row(
        Modifier
            .fillMaxSize()
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .width(150.dp)
                .fillMaxHeight(),
            model = image,
            contentDescription = "img",
            loading = { CircularProgressIndicator() },
            contentScale = ContentScale.FillBounds,

            )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.Gray, RoundedCornerShape(topEnd = 6.dp, bottomEnd = 6.dp))
        ) {
            Text(text = title, color = Color.White)
        }
    }
}
