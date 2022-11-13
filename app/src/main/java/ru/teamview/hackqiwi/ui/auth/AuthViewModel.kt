package ru.teamview.hackqiwi.ui.auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.teamview.hackqiwi.domain.usecase.ConfirmRegistrationUseCase
import ru.teamview.hackqiwi.domain.usecase.RegistrationUseCase
import ru.teamview.hackqiwi.domain.usecase.SendLoginUseCase
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    //private val sendLoginUseCase: SendLoginUseCase,
    private val registrationUseCase: RegistrationUseCase,
    private val confirmRegistrationUseCase: ConfirmRegistrationUseCase
    ) : ViewModel() {

    fun sendPhone() {

    }
}