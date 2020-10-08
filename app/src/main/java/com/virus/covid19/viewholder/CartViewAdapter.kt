package com.virus.covid19.viewholder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.virus.covid19.R
import com.virus.covid19.application.GlobalClass
import com.virus.covid19.database.entities.Product
import com.virus.covid19.interfaces.CartListener
import com.virus.covid19.interfaces.addCartListener
import com.virus.covid19.textview.CustomTextView
import kotlinx.android.synthetic.main.category_list_item.view.*
import kotlinx.android.synthetic.main.category_list_item.view.product
import kotlinx.android.synthetic.main.item_cart.view.*

class CartViewAdapter (val mContext: Context, private val cartList: List<Product>?,private val cartListener: CartListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mRecyclerViewHolder: RecyclerView.ViewHolder? = null

    class CartViewHolder(viewGroup: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_cart, viewGroup, false)
    ) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mRecyclerViewHolder=CartViewHolder(parent)

        return mRecyclerViewHolder as RecyclerView.ViewHolder

    }

    override fun getItemCount(): Int {

        return cartList!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var categoryViewHolder=holder as CartViewHolder
        categoryViewHolder.itemView.product.text=cartList?.get(position)?.product_name
        categoryViewHolder.itemView.desc.text=cartList?.get(position)?.product_desc
        categoryViewHolder.itemView.price.text=cartList?.get(position)?.price
        categoryViewHolder.itemView.qty.text=cartList?.get(position)?.qty
        getTotalPriceItem(categoryViewHolder.itemView.total,cartList?.get(position)?.qty.toString(),cartList?.get(position)?.price!!)

        categoryViewHolder.itemView.plus.setOnClickListener(View.OnClickListener {
            var qty=categoryViewHolder.itemView.qty.text.toString()
            if(qty.toInt()<=10){
                var totalQty=qty.toInt()+1
                cartListener.addQtyToCart(cartList?.get(position),totalQty)
            }else
            {
                Toast.makeText(mContext,"Maximum 10 number of qty added, you can order next time",Toast.LENGTH_SHORT).show()
            }

        })

        categoryViewHolder.itemView.minus.setOnClickListener(View.OnClickListener {
            var qty=categoryViewHolder.itemView.qty.text.toString()
            if(qty.toInt()>1){
                var totalQty=qty.toInt()-1
                cartListener.removeQtyFromCart(cartList?.get(position),totalQty)
            }else
            {
               Toast.makeText(mContext,"You can't able to remove the quantity, click on remove button to delete this item",Toast.LENGTH_SHORT).show()
            }

        })

        categoryViewHolder.itemView.remove.setOnClickListener(View.OnClickListener {
            cartListener.removeFromCart(cartList?.get(position))

        })
    }

    fun getTotalPriceItem(totalView:CustomTextView,qty:String,price:String)
    {
        var doubleValue=price.toDouble()*qty.toInt()
        totalView.text=String.format(doubleValue.toString())

    }


}
