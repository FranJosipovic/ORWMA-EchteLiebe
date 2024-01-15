package ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.models

import ormwa.projekt.fran_josipovic.echteliebe.auth.UserData
import java.time.LocalDateTime

data class PostHomeScreen(
    val id:String,
    val img: String,
    val readingTime: String,
    val shortIntro:String,
    val subtitle: String,
    val title:String,
    val votes: List<String>
)

data class PostDetailsScreen(
    val id:String,
    val detailedText: List<DetailedText>,
    val img: String,
    val readingTime: String,
    val subtitle: String,
    val title:String,
    val votes: List<String>
)
data class Post(
    val id:String,
    val comments: List<Comment>,
    val detailedText: List<DetailedText>,
    val img: String,
    val readingTime: String,
    val shortIntro:String,
    val subtitle: String,
    val title:String,
    val votes: List<String>
)

data class Comment(
    val id: String,
    val postedAt: LocalDateTime,
    val user: UserData,
    val text: String
)

data class DetailedText(
    val subtitle:String?,
    val text: String
)