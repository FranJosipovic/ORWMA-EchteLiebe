package ormwa.projekt.fran_josipovic.echteliebe.data.services.interactions.models

import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.models.Comment

data class FanPoolInteraction(
    val id: String,
    val img: String,
    val title: String,
)

data class InteractionDetails(
    val posterImage: String,
    val title: String,
    val options: List<InteractionOption>,
    val comments: List<Comment>
)

data class InteractionOption(
    val id: String,
    val name: String,
    val votes: List<String>
)
