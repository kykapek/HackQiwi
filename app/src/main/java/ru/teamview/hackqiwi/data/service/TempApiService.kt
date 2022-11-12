package ru.teamview.hackqiwi.data.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT
import ru.teamview.hackqiwi.domain.model.bill.Bill
import ru.teamview.hackqiwi.domain.model.bill.BillResponse

interface TempApiService {

    // /payin/v1/sites/{siteId}/bills/{billId} - в запросе хардкодно {siteId} проставляем
    // а {billId} представляет из себя уникальный айдишник счета, генерируемый на нашей стороне,
    //      когда перейдем на обращение к системе через токен залогиненного пользователя,
    //      billId отправлять не нужно будет, наоборот его нам будут отдавать
    @PUT("payin/v1/sites/sa3khn-06/bills/test-e01") //TODO пока в {billId} хардкод апаем это значение при каждом запросе
    suspend fun getBill(@Body bill: Bill,
                        @Header("Authorization") authorization: String,
                        @Header("Content-type") string: String = "application/json",
                        @Header("Accept") str: String = "application/json",
    ): Response<BillResponse>

    //TODO в месте вызова запроса подаем в значении хедера:  Bearer 4b940151-6c52-442a-9e52-9fcee548cf1f



}