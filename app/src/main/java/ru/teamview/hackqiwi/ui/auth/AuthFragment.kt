package ru.teamview.hackqiwi.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.teamview.hackqiwi.R
import ru.teamview.hackqiwi.databinding.FragmentAuthBinding
import ru.teamview.hackqiwi.databinding.FragmentMainBinding
import ru.teamview.hackqiwi.ui.utils.onClick

class AuthFragment : Fragment() {

    private lateinit var mBinding: FragmentAuthBinding

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
            onShowMainFragment()
        }
    }

    private fun onShowMainFragment() {
        findNavController().navigate(R.id.mainFragment)
    }
}