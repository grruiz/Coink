package com.fpr.appcoink.ui.createpost

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fpr.appcoink.R
import com.fpr.appcoink.core.Result
import com.fpr.appcoink.data.remote.postuser.PostUserDataSource
import com.fpr.appcoink.databinding.FragmentCreatePostBinding
import com.fpr.appcoink.domain.postuser.PostUserRepoImp
import com.fpr.appcoink.presentation.postuser.PostUserViewModel
import com.fpr.appcoink.presentation.postuser.PostUserodelFactory

class CreatePostFragment : Fragment(R.layout.fragment_create_post) {

    private lateinit var binding: FragmentCreatePostBinding
    private val viewModel by viewModels<PostUserViewModel> {
        PostUserodelFactory(
            PostUserRepoImp(
                PostUserDataSource()
            )
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreatePostBinding.bind(view)

        binding.btnCreatePost.setOnClickListener {
            if (!binding.editTextDescExpense.text.isNullOrEmpty() && !binding.editTextExpense.text.isNullOrEmpty()) {
                Log.d("Cosa", "onViewCreated: AAAAAAAAAAA")
                viewModel.uploadPost(
                    post_expense = binding.editTextExpense.text.toString().trim(),
                    post_desc_expense = binding.editTextDescExpense.text.toString().trim()
                ).observe(viewLifecycleOwner, { result ->
                    when (result) {
                        is Result.Loading -> {
                            Toast.makeText(requireContext(), "Uploading post", Toast.LENGTH_SHORT)
                                .show()
                        }
                        is Result.Success -> {
                            findNavController().navigate(R.id.action_createPostFragment_to_homeScreenFragment)
                        }
                        is Result.Failure -> {
                            Toast.makeText(
                                requireContext(),
                                "Error ${result.exception}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
            }
                Toast.makeText(requireContext(), "No se ha podido crear el gasto, rellena todos los campos por favor", Toast.LENGTH_SHORT)
                    .show()

        }
    }
}
