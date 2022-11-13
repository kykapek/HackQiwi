package ru.teamview.hackqiwi.ui.main.buyer

import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import ru.teamview.hackqiwi.databinding.FragmentBuyerBinding
import ru.teamview.hackqiwi.domain.model.bill.Amount
import ru.teamview.hackqiwi.domain.model.bill.Bill
import ru.teamview.hackqiwi.networkUtils.Resource
import java.math.RoundingMode
import java.text.DecimalFormat

@AndroidEntryPoint
class BuyerFragment : Fragment() {
    private var _binding: FragmentBuyerBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BuyerFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuyerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        val qrText = binding.qrTextEditText
        val priceInput = binding.priceInputLayout.editText
        val df = DecimalFormat("#.00")
        df.roundingMode = RoundingMode.DOWN

        binding.getDataForQrBtn.setOnClickListener {

            //TODO ТЕСТОВЫЕ ЛОГИ, УДАЛИТЬ
             val value =  df.format(priceInput?.text.toString().toDouble())
            Log.d("ASS","$value")

            //запрашиваем по клику данные для генерации QR-кода введенные в поле priceInput
            if (TextUtils.isEmpty(priceInput?.text.toString())) {

                //TODO ПЕРЕДЕЛАТЬ НА ВЫВОД ОШИБКИ САМИМ ЭДИТТЕКСТОМ, ЕСЛИ БУДЕТ ВРЕМЯ
                //выводим тост, если поле пустое
                Toast.makeText(context, "Введите сумму платежа", Toast.LENGTH_SHORT).show()
            } else {
                setUpBillObserver(
                    Bill(
                        amount = Amount(
                            currency = "RUB",
                            value =   (priceInput?.text.toString().toDouble())),
//                            value = df.format(priceInput?.text.toString().toDouble())),
//                            value = DecimalFormat("#.##").format(priceInput?.text.toString().toBigDecimal()).toDouble()),
                        arrayListOf("QIWI_WALLET", "SBP"),
                        "Эквайринг от покупателя",
                        "2022-11-13T14:30:00+03:00"
                    )
                )
            }
        }

        binding.generateQrBtn.setOnClickListener {
            //проверяем не пустое ли поле с текстом
            if (TextUtils.isEmpty(qrText.text.toString())) {

                //выводим тост, если поле пустое
                Toast.makeText(context, "Enter your message", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    //используем метод для получения битмапа QR кода из введенного юзером текста
                    val bitmap = generateQrForBitmap(qrText.text.toString())

                    //проставляем битмапу imageView лейаута
                    binding.qrImg.setImageBitmap(bitmap)

                } catch (e: Exception) {

                    //@TODO добавить вывод какого диалога в юзеринтерфейсе в случае отказа

                    //выводим стактрейс экспешена в случае отказа
                    e.printStackTrace()
                }
            }
        }
    }

    private fun setUpBillObserver(bill: Bill) {
        viewModel.getBill(bill).observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    Log.d(TAG, it.data.toString())
                    //в случае успешного получения ссылки для выставления счета,
                    //генерируем из нее битмап QR-кода и отображаем на экране
                    if (it.data?.payUrl != null) {
                        try {
                            val bitmapFromWeb = generateQrForBitmap(it.data.payUrl)
                            binding.qrImg.setImageBitmap(bitmapFromWeb)

                        } catch (e: Exception) {

                            //@TODO добавить вывод какого диалога в юзеринтерфейсе в случае отказа

                            //выводим стактрейс экспешена в случае отказа
                            e.printStackTrace()
                        }

                    }
                }
                Resource.Status.LOADING -> {
                    Log.d(TAG, it.message.toString())
                }
                Resource.Status.ERROR -> {
                    Log.d(TAG, it.message.toString())
                }
            }
        })
    }

    private fun generateQrForBitmap(textForQr: String): Bitmap {

        //@TODO можно написать какой-то свой метод вычисления dimens вместо юза значения "на глаз"

        //инициализируем энкодер
        val qrEncoder = QRGEncoder(textForQr, null, QRGContents.Type.TEXT, 200)

        //получаем и возвращаем битмап QR кода
        return qrEncoder.getBitmap(0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "Buyer"
    }
}