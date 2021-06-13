package com.fpr.appcoink.ui.editpost

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.fpr.appcoink.R
import com.fpr.appcoink.data.remote.auth.AuthDataSource
import com.fpr.appcoink.databinding.FragmentEditBinding
import com.fpr.appcoink.domain.auth.AuthRepoImp
import com.fpr.appcoink.presentation.auth.AuthViewModel
import com.fpr.appcoink.presentation.auth.AuthViewModelFactory
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class EditFragment : Fragment(R.layout.fragment_edit) {
    lateinit var binding: FragmentEditBinding
    private val args: EditFragmentArgs by navArgs()
    private val viewModel by viewModels<AuthViewModel> {
        AuthViewModelFactory(AuthRepoImp(AuthDataSource()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentEditBinding.bind(view)
        val postDescExpense = args.postDescExpense
        val postExpense = args.postExpense
        binding.editTextDescExpense.setText(postDescExpense)
        binding.editTextExpense.setText(postExpense)
        binding.btnDeletePost.setOnClickListener {
            deletePost()
        }
        binding.btnUpdatePost.setOnClickListener {
            updatePost()
        }
    }



    private fun updatePost() = CoroutineScope(Dispatchers.Main).launch {
        val postQuery = FirebaseFirestore.getInstance().collection(args.email)
            .whereEqualTo("id_post", args.uuid)
            .get()
            .await()

        if(postQuery.documents.isNotEmpty()) {
            for(document in postQuery) {
                try {
                    //personCollectionRef.document(document.id).update("age", newAge).await()
                    FirebaseFirestore
                        .getInstance()
                        .collection(args.email)
                        .document(document.id).set(getPost(),
                        SetOptions.merge()
                    ).await()
                    Toast.makeText(requireContext(),"Se ha actualizado correctamente el post",Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_editFragment_to_homeScreenFragment)
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        } else {
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "No persons matched the query.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun deletePost() = CoroutineScope(Dispatchers.Main).launch {
        val postQuery = FirebaseFirestore.getInstance().collection(args.email)
            .whereEqualTo("id_post", args.uuid)
            .get()
            .await()

        if (postQuery.documents.isNotEmpty()) {
            for (document in postQuery) {
                try {
                    FirebaseFirestore
                        .getInstance()
                        .collection(args.email)
                        .document(document.id).delete().await()
                    findNavController().navigate(R.id.action_editFragment_to_homeScreenFragment)
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), "${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        } else {
            withContext(Dispatchers.Main) {
                Toast.makeText(requireContext(), "No hay post", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun getPost(): Map<String, Any> {

        val editTextDescExpense = binding.editTextDescExpense.text.toString()
        val editTextExpense = binding.editTextExpense.text.toString()
        val map = mutableMapOf<String, Any>()
        if(editTextDescExpense.isNotEmpty()) {
            map["post_desc_expense"] = editTextDescExpense
        }
        if(editTextExpense.isNotEmpty()) {
            map["post_expense"] = editTextExpense
        }
        return map
    }
}