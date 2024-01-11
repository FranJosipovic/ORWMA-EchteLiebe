package ormwa.projekt.fran_josipovic.echteliebe.auth

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)