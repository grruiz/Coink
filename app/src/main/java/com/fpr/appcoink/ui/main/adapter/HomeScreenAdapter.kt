package com.fpr.appcoink.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fpr.appcoink.core.BaseViewHolder
import com.fpr.appcoink.data.model.Post
import com.fpr.appcoink.databinding.PostItemViewBinding


//TODO Hacer que los elementos se les pueda hacer clicks para poder editar/borrar en el futuro
class HomeScreenAdapter(private val postList:List<Post>): RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnPostClickListener{
        fun onPostClick()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = PostItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HomeScreenViewHolder(itemBinding, parent.context)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is HomeScreenViewHolder -> holder.bind(postList[position])
        }
    }

    override fun getItemCount(): Int = postList.size


    private inner class HomeScreenViewHolder(
        val binding: PostItemViewBinding,
        val context: Context
    ): BaseViewHolder<Post>(binding.root){
        override fun bind(item: Post) {

            Glide.with(context).load(item.profile_picture).centerCrop().into(binding.profilePicture)
            binding.profileName.text = item.profile_name
            binding.postTimesamp.text = "Hace 2 horas"
            binding.postDescExpense.text = item.post_desc_expense
            binding.postExpense.text = item.post_expense
        }
    }
}

