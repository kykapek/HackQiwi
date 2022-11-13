package ru.teamview.hackqiwi.ui.auth.sms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.teamview.hackqiwi.HackQiwiApp
import ru.teamview.hackqiwi.R
import ru.teamview.hackqiwi.databinding.FragmentAuthBinding
import ru.teamview.hackqiwi.databinding.FragmentSmsBinding
import ru.teamview.hackqiwi.domain.model.registration.SendConfirm
import ru.teamview.hackqiwi.networkUtils.Resource
import ru.teamview.hackqiwi.ui.auth.AuthViewModel
import ru.teamview.hackqiwi.ui.utils.onClick
import ru.teamview.hackqiwi.ui.utils.toast

@AndroidEntryPoint
class SmsFragment : Fragment() {

    private lateinit var mBinding: FragmentSmsBinding

    private val viewModel: SmsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentSmsBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() = with(mBinding) {
        tvPhone.text = arguments?.getString("phone")
        btnNext.onClick {
            setUpConfirmationObserver()
        }
    }

    private fun setUpConfirmationObserver() {
        viewModel.confirmRegistration(
            SendConfirm(mBinding.etPhone.text.toString(),
                HackQiwiApp.getInstance().getAuthToken()!!)
        ).observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Resource.Status.SUCCESS -> {
                    if (it.data?.message == "phone confirmed") {
                        toast("phone confirmed")
                        setUpLoginObserver()
                    }
                }
                Resource.Status.LOADING -> {

                }
                Resource.Status.ERROR -> {
                    toast("Что-то пошло не так")
                }
            }
        })
    }

    private fun setUpLoginObserver() {
        viewModel.login().observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Resource.Status.SUCCESS -> {
                    HackQiwiApp.getInstance().saveJwt(it.data!!.access_token)
                    findNavController().navigate(R.id.mainFragment)
                }
                Resource.Status.LOADING -> {

                }
                Resource.Status.ERROR -> {

                }
            }
        })
    }
}