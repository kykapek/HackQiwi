package ru.teamview.hackqiwi.data.datasource

import ru.teamview.hackqiwi.HackQiwiApp
import ru.teamview.hackqiwi.data.service.PhoneService
import ru.teamview.hackqiwi.domain.model.auth.Login
import ru.teamview.hackqiwi.domain.model.registration.SendConfirm
import ru.teamview.hackqiwi.domain.model.registration.SendRegistration
import ru.teamview.hackqiwi.networkUtils.BaseDataSource
import javax.inject.Inject

class PhoneDataSource @Inject constructor(
    private val phoneService: PhoneService
) : BaseDataSource() {

    suspend fun sendRegistration(sendRegistration: SendRegistration) = getResult {
        phoneService.sendRegistration(sendRegistration)
    }

    suspend fun sendConfirm(sendConfirm: SendConfirm) = getResult {
        phoneService.sendConfirm(sendConfirm.code, sendConfirm.token)
    }

    suspend fun login() = getResult {
        phoneService.login(Login(HackQiwiApp.getInstance().getPhone()!!, HackQiwiApp.getInstance().getPassword()!!))
    }
}