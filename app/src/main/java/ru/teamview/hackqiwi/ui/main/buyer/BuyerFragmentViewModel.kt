package ru.teamview.hackqiwi.ui.main.buyer

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.teamview.hackqiwi.domain.model.bill.Bill
import ru.teamview.hackqiwi.domain.model.bill.BillResponse
import ru.teamview.hackqiwi.domain.usecase.GetBillUseCase
import ru.teamview.hackqiwi.networkUtils.Resource
import javax.inject.Inject

@HiltViewModel
class BuyerFragmentViewModel @Inject constructor(
    private val getBillUseCase: GetBillUseCase
) : ViewModel() {

    fun getBill(bill: Bill): LiveData<Resource<BillResponse>> {
        return getBillUseCase.execute(bill)
    }
}