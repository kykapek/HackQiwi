package ru.teamview.hackqiwi.data.datasource

import ru.teamview.hackqiwi.data.service.TempApiService
import ru.teamview.hackqiwi.domain.model.bill.Bill
import ru.teamview.hackqiwi.networkUtils.BaseDataSource
import javax.inject.Inject

class TempDataSource @Inject constructor(
    private val tempApiService: TempApiService
) : BaseDataSource() {

    suspend fun getBill(bill: Bill) = getResult {
        tempApiService.getBill(bill ,"Bearer 4b940151-6c52-442a-9e52-9fcee548cf1f")
    }
}