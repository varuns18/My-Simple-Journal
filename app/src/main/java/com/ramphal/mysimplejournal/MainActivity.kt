package com.ramphal.mysimplejournal

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ramphal.mysimplejournal.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding?.main)
        ViewCompat.setOnApplyWindowInsetsListener(binding?.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val adapter = MainAdapter(dailyJournalList = Constant.demoJournalList)
        binding?.recyclerView?.adapter = adapter
        binding?.appBarLayout?.setBackgroundColor(ContextCompat.getColor(this, R.color.surface))
        binding?.btnNewJournal?.setOnClickListener {
            val intent = Intent(this, NewJournalActivity::class.java)
            startActivity(intent)
        }
        val window = window
        window.statusBarColor = ContextCompat.getColor(this, R.color.surfaceContainer)

        binding?.appBar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_sync -> {
                    val intent = Intent(this, BackupActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}