package com.example.iconfinder.home.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.iconfinder.R
import com.example.iconfinder.base.BaseRecyclerViewAdapter
import com.example.iconfinder.pojo.Emoji
import com.example.iconfinder.utils.makeGone
import com.example.iconfinder.utils.makeVisible

class IconAdapter(
    private val list: MutableList<Emoji>,
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
    
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        
        private val iconName: TextView = itemView.findViewById(R.id.iconName)
        private val paidTag: ImageView = itemView.findViewById(R.id.paidLogo)
        private val iconImage: ImageView = itemView.findViewById(R.id.logoImage)
        private val downloadButton: ImageView = itemView.findViewById(R.id.downloadButton)
        private val priceText: TextView = itemView.findViewById(R.id.priceText)
        
        fun bindItems(item: Emoji) {
            iconName.text = item.emojiName
            
            if (item.paid){
                paidTag.makeVisible()
                downloadButton.makeGone()
                priceText.makeVisible()
                priceText.text = item.price
            } else {
                paidTag.makeGone()
                downloadButton.makeVisible()
                priceText.makeGone()
            }
        }
        
    }

    interface Onclick {
        fun onIconClicked(emoji: Emoji)
    }

}