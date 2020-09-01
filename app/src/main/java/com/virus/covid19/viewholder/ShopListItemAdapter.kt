package com.virus.covid19.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.virus.covid19.R
import com.virus.covid19.database.entities.Product
import com.virus.covid19.interfaces.addCartListener
import kotlinx.android.synthetic.main.item_product.view.*

class ShopListItemAdapter (private val shopList:List<Product>,private val cartListener: addCartListener): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mRecyclerViewHolder: RecyclerView.ViewHolder? = null

    class ShopListItemViewHolder(viewGroup: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_product, viewGroup, false)
    ) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mRecyclerViewHolder= ShopListItemViewHolder(parent)

        return mRecyclerViewHolder as RecyclerView.ViewHolder
    }

    override fun getItemCount(): Int {

        return shopList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder=holder as ShopListItemViewHolder
        viewHolder.itemView.product.text=shopList.get(position).product_name
        viewHolder.itemView.offerprice.text=shopList.get(position).price
        viewHolder.itemView.desc.text=shopList.get(position).product_desc
        viewHolder.itemView.addtocart.setOnClickListener(View.OnClickListener {
            cartListener.addToCart(shopList.get(position))
        })

    }
}