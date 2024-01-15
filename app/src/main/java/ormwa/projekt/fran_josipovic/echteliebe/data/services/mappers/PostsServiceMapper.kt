package ormwa.projekt.fran_josipovic.echteliebe.data.services.mappers

import com.google.firebase.firestore.QuerySnapshot
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.models.DetailedText
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.models.Post

class PostsServiceMapper : BaseMapper(){
    private fun toDetailedTextList(hashList: List<HashMap<String, Any>>): List<DetailedText> {
        return hashList.map {
            DetailedText(
                it["subtitle"] as? String ?: "",
                it["text"] as? String ?: ""
            )
        }
    }

    fun toPostsList(documents: QuerySnapshot): List<Post> {
        return documents.map { it ->
            val detailedText =
                it.get("detailedText") as? List<HashMap<String, Any>> ?: emptyList()

            val detailedTextList = toDetailedTextList(detailedText)

            val commentsHash = it.get("comments") as? List<Map<String, Any>>

            val commentsList = commentsHash?.let { list -> toCommentsList(list) }

            val img = it.getString("img") ?: ""
            val readingTime = it.getString("readingTime") ?: ""
            val shortIntro = it.getString("shortIntro") ?: ""
            val subtitle = it.getString("subtitle") ?: ""
            val title = it.getString("title") ?: ""
            val votes = it.get("votes") as? List<String> ?: emptyList()


            Post(
                id = it.id,
                commentsList ?: emptyList(),
                detailedTextList,
                img,
                readingTime,
                shortIntro,
                subtitle,
                title,
                votes
            )
        }
    }
}