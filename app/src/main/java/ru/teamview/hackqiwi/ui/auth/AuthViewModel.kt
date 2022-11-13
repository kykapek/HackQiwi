package ru.teamview.hackqiwi.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.teamview.hackqiwi.domain.model.registration.GetRegistration
import ru.teamview.hackqiwi.domain.model.registration.SendRegistration
import ru.teamview.hackqiwi.domain.usecase.ConfirmRegistrationUseCase
import ru.teamview.hackqiwi.domain.usecase.RegistrationUseCase
import ru.teamview.hackqiwi.domain.usecase.SendLoginUseCase
import ru.teamview.hackqiwi.networkUtils.Resource
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    //private val sendLoginUseCase: SendLoginUseCase,
    private val registrationUseCase: RegistrationUseCase,
    private val confirmRegistrationUseCase: ConfirmRegistrationUseCase
    ) : ViewModel() {

    fun sendRegistration(sendRegistration: SendRegistration): LiveData<Resource<GetRegistration>> {
        return registrationUseCase.execute(sendRegistration)
    }
}