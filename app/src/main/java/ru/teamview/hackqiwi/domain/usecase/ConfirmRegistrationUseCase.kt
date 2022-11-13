package ru.teamview.hackqiwi.domain.usecase

import androidx.lifecycle.LiveData
import ru.teamview.hackqiwi.data.repository.PhoneRepository
import ru.teamview.hackqiwi.domain.model.registration.Confirm
import ru.teamview.hackqiwi.domain.model.registration.SendConfirm
import ru.teamview.hackqiwi.networkUtils.Resource
import javax.inject.Inject

class ConfirmRegistrationUseCase @Inject constructor(
    private val phoneRepository: PhoneRepository
) {

    fun execute(sendConfirm: SendConfirm): LiveData<Resource<Confirm>> {
        return phoneRepository.sendConfirm(sendConfirm)
    }
}