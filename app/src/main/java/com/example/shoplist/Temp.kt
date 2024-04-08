package com.example.shoplist

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.shoplist.data.database.AppDatabase
import com.example.shoplist.data.database.entity.ItemDBModel
import com.example.shoplist.data.database.entity.StepsDB

class Temp : AppCompatActivity(R.layout.activity_temp) {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val db = AppDatabase.getInstance(App.instance)
        val dao = db.stepsDao()



        val nameEditText: EditText = findViewById(R.id.name)
        val button: Button = findViewById(R.id.save)
        val text: TextView = findViewById(R.id.text)

        button.setOnClickListener {
            val name = nameEditText.text.toString()
//            dao.addName(StepsDB(
//                name = name,
//                enabled = true
//            ))
            nameEditText.setText("")
            text.text = dao.fetchAllSteps().toString()

        }
    }
}

