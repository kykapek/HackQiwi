package ru.teamview.hackqiwi

import retrofit2.http.Header
import retrofit2.http.PUT

interface tempApiService {

    // /payin/v1/sites/{siteId}/bills/{billId} - в запросе хардкодно {siteId} проставляем
    // а {billId} представляет из себя уникальный айдишник счета, генерируемый на нашей стороне,
    //      когда перейдем на обращение к системе через токен залогиненного пользователя,
    //      billId отправлять не нужно будет, наоборот его нам будут отдавать
    @PUT("/payin/v1/sites/sa3khn-06/bills/test-e01") //TODO пока в {billId} хардкод апаем это значение при каждом запросе
    suspend fun getBill(@Header("Authorization") authorization: String): Any

    //TODO в месте вызова запроса подаем в значении хедера:  Bearer 4b940151-6c52-442a-9e52-9fcee548cf1f



}