package ormwa.projekt.fran_josipovic.echteliebe.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.models.PostHomeScreen

@Composable
fun Post(
    modifier: Modifier,
    post: PostHomeScreen,
    disableVote: Boolean,
    userId: String,
    onNavigate: () -> Unit,
    onPostVote: () -> Unit
) {
    val context = LocalContext.current
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onNavigate() },
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(290.dp),
            model = post.img,
            contentDescription = "img",
            loading = { CircularProgressIndicator() },
            contentScale = ContentScale.FillBounds
        )
        Column(
            Modifier
                .fillMaxWidth()
                .height(350.dp)
                .padding(10.dp)
                .background(Color.White)
        ) {
            Text(text = post.title, color = Color.Black, fontSize = 20.sp)
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = post.subtitle,
                color = Color.LightGray,
                fontSize = 14.sp
            )
            Text(
                text = post.shortIntro, color = Color.Black, fontSize = 15.sp, style = TextStyle(
                    lineHeight = 20.sp
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = post.readingTime, color = Color.LightGray, fontSize = 16.sp)
                Box(
                    Modifier
                        .width(120.dp)
                        .height(40.dp)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(10.dp)
                        .clickable {
                            if (!disableVote) {
                                onPostVote()
                            } else {
                                Toast
                                    .makeText(
                                        context,
                                        "You need to be logged in to vote",
                                        Toast.LENGTH_LONG
                                    )
                                    .show()
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (post.votes.contains(userId)) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
                            contentDescription = "upvote",
                            tint = Color.Black
                        )
                        Text(
                            text = if (post.votes.contains(userId)) "DOWNVOTE" else "UPVOTE",
                            color = Color.Black,
                            fontSize = 10.sp
                        )
                        Text(
                            text = post.votes.size.toString(),
                            color = Color.Black,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}
