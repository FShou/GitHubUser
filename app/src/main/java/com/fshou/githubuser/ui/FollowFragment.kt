package com.fshou.githubuser.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.fshou.githubuser.data.remote.response.User
import com.fshou.githubuser.databinding.FragmentFollowBinding
import com.fshou.githubuser.ui.UserDetailActivity.Companion.username
import com.fshou.githubuser.data.Result

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() =_binding
    private  val followViewModel by viewModels<FollowViewModel>()
    private val viewModel by viewModels <FollowViewModelNew>{
        ViewModelFactory.getInstance(requireActivity())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val index = arguments?.getInt(ARG_SECTION_NUMBER)
        if (index == 1) {
            // get Follower List show to ui
//            followViewModel.apply {
//                getFollowerByUserName(UserDetailActivity.username)
//                followerList.observe(viewLifecycleOwner) { setUserList(it) }
//                followerIsLoading.observe(viewLifecycleOwner) { showLoading(it) }
//                followerToastText.observe(viewLifecycleOwner) {
//                    it.getContentIfNotHandled()?.let { toastText ->
//                        Toast.makeText(requireContext(),toastText,Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
            viewModel.apply {

                followerList.observe(viewLifecycleOwner) { resultHandler(it) }
            }
        } else {
            // get following List show to ui
//            followViewModel.apply {
//                getFollowingByUserName(UserDetailActivity.username)
//                followingList.observe(viewLifecycleOwner) { setUserList(it) }
//                followingIsLoading.observe(viewLifecycleOwner) { showLoading(it) }
//                followingToastText.observe(viewLifecycleOwner) {
//                    it.getContentIfNotHandled()?.let { toastText ->
//                        Toast.makeText(requireContext(),toastText,Toast.LENGTH_SHORT).show()
//                    }
//                }
//            }
            viewModel.apply {

                followingList.observe(viewLifecycleOwner) { resultHandler(it) }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


private fun resultHandler(result: Result<List<User>>) {
    when(result){
        is Result.Loading -> binding?.progressBar?.visibility = View.VISIBLE
        is Result.Error -> {
            binding?.progressBar?.visibility = View.GONE
            val message = result.error.getContentIfNotHandled()
            Toast.makeText(requireActivity(),message,Toast.LENGTH_SHORT).show()
        }
        is Result.Success -> {
            binding?.progressBar?.visibility = View.GONE
            setUserList(result.data)
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
        binding?.rvFollower?.apply {
            layoutManager = rvFollowerLayoutManager
            adapter = userListAdapter
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.progressBar?.visibility  = if (isLoading) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
    companion object {
        const val ARG_SECTION_NUMBER = "section_number"

    }

}