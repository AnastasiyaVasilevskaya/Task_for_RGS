package com.example.shoplist.presentation

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist.databinding.ActivityStepsBinding
import com.example.shoplist.presentation.ListAdapter.Companion.MAX_PULL_SIZE
import com.example.shoplist.presentation.ListAdapter.Companion.VIEW_TYPE_DISABLED
import com.example.shoplist.presentation.ListAdapter.Companion.VIEW_TYPE_ENABLED

class StepsActivity : ComponentActivity() {

    private lateinit var binding: ActivityStepsBinding
    private lateinit var viewModel: StepsViewModel
    private lateinit var adapter: StepsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityStepsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupDetailsRVList()
        viewModel = ViewModelProvider(this)[StepsViewModel::class.java]
        viewModel.stepsListLD.observe(this) {
            adapter.stepsList = it
        }

        binding.icBackButton.setOnClickListener(){
            finish()
        }
    }

    private fun setupDetailsRVList() {
        val rvList = binding.rvAlgorithmList
        val stepsList = StepsDataManager.getStepsList()
        adapter = StepsListAdapter()
        rvList.adapter = adapter
        adapter.setupStepsList(stepsList)
        with(rvList) {
            Log.d(TAG, "setupRVList")
            recycledViewPool.setMaxRecycledViews(VIEW_TYPE_ENABLED, MAX_PULL_SIZE)
            recycledViewPool.setMaxRecycledViews(VIEW_TYPE_DISABLED, MAX_PULL_SIZE)
        }
        setupLongClickListener()
    }

    private fun setupLongClickListener() {
        adapter.onListItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }
}