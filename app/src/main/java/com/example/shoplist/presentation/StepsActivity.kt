package com.example.shoplist.presentation

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.data.database.AppDatabase
import com.example.shoplist.data.database.entity.StepsDBModel
import com.example.shoplist.databinding.ActivityStepsBinding
import com.example.shoplist.domain.StepItem
import com.example.shoplist.presentation.ListAdapter.Companion.MAX_PULL_SIZE
import com.example.shoplist.presentation.ListAdapter.Companion.VIEW_TYPE_DISABLED
import com.example.shoplist.presentation.ListAdapter.Companion.VIEW_TYPE_ENABLED

class StepsActivity : ComponentActivity() {

    private lateinit var binding: ActivityStepsBinding
    private lateinit var viewModel: StepsViewModel
    private lateinit var adapter: StepsListAdapter
    private lateinit var rvList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityStepsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupStepsRVList()

        viewModel = ViewModelProvider(this)[StepsViewModel::class.java]
        viewModel.stepsListLD.observe(this) { steps ->
            if (!::adapter.isInitialized) { // Проверка на инициализацию адаптера
                adapter = StepsListAdapter()
                rvList.adapter = adapter
            }
            adapter.stepsList = steps
        }

        resetStepsToEnabled()
        finishActivity()
    }

    override fun onResume() {
        super.onResume()
        setupStepsRVList()
    }

    private fun setupStepsRVList() {
        rvList = binding.rvAlgorithmList
        val db = AppDatabase.getInstance(application)
        val stepsLD: LiveData<List<StepsDBModel>> = db.stepsDao().fetchAllSteps()
        stepsLD.observe(this) { steps ->
            val stepsList = steps.map { stepDB ->
                StepItem(stepDB.id, stepDB.name, stepDB.enabled)
            }

            adapter = StepsListAdapter()
            rvList.adapter = adapter
            adapter.updateList(stepsList)
            with(rvList) {
                Log.d(TAG, "setupRVList")
                recycledViewPool.setMaxRecycledViews(VIEW_TYPE_ENABLED, MAX_PULL_SIZE)
                recycledViewPool.setMaxRecycledViews(VIEW_TYPE_DISABLED, MAX_PULL_SIZE)
            }

            setupLongClickListener()
        }
    }

    private fun setupLongClickListener() {
        adapter.onListItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }

    private fun finishActivity() {
        binding.icBackButton.setOnClickListener() {
            finish()
        }
    }

    private fun resetStepsToEnabled() {
        binding.icResetButton.setOnClickListener {
            adapter.resetToEnabledItems()
        }
    }
}