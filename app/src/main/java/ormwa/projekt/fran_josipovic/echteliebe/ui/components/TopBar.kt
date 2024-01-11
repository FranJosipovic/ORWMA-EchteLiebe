package ormwa.projekt.fran_josipovic.echteliebe.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Surface
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ormwa.projekt.fran_josipovic.echteliebe.R
import ormwa.projekt.fran_josipovic.echteliebe.auth.UserData
import ormwa.projekt.fran_josipovic.echteliebe.ui.theme.EchteLiebeTheme

data class User(val avatar: String, val username: String, val email: String)

@Composable
fun TopBar(
    shouldNavigateBack: Boolean,
    onNavigateBack: () -> Unit,
    user: UserData?,
    onLogout: () -> Unit,
    onSignIn: () -> Unit
) {
    TopAppBar(
        backgroundColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.height(80.dp)
    ) {
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
                        modifier = Modifier.clickable { onNavigateBack() },
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
                    onClick = { onSignIn() },
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
                Row {
                    var menuExpanded by remember { mutableStateOf(false) }

                    user.username?.let {

                        Box(
                            modifier = Modifier
                                .clickable { menuExpanded = true }
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(50)
                                )
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = it,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                        }
                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false },
                            Modifier.background(MaterialTheme.colorScheme.primary)
                        ) {
                            DropdownMenuItem(onClick = {
                                onLogout()
                                menuExpanded = false
                            }) {
                                Text(
                                    modifier = Modifier.fillMaxSize(),
                                    text = "Logout",
                                    color = Color.Black,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
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
                onNavigateBack = {},
                user = null,
                {}
            ) {}
        }) {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
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
                onNavigateBack = {},
                user = UserData("", "Fran josipovic", "fran.josipovic55@gmail.com"),
                {}
            ) {}
        }) {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
            }
        }
    }
}
