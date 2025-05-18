package com.ramphal.mysimplejournal

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.net.toUri
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.ramphal.mysimplejournal.data.DailyJournalModel
import com.ramphal.mysimplejournal.databinding.RecyclerviewItemBinding

class MainAdapter(private val dailyJournalList: ArrayList<DailyJournalModel>): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
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
                if (dailyJournalModel.image.isNullOrEmpty() == true){
                    itemBinding.imageCard.visibility = View.GONE
                } else {
                    itemBinding.imageCard.visibility = View.VISIBLE
                    itemBinding.ivImage.setImageURI(dailyJournalModel.image.toUri())
                }
                itemBinding.mainCard.setOnClickListener { view ->
                    val ctx = view.context as AppCompatActivity
                    val intent = Intent(ctx, JournalDetailActivity::class.java).apply {
                        putExtra("JOURNAL_ID", dailyJournalModel.id)
                    }
//                    val titlePair: Pair<View, String> = Pair(itemBinding.tvTitle, "Shared_transition_title")
//                    val contentPair: Pair<View, String> = Pair(itemBinding.tvContent, "Shared_transition_content")
//                    val datePair: Pair<View, String> = Pair(itemBinding.tvDate, "Shared_transition_date")
//                    val cardPair: Pair<View, String> = Pair(itemBinding.mainCard, "Shared_transition_detail")
//                    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        ctx, cardPair,datePair, titlePair, contentPair
//                    ).toBundle()
                    ctx.startActivity(intent)
                }

            }
    }

}