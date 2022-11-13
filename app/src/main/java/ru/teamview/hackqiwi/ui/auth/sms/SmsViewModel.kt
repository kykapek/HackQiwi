package ru.teamview.hackqiwi.ui.auth.sms

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.teamview.hackqiwi.domain.model.auth.Auth
import ru.teamview.hackqiwi.domain.model.registration.Confirm
import ru.teamview.hackqiwi.domain.model.registration.SendConfirm
import ru.teamview.hackqiwi.domain.usecase.ConfirmRegistrationUseCase
import ru.teamview.hackqiwi.domain.usecase.LoginUseCase
import ru.teamview.hackqiwi.networkUtils.Resource
import javax.inject.Inject

@HiltViewModel
class SmsViewModel @Inject constructor(
    private val confirmRegistrationUseCase: ConfirmRegistrationUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    fun confirmRegistration(sendConfirm: SendConfirm): LiveData<Resource<Confirm>> {
        return confirmRegistrationUseCase.execute(sendConfirm)
    }

    fun login(): LiveData<Resource<Auth>> {
        return loginUseCase.execute()
    }
}