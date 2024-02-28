package com.fshou.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPagerAdapter(activity: UserDetailActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragment = FollowerFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowerFragment.ARGS_SECTION_NUMBER, position)
        }
        return fragment
    }
}