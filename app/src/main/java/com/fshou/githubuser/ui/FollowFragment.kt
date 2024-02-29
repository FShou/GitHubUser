package com.fshou.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels

import androidx.recyclerview.widget.LinearLayoutManager
import com.fshou.githubuser.data.response.User
import com.fshou.githubuser.databinding.FragmentFollowBinding


class FollowFragment : Fragment() {

    private lateinit var binding: FragmentFollowBinding
    private  val followViewModel by viewModels<FollowViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        followViewModel.isLoading.observe(requireActivity()) { showLoading(it) }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val index = arguments?.getInt(ARG_SECTION_NUMBER)
        if (index == 1) {
            // get Follower List show to ui
            followViewModel.apply {
                getFollowerByUserName(UserDetailActivity.username)
                followerList.observe(requireActivity()) { setUserList(it) }
            }
        } else {
            // get following List show to ui
            followViewModel.apply {
                getFollowingByUserName(UserDetailActivity.username)
                followingList.observe(requireActivity()) { setUserList(it) }
            }
        }
    }

    private fun setUserList(users: List<User>) {
        val userListAdapter = UserListAdapter(users)
        val rvFollowerLayoutManager = LinearLayoutManager(requireActivity())

        userListAdapter.apply {
            showArrow = false
            addIntent = false
        }
        binding.rvFollower.apply {
            layoutManager = rvFollowerLayoutManager
            adapter = userListAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
    }

}