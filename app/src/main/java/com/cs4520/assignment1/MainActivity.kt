package com.cs4520.assignment1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cs4520.assignment1.database.DatabaseProvider

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DatabaseProvider.setContext(this)
        setContentView(R.layout.main_activity)
    }
}