package ru.teamview.hackqiwi.domain.usecase

import androidx.lifecycle.LiveData
import ru.teamview.hackqiwi.data.repository.PhoneRepository
import ru.teamview.hackqiwi.domain.model.registration.GetRegistration
import ru.teamview.hackqiwi.domain.model.registration.SendRegistration
import ru.teamview.hackqiwi.networkUtils.Resource
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(
    private val phoneRepository: PhoneRepository
) {

    fun execute(sendRegistration: SendRegistration): LiveData<Resource<GetRegistration>> {
        return phoneRepository.sendRegistration(sendRegistration)
    }
}