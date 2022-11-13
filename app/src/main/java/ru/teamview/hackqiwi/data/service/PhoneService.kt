package ru.teamview.hackqiwi.data.service

import android.util.Log
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import ru.teamview.hackqiwi.domain.model.auth.Auth
import ru.teamview.hackqiwi.domain.model.auth.Login
import ru.teamview.hackqiwi.domain.model.registration.Confirm
import ru.teamview.hackqiwi.domain.model.registration.GetRegistration
import ru.teamview.hackqiwi.domain.model.registration.SendConfirm
import ru.teamview.hackqiwi.domain.model.registration.SendRegistration

interface PhoneService {

    @POST("/v1/registration")
    suspend fun sendRegistration(@Body sendRegistration: SendRegistration) : Response<GetRegistration>

    @POST("registration/confirm")
    suspend fun sendConfirm(@Query("code") code: String, @Header("token") token: String) : Response<Confirm>

    @POST("auth/log")
    suspend fun login(@Body login: Login) : Response<Auth>
}