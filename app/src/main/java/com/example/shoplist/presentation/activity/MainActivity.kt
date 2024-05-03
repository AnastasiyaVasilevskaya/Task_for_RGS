package com.example.shoplist.presentation.activity

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.databinding.ActivityMainBinding
import com.example.shoplist.presentation.adapter.ListAdapter
import com.example.shoplist.presentation.adapter.ListAdapter.Companion.MAX_PULL_SIZE
import com.example.shoplist.presentation.adapter.ListAdapter.Companion.VIEW_TYPE_ENABLED
import com.example.shoplist.presentation.viewmodels.MainViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: ListAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRVList()
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

