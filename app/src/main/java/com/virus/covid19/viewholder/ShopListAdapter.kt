package com.virus.covid19.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.virus.covid19.R
import com.virus.covid19.database.entities.Shops
import com.virus.covid19.interfaces.CardClickListener
import kotlinx.android.synthetic.main.drawer_item.view.*

class ShopListAdapter(private val shopList:List<Shops>,private val cardClickListener: CardClickListener):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mRecyclerViewHolder: RecyclerView.ViewHolder? = null

    class ShopListViewHolder(viewGroup: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_shop, viewGroup, false)
    ) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mRecyclerViewHolder= ShopListViewHolder(parent)

        return mRecyclerViewHolder as RecyclerView.ViewHolder
    }

    override fun getItemCount(): Int {

       return shopList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder=holder as ShopListViewHolder
        viewHolder.itemView.title.text=shopList.get(position).shopName

        viewHolder.itemView.setOnClickListener(View.OnClickListener {
            cardClickListener.getCategoryName(shopList.get(position).shopType!!)
            cardClickListener.onCardClick(shopList.get(position).shopName!!)
        })

    }
}