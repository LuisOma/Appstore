package com.example.appstore.ui.home

import android.os.Bundle
import android.widget.Filter
import android.widget.Filterable
import androidx.navigation.findNavController
import com.example.appstore.R
import com.example.appstore.base.BaseAdapter
import com.example.appstore.data.domain.AppsDetailModel
import com.example.appstore.databinding.ItemAppBinding
import com.example.appstore.ui.util.Constants.Companion.APP
import com.squareup.picasso.Picasso
import java.util.*

class AppsAdapter : BaseAdapter<AppsDetailModel, ItemAppBinding>(ItemAppBinding::inflate),
    Filterable {

    private var filteredList: List<AppsDetailModel> = ArrayList()

    init {
        filteredList = itemList
    }


    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ItemAppBinding>,
        position: Int
    ) {
        val item = filteredList[position]

        Picasso.with(holder.binding.appImage.context).load(item.image).fit().centerCrop()
            .placeholder(R.drawable.ic_camera)
            .error(R.drawable.ic_camera)
            .into(holder.binding.appImage)

        holder.binding.appNameText.text = item.nombre
        holder.binding.devText.text = item.dev
        holder.binding.appQualificationText.text = item.rating.toString()
        if(item.price >= 0.5) {
            holder.binding.priceText.text = "$ ${item.price}"
        }
        holder.binding.root.setOnClickListener {
            holder.binding.cardView.setBackgroundResource(R.color.amber_600)
            listener?.invoke(it, item, position)
            holder.binding.cardView.setBackgroundResource(R.color.white)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filteredList = itemList
                } else {
                    val resultList = ArrayList<AppsDetailModel>()
                    for (row in itemList) {
                        if ((row.nombre.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))|| row.category.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT)) )
                        ) {
                            resultList.add(row)
                        }
                    }
                    filteredList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as ArrayList<AppsDetailModel>
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

}