package ormwa.projekt.fran_josipovic.echteliebe.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ormwa.projekt.fran_josipovic.echteliebe.ui.theme.EchteLiebeTheme

@Composable
fun CustomTabRow(
    selectedTabIndex: Int,
    tabs: List<String>,
    fontSize:TextUnit = 25.sp,
    onTabSelected: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        content = {
            tabs.forEachIndexed { index, title ->
                CustomTab(
                    text = title,
                    selected = selectedTabIndex == index,
                    onClick = { onTabSelected(index) },
                    fontSize = fontSize
                )
            }
        }
    )
}

@Composable
fun CustomTab(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    fontSize: TextUnit = 25.sp
) {
    Box(
        modifier = Modifier
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (selected) MaterialTheme.colorScheme.primary else Color.White,
            modifier = Modifier.padding(10.dp),
            fontSize = fontSize
        )
    }
}

@Preview
@Composable
fun TabsPreview() {
    EchteLiebeTheme {
        var tabIndex by remember { mutableStateOf(0) }

        val tabs = listOf("Home", "About")
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)) {
            CustomTabRow(selectedTabIndex = tabIndex, tabs = tabs) { index ->
                tabIndex = index
            }
            when (tabIndex) {
                0 -> Text("Home", color = Color.White)
                1 -> Text("About", color = Color.White)
            }
        }
    }
}