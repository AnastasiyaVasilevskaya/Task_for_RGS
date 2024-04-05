package com.example.shoplist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.shoplist.databinding.ActivityStepsBinding

class StepsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStepsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityStepsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}