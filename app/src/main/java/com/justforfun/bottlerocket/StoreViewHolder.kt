package com.justforfun.bottlerocket

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.nav_header_main.view.*

class StoreViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_store_layout, parent, false)), View.OnClickListener {

    private var mNameView: TextView? = null
    private var mPhoneView: TextView? = null
    private var mLogoView: ImageView? = null
    private var store:Store? = null

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val context = itemView.context
        val showDetailsActivity = Intent(context, DetailsActivity::class.java)
        showDetailsActivity.putExtra("storeID", store!!.storeID)
        context.startActivity(showDetailsActivity)
    }

    init {
        mNameView = itemView.findViewById(R.id.name_text_view)
        mPhoneView = itemView.findViewById(R.id.phone_text_view)
        mLogoView = itemView.findViewById(R.id.logo_image_view)
    }

    fun bind(store: Store) {
        mNameView?.text = store.name
        mPhoneView?.text = store.phone
        this.store = store
    }

}