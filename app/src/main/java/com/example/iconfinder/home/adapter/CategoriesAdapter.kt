package com.example.iconfinder.home.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.iconfinder.R
import com.example.iconfinder.base.BaseRecyclerViewAdapter
import com.example.iconfinder.pojo.Category
import kotlinx.android.synthetic.main.card_category.view.*

class CategoriesAdapter(
    private val list: List<Category>,
    private val listener: Onclick
): BaseRecyclerViewAdapter() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(getView(R.layout.card_category, parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> holder.bindItems(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(item: Category) {

             with (itemView) {
                categoryName.text = item.name

                categoryCard.setOnClickListener {
                    listener.onCategoryClicked(item)
                }
             }
        }
    }

    interface Onclick {
        fun onCategoryClicked(category: Category)
    }
}