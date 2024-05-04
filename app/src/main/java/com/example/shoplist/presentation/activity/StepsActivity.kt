package com.example.shoplist.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.databinding.ActivityStepsBinding
import com.example.shoplist.domain.StepItem
import com.example.shoplist.presentation.adapter.StepsListAdapter
import com.example.shoplist.presentation.viewmodels.StepsViewModel

class StepsActivity : ComponentActivity() {

    private lateinit var binding: ActivityStepsBinding
    private val viewModel: StepsViewModel by viewModels()
    private lateinit var adapter: StepsListAdapter
    private lateinit var rvList: RecyclerView

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
        rvList.itemAnimator = null
        rvList.adapter = adapter
        viewModel.stepsListLD.observe(this) { steps ->
            val stepsListDB = steps.map { stepDB ->
                StepItem(stepDB.id, stepDB.name, stepDB.enabled)
            }
            setupLongClickListener()

            adapter.submitList(stepsListDB)
        }
    }

    private fun setupLongClickListener() {
        adapter.onListItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }

    private fun setupResetButtonClickListener() {
        binding.icResetButton.setOnClickListener {
            viewModel.resetToEnabledItems()
        }
    }


    private fun initViewsListeners() {
        binding.icBackButton.setOnClickListener() {
            finish()
        }
    }
}
