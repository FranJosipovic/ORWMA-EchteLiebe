package ormwa.projekt.fran_josipovic.echteliebe.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import ormwa.projekt.fran_josipovic.echteliebe.R
import ormwa.projekt.fran_josipovic.echteliebe.ui.theme.EchteLiebeTheme

data class User(val avatar: String, val username: String, val email: String)

@Composable
fun TopBar(shouldNavigateBack: Boolean, user: User?) {
    TopAppBar(backgroundColor = MaterialTheme.colorScheme.background) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                if (shouldNavigateBack) {
                    Icon(
                        painter = painterResource(id = R.drawable.back_icon),
                        contentDescription = "back icon", tint = MaterialTheme.colorScheme.primary
                    )
                }
                Image(
                    modifier = Modifier.size(35.dp),
                    painter = painterResource(id = R.drawable.borussia_dortmund_logo),
                    contentDescription = "app logo"
                )
            }

            if (user == null) {
                Button(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .size(height = 35.dp, width = 100.dp),
                ) {
                    Text(text = "Login", color = Color.Black)
                }
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.borussia_dortmund_logo),
                        contentDescription = "user avatar",
                        modifier = Modifier.size(35.dp)
                    )
                    Column {
                        Text(text = user.username, color = Color.White)
                        Text(text = "Treba smislit", color = Color.White)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    EchteLiebeTheme {
        Scaffold(topBar = {
            TopBar(
                shouldNavigateBack = true,
                //user = User("", "frcko17", "fran.josipovic55@gmail.com")
                user = null
            )
        }) { paddingValues ->
            Surface(
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .fillMaxSize()
            ) {
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopBarPreviewWithUser() {
    EchteLiebeTheme {
        Scaffold(topBar = {
            TopBar(
                shouldNavigateBack = true,
                user = User("", "frcko17", "fran.josipovic55@gmail.com")
            )
        }) { paddingValues ->
            Surface(
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .fillMaxSize()
            ) {
            }
        }
    }
}
