package ru.teamview.hackqiwi.domain.usecase

import androidx.lifecycle.LiveData
import ru.teamview.hackqiwi.data.repository.TempBillsRepository
import ru.teamview.hackqiwi.domain.model.bill.Bill
import ru.teamview.hackqiwi.domain.model.bill.BillResponse
import ru.teamview.hackqiwi.networkUtils.Resource
import javax.inject.Inject

class GetBillUseCase @Inject constructor(
    private val tempBillsRepository: TempBillsRepository
) {

    fun execute(bill: Bill): LiveData<Resource<BillResponse>> {
        return tempBillsRepository.getBill(bill)
    }
}