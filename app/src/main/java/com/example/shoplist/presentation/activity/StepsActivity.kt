package com.example.shoplist.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.databinding.ActivityStepsBinding
import com.example.shoplist.domain.StepItem
import com.example.shoplist.presentation.adapter.StepsListAdapter
import com.example.shoplist.presentation.viewmodels.StepsViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class StepsActivity : ComponentActivity() {

    private lateinit var binding: ActivityStepsBinding
    private val viewModel: StepsViewModel by viewModels()
    private lateinit var adapter: StepsListAdapter
    private lateinit var rvList: RecyclerView
    private lateinit var firebaseRef : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityStepsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupStepsRVList()
        setupFbDB()

        rvList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this.context)
        }

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

    private fun setupFbDB() {
        firebaseRef = FirebaseDatabase.getInstance().getReference("1_1_steps")
        firebaseRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val stepsListFB = mutableListOf<StepItem>()
                if(snapshot.exists()) {
                    snapshot.children.forEach { stepSnap ->
                        val step = stepSnap.getValue(StepItem::class.java)
                        step?.let {
                            stepsListFB.add(it)
                        }
                    }
                    adapter.submitList(stepsListFB)
                }
            }
            override fun onCancelled(error: DatabaseError) {            }
        })
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
