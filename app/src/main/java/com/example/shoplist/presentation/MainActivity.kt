package com.example.shoplist.presentation

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.databinding.ActivityMainBinding
import com.example.shoplist.presentation.ItemActivity.Companion.newIntentAdd
import com.example.shoplist.presentation.ItemActivity.Companion.newIntentDetails
import com.example.shoplist.presentation.ItemActivity.Companion.newIntentEdit
import com.example.shoplist.presentation.ListAdapter.Companion.MAX_PULL_SIZE
import com.example.shoplist.presentation.ListAdapter.Companion.VIEW_TYPE_DISABLED
import com.example.shoplist.presentation.ListAdapter.Companion.VIEW_TYPE_ENABLED

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ListAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRVList()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.listLD.observe(this) {
            adapter.list = it
        }
        binding.bttnAddShopItem.setOnClickListener {
            val intent = newIntentAdd(this)
            startActivity(intent)
        }
    }

    fun setupRVList() {
        val rvList = findViewById<RecyclerView>(R.id.rv_shop_list)
        adapter = ListAdapter()
        rvList.adapter = adapter
        with(rvList) {
            Log.d(TAG, "setupRVList")
            recycledViewPool.setMaxRecycledViews(VIEW_TYPE_ENABLED, MAX_PULL_SIZE)
            recycledViewPool.setMaxRecycledViews(VIEW_TYPE_DISABLED, MAX_PULL_SIZE)
        }
        setupSwipeListener(rvList)
        setupLongClickListener()
        setupClickListener()
        //setupEditClickListener()
    }

    private fun setupSwipeListener(rvList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.list[viewHolder.adapterPosition]
                viewModel.deleteItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvList)
    }

    private fun setupEditClickListener() {
        adapter.onListItemClickListener = {
            Log.d(TAG, "it info id: ${it.id} status: ${it.enabled} ")
            val intent = newIntentEdit(this, it.id)
            startActivity(intent)
        }
    }

    private fun setupClickListener() {
        adapter.onListItemClickListener = {
            val intent = newIntentDetails(this, it.id)
            startActivity(intent)
        }
    }

    private fun setupLongClickListener() {
        adapter.onListItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }

}

