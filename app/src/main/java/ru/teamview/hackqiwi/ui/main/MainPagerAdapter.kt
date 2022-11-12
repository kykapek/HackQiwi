package ru.teamview.hackqiwi.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.teamview.hackqiwi.ui.main.buyer.BuyerFragment
import ru.teamview.hackqiwi.ui.main.seller.SellerFragment

class MainPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragments: ArrayList<Fragment> = fullMenu()

    private fun fullMenu(): ArrayList<Fragment> {
        return arrayListOf(
            SellerFragment(),
            BuyerFragment()
        )
    }

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]
}