package com.justforfun.bottlerocket

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_store_layout.view.*

class StoresAdapter (private val stores: MutableList<Store>) : RecyclerView.Adapter<StoreViewHolder>(){


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): StoreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return StoreViewHolder(inflater, parent)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: StoreViewHolder, position: Int) {
        val store: Store = stores[position]
        holder.bind(store)
        GlideApp.with(holder.itemView).load(store.storeLogoURL).into(holder.itemView.logo_image_view)
    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = stores.size
}