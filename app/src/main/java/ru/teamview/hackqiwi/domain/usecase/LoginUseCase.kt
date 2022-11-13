package ru.teamview.hackqiwi.domain.usecase

import androidx.lifecycle.LiveData
import ru.teamview.hackqiwi.data.repository.PhoneRepository
import ru.teamview.hackqiwi.domain.model.auth.Auth
import ru.teamview.hackqiwi.networkUtils.Resource
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val phoneRepository: PhoneRepository
) {

    fun execute(): LiveData<Resource<Auth>> {
        return phoneRepository.login()
    }
}