package com.fpr.appcoink.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fpr.appcoink.MainActivity
import com.fpr.appcoink.R
import com.fpr.appcoink.core.BaseViewHolder
import com.fpr.appcoink.data.model.Post
import com.fpr.appcoink.data.remote.auth.AuthDataSource
import com.fpr.appcoink.databinding.FragmentEditPostBinding
import com.fpr.appcoink.databinding.PostItemViewBinding
import com.fpr.appcoink.domain.auth.AuthRepoImp
import com.fpr.appcoink.presentation.auth.AuthViewModel
import com.fpr.appcoink.presentation.auth.AuthViewModelFactory
import com.fpr.appcoink.ui.createpost.CreatePostFragment
import com.fpr.appcoink.ui.editpost.EditFragment
import com.fpr.appcoink.ui.main.HomeScreenFragment

import androidx.fragment.app.viewModels
import com.fpr.appcoink.core.TimeUtils
import com.fpr.appcoink.ui.main.HomeScreenFragmentDirections
import com.google.firebase.auth.FirebaseAuth

class HomeScreenAdapter(private val postList:List<Post>): RecyclerView.Adapter<BaseViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = PostItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeScreenViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is HomeScreenViewHolder -> {
                holder.bind(postList[position])
            }
        }
    }

    override fun getItemCount(): Int = postList.size


    private inner class HomeScreenViewHolder(val binding: PostItemViewBinding, val context: Context): BaseViewHolder<Post>(binding.root){
        override fun bind(item: Post) {
            val user = FirebaseAuth.getInstance().currentUser
            Glide.with(context).load(item.profile_picture).centerCrop().into(binding.profilePicture)
            binding.profileName.text = item.profile_name
            val createAt = item.post_timestamp?.time?.div(1000L)?.let {
                TimeUtils.getTimeAgo(it.toInt())
            }
            binding.postTimesamp.text = createAt
            binding.postDescExpense.text = item.post_desc_expense
            binding.postExpense.text =  item.post_expense + "€"
            binding.cardView.setOnClickListener {

                val action = user?.email?.let { email ->
                    HomeScreenFragmentDirections.actionHomeScreenFragmentToEditFragment(
                        email,binding.postDescExpense.text.toString(),binding.postExpense.text.toString(),item.id_post
                    )
                }
                if (action != null) {
                    it.findNavController().navigate(action)
                }
            }


        }

        }
    }

