package ru.teamview.hackqiwi.data.repository

import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import ru.teamview.hackqiwi.data.datasource.TempDataSource
import ru.teamview.hackqiwi.domain.model.bill.Bill
import javax.inject.Inject

class TempBillsRepository @Inject constructor(
    private val tempDataSource: TempDataSource
) {

    fun getBill(bill: Bill) = liveData(Dispatchers.IO) {
        emit(tempDataSource.getBill(bill))
    }
}