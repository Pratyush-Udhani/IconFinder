package com.example.iconfinder.home.adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.iconfinder.R
import com.example.iconfinder.base.BaseRecyclerViewAdapter
import com.example.iconfinder.pojo.Icon
import com.example.iconfinder.utils.makeGone
import com.example.iconfinder.utils.makeVisible
import kotlinx.android.synthetic.main.card_emoji.view.*

class IconAdapter(
    private var list: MutableList<Icon>,
    private val listener: Onclick
) : BaseRecyclerViewAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(getView(R.layout.card_emoji, parent))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> holder.bindItems(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }


    fun submitList(list: MutableList<Icon>) {
        this.list = list
        Log.d("TAG!!!!", "inside adp ${this.list.size}")
        notifyDataSetChanged()
    }

    fun checkAdapter() {
        if (list.isNotEmpty()) {
            list.clear()
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bindItems(item: Icon) {

            with(itemView) {
                if (item.rasterSizes.size > 6)
                    logoImage.load(item.rasterSizes[6].formats[0].previewUrl) {
                        crossfade(true)
                        placeholder(R.drawable.ic_loading)
                    }
                else
                    logoImage.load(item.rasterSizes[0].formats[0].previewUrl) {
                        crossfade(true)
                        placeholder(R.drawable.ic_loading)
                    }

                if (item.isPremium) {
                    paidLogo.makeVisible()
                    downloadButton.makeGone()
                    priceText.makeVisible()

                    if (item.prices.isNotEmpty())
                        priceText.text = "${item.prices[0].currency} ${item.prices[0].price}"

                } else {
                    paidLogo.makeGone()
                    downloadButton.makeVisible()
                    priceText.makeGone()

                    emojiCard.setOnClickListener {
                        listener.onIconClicked(item)
                    }
                }
            }
        }
        
    }

    interface Onclick {
        fun onIconClicked(icon: Icon)
    }

}