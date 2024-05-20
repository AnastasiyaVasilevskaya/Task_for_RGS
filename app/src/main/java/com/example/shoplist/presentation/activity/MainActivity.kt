package com.example.shoplist.presentation.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.databinding.ActivityMainBinding
import com.example.shoplist.domain.StepItem
import com.example.shoplist.presentation.adapter.PlansAdapter
import com.example.shoplist.presentation.viewmodels.MainViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: PlansAdapter
    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRVList()
        viewModel.listLD.observe(this) {
            adapter.plansList = it
        }

    }

    private fun setupRVList() {
        val rvList = findViewById<RecyclerView>(R.id.rv_shop_list)
        adapter = PlansAdapter()
        rvList.adapter = adapter
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

