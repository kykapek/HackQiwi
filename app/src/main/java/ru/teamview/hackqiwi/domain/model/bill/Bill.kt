package ru.teamview.hackqiwi.domain.model.bill

data class Bill(
    val amount: Amount,
    val billPaymentMethodsType: List<String>,
    val comment: String,
    val expirationDateTime: String
)

/*
{
   "amount": {
     "currency": "RUB",
     "value": 42.24
   },
   "billPaymentMethodsType": [
      "QIWI_WALLET",
      "SBP"
   ],
   "comment": "Spasibo",
   "expirationDateTime": "2019-09-13T14:30:00+03:00",
   "customFields": {}
}
 */
