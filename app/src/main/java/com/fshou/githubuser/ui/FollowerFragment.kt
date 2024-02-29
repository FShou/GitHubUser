package com.fshou.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fshou.githubuser.data.response.User
import com.fshou.githubuser.databinding.FragmentFollowerBinding


class FollowerFragment : Fragment() {

    private lateinit var binding: FragmentFollowerBinding
    private lateinit var followerViewModel: FollowerViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowerBinding.inflate(inflater, container, false)
        followerViewModel = ViewModelProvider(
            requireActivity(),
            ViewModelProvider.NewInstanceFactory()
        )[FollowerViewModel::class.java]
        followerViewModel.isLoading.observe(requireActivity()) { showLoading(it) }
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val index = arguments?.getInt(ARG_SECTION_NUMBER)
        if (index == 1) {
            // get Follower List show to ui
            followerViewModel.apply {
                getFollowerByUserName(UserDetailActivity.username)
                followerList.observe(requireActivity()) { setUserList(it) }
            }
        } else {
            // get following List show to ui
            followerViewModel.apply {
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