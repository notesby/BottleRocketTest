package com.justforfun.bottlerocket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.item_store_layout.*
import kotlinx.android.synthetic.main.item_store_layout.view.*

class DetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailsViewModel
    var nameTextView: TextView? = null
    var addressTextView : TextView? = null
    var phoneTextView: TextView? = null
    var store: Store? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        nameTextView = findViewById<TextView>(R.id.name_text_view)
        addressTextView = findViewById<TextView>(R.id.address_text_view)
        phoneTextView = findViewById<TextView>(R.id.phone_text_view)

        if (intent.hasExtra("storeID")) {
            val storeid: Long = intent.getLongExtra("storeID", 0)
            viewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)

            viewModel.fetchStore(this,storeid)

            viewModel.storeLiveData.observe(this, Observer {
                this.store = it
                GlideApp.with(this).load(it.storeLogoURL).into(details_logo_imageview)
                nameTextView!!.text = it.name
                addressTextView!!.text = "Address: "+ it.address + ", Zip Code " + it.zipcode + ", " + it.city + ", " + it.state
                phoneTextView!!.text = "Phone: "+it.phone
            })
        }

    }


    fun share(view: View?){

        if (store != null) {
            val shareTxt: String = store!!.name + " Phone: " + store!!.phone +
                    " Address " + store!!.address + " Zip Code "+ store!!.zipcode +" City " +
                    store!!.city + " State " + store!!.state
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareTxt)
            startActivity(Intent.createChooser(shareIntent, "Share to"))
        }
    }
}
