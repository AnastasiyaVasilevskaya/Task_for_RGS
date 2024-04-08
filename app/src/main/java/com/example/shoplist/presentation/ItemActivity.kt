package com.example.shoplist.presentation

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist.R
import com.example.shoplist.databinding.ActivityShopItemBinding
import com.example.shoplist.domain.ListItem

class ItemActivity : ComponentActivity() {
    private lateinit var binding: ActivityShopItemBinding
    private lateinit var viewModel: ItemViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parseIntent()
        viewModel = ViewModelProvider(this)[ItemViewModel::class.java]
        addTextChangeListeners()
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }

        viewModel.errorInputName.observe(this) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            binding.layoutName.error = message
        }
        viewModel.finishCurrentScreenLD.observe(this) {
            finish()
        }

        binding.backButton.setOnClickListener(){
            finish()
        }
    }

    private fun addTextChangeListeners() {
        binding.editTextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun launchEditMode() {
        viewModel.getItem(itemID)
        viewModel.itemLD.observe(this) {
            binding.editTextName.setText(it.name)
        }
        binding.buttonSave.setOnClickListener {
            viewModel.editItem(
                binding.editTextName.text?.toString()
            )
        }
    }

    private fun launchAddMode() {
        binding.buttonSave.setOnClickListener {
            viewModel.addItem(
                binding.editTextName.text?.toString()
            )
        }
    }

    private var screenMode = MODE_UNKNOWN
    private var itemID = ListItem.UNDEFINED_ID

    private fun parseIntent() { //проверка интента
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("param screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        Log.d(TAG, "mode is $mode")
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("unknown mode intent: $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_ITEM_ID)) {
                throw RuntimeException("Mode edit doesn't have extra shop item id")
            }
            itemID = intent.getIntExtra(EXTRA_ITEM_ID, ListItem.UNDEFINED_ID)
        }
    }

    companion object {
        const val EXTRA_SCREEN_MODE = "extra_mode"
        const val EXTRA_ITEM_ID = "extra_item_id"
        const val MODE_DETAILS = "mode_details"
        const val MODE_EDIT = "mode_edit"
        const val MODE_ADD = "mode_add"
        const val MODE_UNKNOWN = ""

        fun newIntentAdd(context: Context): Intent {
            val intent = Intent(context, ItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            Log.d(TAG, "intent from newIntentAdd: $intent")
            return intent
        }

        fun newIntentEdit(context: Context, itemID: Int): Intent {
            val intent = Intent(context, ItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_ITEM_ID, itemID)
            Log.d(TAG, "intent from newIntentEdit: $intent")
            Log.d(TAG, "intent from newIntentEdit (ID): $intent")
            return intent
        }

        fun newIntentDetails(context: Context, itemID: Int): Intent {
            val intent = Intent(context, StepsActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_DETAILS)
            intent.putExtra(EXTRA_ITEM_ID, itemID)
            Log.d(TAG, "intent from newIntentEdit: $intent")
            Log.d(TAG, "intent from newIntentEdit (ID): $intent")
            return intent
        }
    }
}