package com.virus.covid19.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.virus.covid19.R
import com.virus.covid19.database.entities.Product
import com.virus.covid19.interfaces.addCartListener
import kotlinx.android.synthetic.main.item_product.view.*
import java.util.*
import kotlin.collections.ArrayList

class ShopListItemAdapter (private val shopList:ArrayList<Product>,private val cartListener: addCartListener): RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable{
    private var mRecyclerViewHolder: RecyclerView.ViewHolder? = null
    var FilterList:List<Product> = ArrayList<Product>()

    class ShopListItemViewHolder(viewGroup: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_product, viewGroup, false)
    ) {

    }

    init {
        FilterList = shopList
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mRecyclerViewHolder= ShopListItemViewHolder(parent)

        return mRecyclerViewHolder as RecyclerView.ViewHolder
    }

    override fun getItemCount(): Int {

        return FilterList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder=holder as ShopListItemViewHolder
        viewHolder.itemView.product.text=FilterList.get(position).product_name
        viewHolder.itemView.offerprice.text=FilterList.get(position).price
        viewHolder.itemView.desc.text=FilterList.get(position).product_desc
        if(FilterList.get(position).product_type.equals("Saloon",ignoreCase = true) || FilterList.get(position).product_type.equals("Physiotheraphy",ignoreCase = true)){
            viewHolder.itemView.addtocart.text="Book Now"

        }else
        {
            viewHolder.itemView.addtocart.text="Add To Cart"

        }

        viewHolder.itemView.addtocart.setOnClickListener(View.OnClickListener {
            if(FilterList.get(position).product_type.equals("Saloon",ignoreCase = true) || FilterList.get(position).product_type.equals("Physiotheraphy",ignoreCase = true)){
                cartListener.showSaloonOrPhysioDialog()
            }else{
                cartListener.addToCart(FilterList.get(position))
            }

        })

    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    FilterList = shopList
                } else {
                    val resultList:ArrayList<Product> = ArrayList<Product>()
                    for (row in shopList) {
                        if (row.product_name?.toLowerCase(Locale.ROOT)!!.contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    FilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = FilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                FilterList = results?.values as ArrayList<Product>
                notifyDataSetChanged()
            }

        }
    }
}