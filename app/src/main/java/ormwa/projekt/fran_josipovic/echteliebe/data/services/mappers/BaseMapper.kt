package ormwa.projekt.fran_josipovic.echteliebe.data.services.mappers

import android.util.Log
import ormwa.projekt.fran_josipovic.echteliebe.auth.UserData
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.models.Comment
import java.time.LocalDateTime

open class BaseMapper {
    fun toCommentHash(comment: Comment): HashMap<String, Any> {
        return hashMapOf(
            "id" to comment.id,
            "postedAt" to comment.postedAt,
            "user" to comment.user,
            "text" to comment.text
        )
    }
    private fun toUserData(
        user: Map<String, Any>
    ): UserData {
        return UserData(
            user["userId"] as? String ?: "",
            user["username"] as? String,
            user["profilePictureUrl"] as? String
        )
    }
    fun toCommentsList(commentsHash: List<Map<String, Any>>): List<Comment> {
        return commentsHash.map {
            Log.d("comment", it.toString())

            val postedAtMap = it["postedAt"] as? Map<String, Any>

            val postedAt = if (postedAtMap != null) {
                LocalDateTime.of(
                    (postedAtMap["year"] as Long).toInt(),
                    (postedAtMap["monthValue"] as Long).toInt(),
                    (postedAtMap["dayOfMonth"] as Long).toInt(),
                    (postedAtMap["hour"] as Long).toInt(),
                    (postedAtMap["minute"] as Long).toInt(),
                    (postedAtMap["second"] as Long).toInt()
                )
            } else {
                LocalDateTime.MIN
            }

            val user = it["user"] as? Map<String, Any>

            Comment(
                id = it["id"] as? String ?: "",
                postedAt = postedAt,
                user = toUserData(
                    user ?: hashMapOf()
                ),
                text = it["text"] as? String ?: ""
            )
        }
    }
}
