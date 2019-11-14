package com.justforfun.bottlerocket

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.store_list_fragment.*
import android.content.Context.MODE_PRIVATE




class StoreListFragment : Fragment() {


    companion object {
        fun newInstance() = StoreListFragment()
    }

    private lateinit var viewModel: StoreListViewModel
    var stores: MutableList<Store> = mutableListOf()
    var storesAdapter: StoresAdapter = StoresAdapter(stores)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.store_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stores_recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = storesAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(StoreListViewModel::class.java)

        viewModel.fetchStores(context!!,false)


        // TODO: Use the ViewModel

        viewModel.storesLiveData.observe(this, Observer {
            stores.clear()
            stores.addAll(it)
            storesAdapter.notifyDataSetChanged()
        })
    }


    fun manualRefresh(){
        if (viewModel != null) {
            viewModel.fetchStores(context!!,true)
        }
    }

}
