package ormwa.projekt.fran_josipovic.echteliebe.ui.screens.interactions.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ormwa.projekt.fran_josipovic.echteliebe.auth.UserData
import ormwa.projekt.fran_josipovic.echteliebe.data.repositories.interactions.InteractionsRepository
import ormwa.projekt.fran_josipovic.echteliebe.data.services.posts.Comment
import java.time.LocalDateTime
import java.util.UUID

class InteractionDetailsViewModel(
    private val interactionsRepository: InteractionsRepository,
    private val interactionId: String
) : ViewModel() {
    val interactionDetails: StateFlow<InteractionsScreenUiState> =
        interactionsRepository.getInteractionDetails(interactionId).map {
            InteractionsScreenUiState.Success(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = InteractionsScreenUiState.Loading
        )

    fun toggleVote(userId: String, optionId: String) {
        viewModelScope.launch {
            interactionsRepository.toggleVote(userId, interactionId, optionId)
        }
    }

    fun postNewComment(user: UserData, text: String) {
        viewModelScope.launch {
            interactionsRepository.postNewComment(
                interactionId,
                Comment(UUID.randomUUID().toString(), LocalDateTime.now(), user, text)
            )
        }
    }
}

sealed class InteractionsScreenUiState() {
    data class Success(val details: InteractionDetails) : InteractionsScreenUiState()
    data object Loading : InteractionsScreenUiState()
}