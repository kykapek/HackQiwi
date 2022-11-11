package ru.teamview.hackqiwi.ui.connection

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.teamview.hackqiwi.R

@AndroidEntryPoint
class ConnectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_connection)
    }

    override fun onBackPressed() {

    }
}