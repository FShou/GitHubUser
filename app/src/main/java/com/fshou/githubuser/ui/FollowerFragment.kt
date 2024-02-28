package com.fshou.githubuser.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fshou.githubuser.R
import com.fshou.githubuser.data.response.UserFollowersResponseItem
import com.fshou.githubuser.databinding.FragmentFollowerBinding



class FollowerFragment : Fragment() {

    private lateinit var binding: FragmentFollowerBinding
    private val adapter = FollowerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFollowerBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollower.layoutManager = layoutManager
        binding.rvFollower.adapter = adapter

        val followerViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())[FollowerViewModel::class.java]

        followerViewModel.followerList.observe(requireActivity()){
            setList(it)
        Log.d("FRAGMENT FOLLOWER",it.toString())
        }

        followerViewModel.followingList.observe(requireActivity()) {
            setList(it)
            Log.d("FRAGMENT FOLLOWING",it.toString())
        }

        val index = arguments?.getInt(ARGS_SECTION_NUMBER,0)
        binding.tvLabel.text = index.toString()

        if (index == 1) {
            followerViewModel.getFollowerByUserName(UserDetailActivity.username)
            followerViewModel.followerList.value?.let { setList(it) }
        }else {
            followerViewModel.getFollowingByUserName(UserDetailActivity.username)
        }


    }

    companion object {
        const val ARGS_SECTION_NUMBER = "section_number"
    }

    private fun setList(followers: List<UserFollowersResponseItem>){
        adapter.submitList(followers)
    }
}