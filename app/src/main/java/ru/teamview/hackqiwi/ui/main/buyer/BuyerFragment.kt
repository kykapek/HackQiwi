package ru.teamview.hackqiwi.ui.main.buyer

import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import ru.teamview.hackqiwi.databinding.FragmentBuyerBinding

private var _binding: FragmentBuyerBinding? = null
private val binding get() = _binding!!

//переменная для хранения битмапа
lateinit var bitmap: Bitmap
//переменная для хранения энкодера
lateinit var qrEncoder: QRGEncoder


class BuyerFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TODO нормально заимплементить вью модель
        val viewModel: BuyerFragmentViewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBuyerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}