package ormwa.projekt.fran_josipovic.echteliebe.ui.screens.posts.details

import android.util.Log
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import ormwa.projekt.fran_josipovic.echteliebe.auth.UserData
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.models.Comment
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun PostDetailsScreen(postDetailsViewModel: PostDetailsViewModel, userData: UserData?) {

    val postDetailsViewState by postDetailsViewModel.postDetailsViewState.collectAsState()
    val comments by postDetailsViewModel.postComments.collectAsState()
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        when (postDetailsViewState) {
            is PostDetailsState.Loading -> {
                Text(text = "Loading", color = Color.White)
            }

            is PostDetailsState.Success -> {
                val post = (postDetailsViewState as PostDetailsState.Success).post
                Column(
                    Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    SubcomposeAsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp),
                        model = post.img,
                        contentDescription = "img",
                        loading = { CircularProgressIndicator() },
                        contentScale = ContentScale.FillBounds
                    )
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    ) {
                        Text(text = post.title, color = Color.White, fontSize = 20.sp)
                        Text(
                            modifier = Modifier.padding(vertical = 8.dp),
                            text = post.subtitle,
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                        post.detailedText.forEach {
                            it.subtitle?.let { it1 ->
                                Text(
                                    text = it1,
                                    fontSize = 18.sp,
                                    color = Color.White
                                )
                            }
                            Text(
                                text = it.text,
                                modifier = Modifier.padding(bottom = 15.dp),
                                color = Color.White
                            )
                        }
                        Box(
                            modifier = Modifier
                                .padding(vertical = 20.dp)
                                .fillMaxWidth()
                                .height(40.dp)
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .clickable {
                                    if (userData == null) {
                                        Toast
                                            .makeText(
                                                context,
                                                "You need to be logged in to vote",
                                                Toast.LENGTH_LONG
                                            )
                                            .show()
                                    } else {
                                        postDetailsViewModel.onPostVote(userId = userData.userId)
                                    }

                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                                Icon(
                                    imageVector = if (post.votes.contains(userData?.userId)) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
                                    contentDescription = "vote",
                                    tint = Color.Black
                                )
                                Text(
                                    text = if (post.votes.contains(userData?.userId)) "DOWNVOTE" else "UPVOTE",
                                    color = Color.Black
                                )
                            }
                        }
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            thickness = 2.dp,
                            color = Color.White
                        )
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowUp,
                                contentDescription = "up",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = post.votes.size.toString(),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        CommentsSection(comments = comments) {
                            if (userData == null) {
                                Toast
                                    .makeText(
                                        context,
                                        "You need to be logged in to vote",
                                        Toast.LENGTH_LONG
                                    )
                                    .show()
                            } else {
                                postDetailsViewModel.postNewComment(
                                    userData,
                                    it
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CommentsSection(comments: List<Comment>, postComment: (String) -> Unit) {
    var commentValue by remember {
        mutableStateOf("")
    }
    Column(Modifier.fillMaxWidth()) {
        Text(text = "Comments", color = Color.White, fontSize = 25.sp)
        if (comments.isNotEmpty()) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(10.dp), verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {

                comments.forEach {
                    Log.d("comment", it.toString())
                    SingleComment(
                        userData = it.user,
                        comment = it
                    )
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No comments", color = Color.Gray)
            }
        }

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            TextField(
                modifier = Modifier.weight(1f),
                value = commentValue,
                placeholder = { Text(text = "Enter your comment here...") },
                onValueChange = { commentValue = it },
            )
            Box(
                modifier = Modifier
                    .height(55.dp)
                    .width(55.dp)
                    .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(4.dp))
                    .clickable {
                        postComment(commentValue)
                        commentValue = ""
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Send,
                    contentDescription = "send",
                    tint = Color.Black
                )
            }
        }
    }
}

@Composable
fun SingleComment(userData: UserData, comment: Comment) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .width(48.dp)
                .height(48.dp)
                .clip(CircleShape),
            model = userData.profilePictureUrl,
            contentDescription = "img",
            loading = { CircularProgressIndicator() },
            contentScale = ContentScale.FillBounds
        )
        Column(Modifier.weight(1f)) {
            userData.username?.let { Text(text = it, color = Color.White) }
            Text(text = formatPostedAt(comment.postedAt), color = Color.Gray)
            Text(text = comment.text, color = Color.White, modifier = Modifier.padding(top = 5.dp))
        }
    }
}

fun formatPostedAt(postedAt: LocalDateTime): String {
    val now = LocalDateTime.now()
    val difference = ChronoUnit.SECONDS.between(postedAt, now)

    return when {
        difference < 60 -> "$difference seconds ago"
        difference < 3600 -> "${(difference / 60)} minutes ago"
        difference < 86400 -> "${(difference / 3600)} hours ago"
        else -> postedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
    }
}
