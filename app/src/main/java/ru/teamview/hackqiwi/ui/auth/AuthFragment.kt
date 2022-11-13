package ru.teamview.hackqiwi.ui.auth

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.teamview.hackqiwi.HackQiwiApp
import ru.teamview.hackqiwi.R
import ru.teamview.hackqiwi.databinding.FragmentAuthBinding
import ru.teamview.hackqiwi.domain.model.registration.SendRegistration
import ru.teamview.hackqiwi.networkUtils.Resource
import ru.teamview.hackqiwi.ui.utils.*

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
        btnNext.onClick {
            sendRegistration(SendRegistration(etPhone.text.toString(), etPassword.text.toString()))
            it.hide()
            pbAuth.show()
        }
    }

    private fun sendRegistration(sendRegistration: SendRegistration) {
        viewModel.sendRegistration(sendRegistration).observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Resource.Status.SUCCESS -> {
                    Log.d(TAG, it.data!!.token)
                    saveData(it.data.token)
                    mBinding.btnNext.show()
                }
                Resource.Status.LOADING -> {
                    mBinding.pbAuth.show()
                    mBinding.btnNext.hide()
                }
                Resource.Status.ERROR -> {
                    mBinding.pbAuth.hide()
                    mBinding.btnNext.show()
                    toast("Что-то пошло не так. Попробуйте еще раз")
                }
            }
        })
    }

    private fun saveData(token: String) {
        HackQiwiApp.getInstance().saveAuthToken(token)
        HackQiwiApp.getInstance().savePhone(mBinding.etPhone.text.toString())
        HackQiwiApp.getInstance().savePassword(mBinding.etPassword.text.toString())
        onShowSmsFragment(mBinding.etPhone.text.toString())
    }

    private fun onShowMainFragment() {
        findNavController().navigate(R.id.mainFragment)
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

    companion object {
        const val TAG = "TEXT_COUNT"
        const val TAG1 = "I_WANT_TOKEN"
        const val PHONE_LENGTH = 15
        const val BORDER_POSITION = 15
    }
}