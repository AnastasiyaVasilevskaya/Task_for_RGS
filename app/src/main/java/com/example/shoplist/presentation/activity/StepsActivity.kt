package com.example.shoplist.presentation.activity

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.databinding.ActivityStepsBinding
import com.example.shoplist.domain.StepItem
import com.example.shoplist.presentation.adapter.ListAdapter.Companion.MAX_PULL_SIZE
import com.example.shoplist.presentation.adapter.ListAdapter.Companion.VIEW_TYPE_DISABLED
import com.example.shoplist.presentation.adapter.ListAdapter.Companion.VIEW_TYPE_ENABLED
import com.example.shoplist.presentation.adapter.StepsListAdapter
import com.example.shoplist.presentation.viewmodels.StepsViewModel

class StepsActivity : ComponentActivity() {

    private lateinit var binding: ActivityStepsBinding
    private val viewModel: StepsViewModel by viewModels()
    private lateinit var adapter: StepsListAdapter
    private lateinit var rvList: RecyclerView
    var onResetButtonClickListener: ((List<StepItem>) -> Unit)? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityStepsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupStepsRVList()
        setupResetButtonClickListener()
        initViewsListeners()
    }

    private fun setupStepsRVList() {
        rvList = binding.rvAlgorithmList
        adapter = StepsListAdapter()
        viewModel.getStepsLD().observe(this) { steps ->
            val stepsListDB = steps.map { stepDB ->
                StepItem(stepDB.id, stepDB.name, stepDB.enabled)
            }
            setupLongClickListener()

            rvList.adapter = adapter
            adapter.stepsList = stepsListDB
            with(rvList) {
                Log.d(TAG, "setupRVList")
                recycledViewPool.setMaxRecycledViews(VIEW_TYPE_ENABLED, MAX_PULL_SIZE)
                recycledViewPool.setMaxRecycledViews(VIEW_TYPE_DISABLED, MAX_PULL_SIZE)
            }
        }
    }

    private fun setupLongClickListener() {
        adapter.onListItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }

    private fun setupResetButtonClickListener() {
        onResetButtonClickListener = {
            viewModel.resetToEnabledItems(it)
        }
        binding.icResetButton.setOnClickListener {
            onResetButtonClickListener?.invoke(adapter.stepsList)
        }
    }


    private fun initViewsListeners() {
        binding.icBackButton.setOnClickListener() {
            finish()
        }
    }
}
