package com.example.appstore.ui.detail

import com.example.appstore.R
import com.example.appstore.base.BaseAdapter
import com.example.appstore.data.domain.CommentsDetailModel
import com.example.appstore.databinding.ItemCommentBinding
import com.squareup.picasso.Picasso

class CommentsAdapter : BaseAdapter<CommentsDetailModel, ItemCommentBinding>(ItemCommentBinding::inflate) {
    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ItemCommentBinding>,
        position: Int
    ) {
        val item = itemList[position]
        Picasso.with(holder.binding.userImage.context).load(item.imageUser).fit().centerCrop()
            .placeholder(R.drawable.ic_camera)
            .error(R.drawable.ic_camera)
            .into(holder.binding.userImage)

        holder.binding.userTextview.text = item.idComment
        holder.binding.commentTextview.text = item.comment
    }
}