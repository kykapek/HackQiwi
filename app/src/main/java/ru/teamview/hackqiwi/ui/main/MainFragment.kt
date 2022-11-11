package ru.teamview.hackqiwi.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import ru.teamview.hackqiwi.R
import ru.teamview.hackqiwi.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var mBinding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentMainBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
    }

    private fun initViewPager() = with(mBinding) {
        val adapter = MainPagerAdapter(requireActivity().supportFragmentManager, lifecycle)
        vpMain.adapter = adapter
        val names: Array<String> = arrayOf(
            SELLER,
            BUYER
        )
        TabLayoutMediator(tlMain, vpMain) { tab, position ->
            tab.text = names[position]
        }.attach()
    }

    companion object {
        const val SELLER = "Продавец"
        const val BUYER = "Покупатель"
    }
}