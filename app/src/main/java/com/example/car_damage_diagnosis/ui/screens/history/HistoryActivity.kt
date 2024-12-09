package com.example.car_damage_diagnosis.ui.screens.history

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.car_damage_diagnosis.R


class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        // Настройка RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        if (recyclerView != null) {
            recyclerView.layoutManager = GridLayoutManager(this, 2) // Сетка из 2 колонок
            recyclerView.adapter = HistoryAdapter {
                // Обработчик нажатия на карточку
                val intent = Intent(this, DetailActivity::class.java)
                startActivity(intent)
            }
        } else {
            Log.e("HistoryActivity", "RecyclerView не найден!")
        }

        // Кнопка добавления
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab?.setOnClickListener {
            // Логика добавления нового элемента
            Log.d("HistoryActivity", "FAB clicked!")
        }
    }
}