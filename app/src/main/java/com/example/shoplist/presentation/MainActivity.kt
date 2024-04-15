package com.example.shoplist.presentation

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.databinding.ActivityMainBinding
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
    }

    private fun setupRVList() {
        val rvList = findViewById<RecyclerView>(R.id.rv_shop_list)
        adapter = ListAdapter()
        rvList.adapter = adapter
        with(rvList) {
            Log.d(TAG, "setupRVList")
            recycledViewPool.setMaxRecycledViews(VIEW_TYPE_ENABLED, MAX_PULL_SIZE)

        }
        setupClickListener()
    }

    private fun setupClickListener() {
        adapter.onListItemClickListener = { listItem ->
            val intent = Intent(this, StepsActivity::class.java)
            intent.putExtra("itemId", listItem.id)
            startActivity(intent)
        }
    }
}

