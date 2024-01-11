package ormwa.projekt.fran_josipovic.echteliebe.data.services.interactions

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import ormwa.projekt.fran_josipovic.echteliebe.ui.screens.interactions.FanPoolInteractionScreen

class InteractionsServiceImpl : InteractionsService {
    private val db = Firebase.firestore
    private val interactionsCollection = db.collection("Interaction")

    override suspend fun getInteractions(): List<FanPoolInteractionScreen> {
        val documents = interactionsCollection.get().await()

        val pools = mutableListOf<FanPoolInteractionScreen>()

        for (document in documents) {
            val pool = document.data
            pools.add(
                FanPoolInteractionScreen(
                    id = document.id,
                    title = pool["title"] as String,
                    img = pool["img"] as String
                )
            )
        }
        return pools
    }
}