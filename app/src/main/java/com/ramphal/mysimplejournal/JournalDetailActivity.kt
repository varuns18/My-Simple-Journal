package com.ramphal.mysimplejournal

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Delete
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ramphal.mysimplejournal.data.DailyJournalDao
import com.ramphal.mysimplejournal.data.DailyJournalModel
import com.ramphal.mysimplejournal.databinding.ActivityJournalDetailBinding
import kotlinx.coroutines.launch

class JournalDetailActivity : AppCompatActivity() {

    private var binding: ActivityJournalDetailBinding? = null
    private var dailyJournalData: DailyJournalModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJournalDetailBinding.inflate(layoutInflater)
        val journalDao = (application as MyJournalApp).db.dailyJournalDao()
        enableEdgeToEdge()
        setContentView(binding?.main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val myColor = Constant.getCustomColorList()
        val journalId = intent.getIntExtra("JOURNAL_ID", -1)
        lifecycleScope.launch {
            journalDao.getById(journalId).collect{
                it?.let {
                    dailyJournalData = it
                    binding?.tvTitle?.text = it.title
                    binding?.tvContent?.text = it.content
                    binding?.tvDate?.text = it.date
                    binding?.main?.setBackgroundColor(getColor(myColor[it.color]))
                    binding?.appBarLayout?.setBackgroundColor(getColor(myColor[it.color]))
                    binding?.main?.statusBarBackground = ContextCompat.getDrawable(this@JournalDetailActivity, myColor[it.color])
                    it.image?.let { it1 ->
                        binding?.ivImage?.setImageURI(it1.toUri())
                        binding?.imageCard?.visibility = View.VISIBLE
                    }
                }
            }
        }

        val deleteDialog: MaterialAlertDialogBuilder = MaterialAlertDialogBuilder(this)
            .setTitle("Delete permanently?")
            .setMessage("This action cannot be undone. Are you sure you want to delete this journal entry?")

        binding?.appBar?.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.action_edit -> {
                    val intent = Intent(this@JournalDetailActivity, NewJournalActivity::class.java).apply {
                        putExtra("id", journalId)
                    }
                    startActivity(intent)
                    true
                }
                R.id.action_delete -> {
                    deleteDialog
                        .setNegativeButton("Cancel") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .setPositiveButton("Delete") { dialog, _ ->
                            dialog.dismiss()
                            lifecycleScope.launch {
                                journalDao.delete(dailyJournalData!!)
                                finish()
                            }
                        }
                        .show()
                    true
                }
                else -> false
            }
        }

    }
}
