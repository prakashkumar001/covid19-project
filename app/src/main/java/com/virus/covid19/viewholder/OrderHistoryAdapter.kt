package com.virus.covid19.viewholder

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.virus.covid19.R
import com.virus.covid19.database.entities.OrderHistory
import kotlinx.android.synthetic.main.drawer_item.view.title
import kotlinx.android.synthetic.main.item_order_history.view.*
import org.jetbrains.anko.textColor

class OrderHistoryAdapter(private val orderHistoryList:List<OrderHistory>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mRecyclerViewHolder: RecyclerView.ViewHolder? = null
    private var mContext:Context?=null
    class OrderHistoryViewHolder(viewGroup: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_order_history, viewGroup, false)
    ) {
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mRecyclerViewHolder= OrderHistoryViewHolder(parent)
        mContext=parent.context

        return mRecyclerViewHolder as RecyclerView.ViewHolder
    }

    override fun getItemCount(): Int {

        return orderHistoryList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var viewHolder=holder as OrderHistoryViewHolder
        viewHolder.itemView.title.text=orderHistoryList.get(position).shop_name
        viewHolder.itemView.amt.text=orderHistoryList.get(position).totalAmount
        if(orderHistoryList.get(position).status.equals("success",ignoreCase = true)){
            viewHolder.itemView.amt.textColor= Color.GREEN
        }else{
            viewHolder.itemView.amt.textColor= Color.RED
        }



    }
}