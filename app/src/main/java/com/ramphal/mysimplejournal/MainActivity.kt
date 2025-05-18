package com.ramphal.mysimplejournal

import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.ramphal.mysimplejournal.data.DailyJournalModel
import com.ramphal.mysimplejournal.databinding.ActivityMainBinding
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding?.main)
        val journalDao = (application as MyJournalApp).db.dailyJournalDao()
        ViewCompat.setOnApplyWindowInsetsListener(binding?.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lifecycleScope.launch {
            journalDao.getAll().collect{
                val list = ArrayList(it)
                setUpDataToRecyclerview(list)
            }
        }

        binding?.appBarLayout?.setBackgroundColor(ContextCompat.getColor(this, R.color.surface))
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

        binding?.btnNewJournal?.setOnClickListener {
            val intent = Intent(this, NewJournalActivity::class.java)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this, binding!!.btnNewJournal, "shared_element_to_compose"
            )
            startActivity(intent, options.toBundle())
        }

    }

    private fun setUpDataToRecyclerview(journalList: ArrayList<DailyJournalModel>){

        if (journalList.isNotEmpty()){
            val adapter = MainAdapter(dailyJournalList = journalList)
            binding?.recyclerView?.adapter = adapter
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}