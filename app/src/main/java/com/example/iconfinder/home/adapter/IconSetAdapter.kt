package com.example.iconfinder.home.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.iconfinder.R
import com.example.iconfinder.base.BaseRecyclerViewAdapter
import com.example.iconfinder.pojo.IconSet
import kotlinx.android.synthetic.main.card_icon_set.view.*

class IconSetAdapter(
    private var list: List<IconSet>,
    private val listener: OnClick
) : BaseRecyclerViewAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(getView(R.layout.card_icon_set, parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> holder.bindItems(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun submitList(list: MutableList<IconSet>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems (item: IconSet) {

            with (itemView) {
                iconSetName.text = item.name

                iconSetCard.setOnClickListener {
                    listener.onIconSetClicked(item)
                }
            }
        }
    }

    interface OnClick {
        fun onIconSetClicked(iconSet: IconSet)
    }
}