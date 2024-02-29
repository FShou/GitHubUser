package com.fshou.githubuser.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fshou.githubuser.databinding.FragmentFollowerBinding



class FollowerFragment : Fragment() {

    private lateinit var binding: FragmentFollowerBinding
    private lateinit var followerViewModel: FollowerViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowerBinding.inflate(inflater,container,false)
        followerViewModel = ViewModelProvider(requireActivity(), ViewModelProvider.NewInstanceFactory())[FollowerViewModel::class.java]
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollower.layoutManager = layoutManager

        followerViewModel.isLoading.observe(requireActivity()){
            if (it){
                binding.progressBar.visibility = View.VISIBLE
            }else {
                binding.progressBar.visibility = View.GONE
            }
        }

        val index = arguments?.getInt(ARG_SECTION_NUMBER)
        if (index == 1) {
            followerViewModel.getFollowerByUserName(UserDetailActivity.username)
            followerViewModel.followerList.observe(requireActivity()){
                binding.rvFollower.adapter = FollowerAdapter(it)
            }
        }else{
            followerViewModel.getFollowingByUserName(UserDetailActivity.username)
            followerViewModel.followingList.observe(requireActivity()){
                binding.rvFollower.adapter = FollowerAdapter(it)
            }
        }
    }

    companion object {
        const val ARG_SECTION_NUMBER = "section_number"
    }

}