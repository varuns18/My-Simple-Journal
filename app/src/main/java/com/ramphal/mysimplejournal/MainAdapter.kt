package com.ramphal.mysimplejournal

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.ramphal.mysimplejournal.databinding.RecyclerviewItemBinding

class MainAdapter(private val dailyJournalList: List<DailyJournalModel>): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolder {
        return MainViewHolder(RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(
        holder: MainViewHolder,
        position: Int
    ) {
        val dailyJournalModel = dailyJournalList[position]
        holder.bindItem(dailyJournalModel)
        when{

        }
    }

    override fun getItemCount(): Int {
        return dailyJournalList.size
    }

    inner class MainViewHolder(private val itemBinding: RecyclerviewItemBinding)
        :RecyclerView.ViewHolder(itemBinding.root) {
            fun bindItem(dailyJournalModel: DailyJournalModel) {
                itemBinding.tvTitle.text = dailyJournalModel.title
                itemBinding.tvDate.text = dailyJournalModel.date
                itemBinding.tvContent.text = dailyJournalModel.content
                itemBinding.mainCard.setCardBackgroundColor(itemBinding.mainCard.context.getColor(
                    Constant.getCustomColorList()[dailyJournalModel.color]
                ))
                dailyJournalModel.image?.let {
                    itemBinding.imageCard.visibility = View.VISIBLE
                    itemBinding.ivImage.setImageResource(it)
                }
                itemBinding.mainCard.setOnClickListener {
                    val ctx = it.context
                    val intent = Intent(ctx, NewJournalActivity::class.java)
                    ctx.startActivity(intent)
                }
            }
    }

}