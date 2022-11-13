package ru.teamview.hackqiwi.ui.auth

import android.app.ProgressDialog.show
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.teamview.hackqiwi.R
import ru.teamview.hackqiwi.databinding.FragmentAuthBinding
import ru.teamview.hackqiwi.databinding.FragmentMainBinding
import ru.teamview.hackqiwi.ui.utils.getBaseCountryCode
import ru.teamview.hackqiwi.ui.utils.hideKeyboard
import ru.teamview.hackqiwi.ui.utils.onClick
import ru.teamview.hackqiwi.ui.utils.show

@AndroidEntryPoint
class AuthFragment : Fragment() {

    private lateinit var mBinding: FragmentAuthBinding

    private lateinit var regex: Regex

    private val viewModel: AuthViewModel by viewModels()

    private var phone: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentAuthBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() = with(mBinding) {
        /*
        btnNext.onClick {
            onShowMainFragment()
        }
         */

    }

    private fun setUpEditText() {

        mBinding.etPhone.setOnTouchListener { v, event ->
            mBinding.etPhone.showKeyboard()
            return@setOnTouchListener mBinding.etPhone.hasFocus()
        }
        mBinding.etPhone.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                mBinding.etPhone.setSelection(
                    mBinding.etPhone.text!!.length
                )
            }
        }

        regex = Regex("(\\+?\\d)(\\d{0,3})(\\d{0,3})(\\d{0,2})(\\d{0,2})")

        mBinding.etPhone.doOnTextChanged { text, position, deleted, count ->
            if (phone == text.toString()) {
                return@doOnTextChanged
            }
            phone = text.toString().replace(" ", "")
            Log.d(TAG, "$count---$position---$deleted--$text")
            if (text!!.startsWith(getBaseCountryCode())) {
                if (text.length == PHONE_LENGTH + 1 && deleted == 0) {
                    setUpObserver(text.toString())
                }
            } else {
                if (position < BORDER_POSITION && text.length > BORDER_POSITION) {
                    Log.d(TAG, "ERROR TEXT: ___ ${text.subSequence(3, text.length)}")
                    phone = getBaseCountryCode() + text.subSequence(3, text.length)
                } else {
                    phone = getBaseCountryCode()
                }
            }

            val phoneParts = regex.find(phone)
                ?.groupValues
                ?.drop(1)
                ?.filter { part ->
                    part.isNotEmpty()
                }
            Log.d(TAG, "phoneParts --- ${phoneParts.toString()}")
            phone = phoneParts!!.joinToString(" ")
            Log.d(TAG, "phone --- '$phone'")
            mBinding.etPhone.setText(phone)
            mBinding.etHide.hint = formHintString(phone)
            mBinding.etPhone.setSelection(position + count + (phone.length - text.length))
        }

        mBinding.etPhone.onFocusChangeListener = View.OnFocusChangeListener { view, hasfocus ->
            if (hasfocus) {
                mBinding.etPhone.setText(getBaseCountryCode())
            } else {
                mBinding.etPhone.setHint(R.string.phone_default_number)
            }
        }

        mBinding.etPhone.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if (keyCode == KeyEvent.KEYCODE_ENTER && phone.length == PHONE_LENGTH + 1 && event?.action == KeyEvent.ACTION_UP) {
                    //onShowSmsFragment(phone)
                    setUpObserver(mBinding.etPhone.text.toString())
                }
                return false
            }
        })
    }

    private fun setUpObserver(text: String) {
        viewModel.sendPhone(Phone(text.replace(" ", ""))).observeOnce(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    Log.d(it.data.toString(), TAG1)
                    onShowSmsFragment(text)
                    DarsApp.getInstance().saveToken(it.data.toString())
                }
                Resource.Status.LOADING -> {

                }
                Resource.Status.ERROR -> {
                    Log.d(it.message, TAG1)
                    onShowErrorPhone()
                }
            }
        })
    }

    private fun onShowMainFragment() {
        findNavController().navigate(R.id.mainFragment)
    }

    private fun formHintString(string: String): String {
        val defaultNumber = getString(R.string.phone_default_number)
        return string + defaultNumber.substring(string.length)
    }

    private fun onShowSmsFragment(phone: String) {
        hideKeyboard()
        mBinding.tvToolbar.apply {
            show()
            text = getString(R.string.base_approval)
        }
        val bundle = bundleOf("phone" to phone)
        findNavController().navigate(R.id.smsBsd, bundle)
    }

    private fun onShowErrorPhone() {
        hideKeyboard()
        findNavController().navigate(R.id.errorPhoneBsd)
    }

    companion object {
        const val TAG = "TEXT_COUNT"
        const val TAG1 = "I_WANT_TOKEN"
        const val PHONE_LENGTH = 15
        const val BORDER_POSITION = 15
    }
}