package ru.teamview.hackqiwi.data.repository

import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import ru.teamview.hackqiwi.data.datasource.PhoneDataSource
import ru.teamview.hackqiwi.domain.model.registration.SendConfirm
import ru.teamview.hackqiwi.domain.model.registration.SendRegistration
import javax.inject.Inject

class PhoneRepository @Inject constructor(
    private val phoneDataSource: PhoneDataSource
) {

    fun sendRegistration(sendRegistration: SendRegistration) = liveData(Dispatchers.IO) {
        emit(phoneDataSource.sendRegistration(sendRegistration))
    }

    fun sendConfirm(sendConfirm: SendConfirm) = liveData(Dispatchers.IO) {
        emit(phoneDataSource.sendConfirm(sendConfirm))
    }
}