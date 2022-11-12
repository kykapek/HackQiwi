package ru.teamview.hackqiwi.ui.main.seller

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import ru.teamview.hackqiwi.R
import ru.teamview.hackqiwi.databinding.FragmentBuyerBinding
import ru.teamview.hackqiwi.databinding.FragmentSellerBinding
import ru.teamview.hackqiwi.ui.utils.cameraPermissions
import ru.teamview.hackqiwi.ui.utils.onClick
import ru.teamview.hackqiwi.ui.utils.permissions
import ru.teamview.hackqiwi.ui.utils.show

class SellerFragment : Fragment() {

    private lateinit var mBinding: FragmentSellerBinding
    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentSellerBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initUi()
        checkPhotoPermission()
    }

    private fun checkPhotoPermission() {
        permissions(*cameraPermissions) {
            this.allGranted {
                initUi()
            }
            this.denied {

            }
        }
    }

    private fun initUi() = with(mBinding) {
        codeScannerView.show()
        codeScanner = CodeScanner(requireContext(), codeScannerView)
        codeScanner.formats = CodeScanner.ALL_FORMATS
        codeScanner.decodeCallback = DecodeCallback {
            activity?.runOnUiThread {
                Toast.makeText(activity, it.text, Toast.LENGTH_LONG).show()
            }
        }
        codeScanner.startPreview()
        codeScannerView.onClick {
            codeScanner.startPreview()
        }
    }

    override fun onResume() {
        super.onResume()
        //codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }
}