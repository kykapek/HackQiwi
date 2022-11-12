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

private var _binding: FragmentBuyerBinding? = null
private val binding get() = _binding!!

//переменная для хранения битмапа
lateinit var bitmap: Bitmap
//переменная для хранения энкодера
lateinit var qrEncoder: QRGEncoder

@AndroidEntryPoint
class BuyerFragment : Fragment() {

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
        setUpBillObserver(Bill(Amount("RUB", 42.24), arrayListOf("QIWI", "SBP"), "Spasibo", "2022-11-13T14:30:00+03:00"))
    }

    private fun initUi() {
        val qrText = binding.qrTextEditText
        val qrImage = binding.qrImg

        binding.getDataForQrBtn.setOnClickListener {

        }

        binding.generateQrBtn.setOnClickListener {
            //проверяем не пустое ли поле с текстом
            if (TextUtils.isEmpty(qrText.text.toString())) {

                //выводим тост, если поле пустое
                Toast.makeText(context, "Enter your message", Toast.LENGTH_SHORT).show()
            } else {

                //@TODO можно написать какой-то свой метод вычисления dimens вместо юза значения "на глаз"

                try {
                    //инициализируем энкодер
                    qrEncoder = QRGEncoder(qrText.text.toString(), null, QRGContents.Type.TEXT, 200)

                    //инициализируем битмапу
                    bitmap = qrEncoder.getBitmap(0)

                    //проставляем битмапу imageView лейаута
                    qrImage.setImageBitmap(bitmap)
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
            when(it.status) {
                Resource.Status.SUCCESS -> {
                    Log.d(TAG, it.data.toString())
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "Buyer"
    }
}