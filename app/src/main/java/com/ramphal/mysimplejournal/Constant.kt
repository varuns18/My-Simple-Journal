package com.ramphal.mysimplejournal

import com.ramphal.mysimplejournal.data.DailyJournalModel

object Constant {

    val tableName = "daily_journal_table"

    private val listOfJournalTitles = listOf(
        "Today’s Highlights and What They Meant to Me",
        "Moments That Made Me Smile and Why They Mattered",
        "Challenges I Faced Today and How I Handled Them",
        "Conversations That Stood Out and Their Impact on Me",
        "Tasks I Accomplished and How They Made Me Feel",
        "Things I Learned Today and Their Significance",
        "Acts of Kindness I Gave or Received Today",
        "Decisions I Made and Their Outcomes",
        "Times I Felt Stressed and How I Coped",
        "New Ideas or Thoughts That Came to Me Today",
        "Things I Noticed About Myself Today",
        "Moments of Peace and What Brought Them",
        "Interactions That Taught Me Something New",
        "Steps I Took Toward My Goals Today",
        "Things I Found Difficult and What I Learned",
        "Ways I Took Care of Myself Today",
        "Moments When I Felt Proud and Why",
        "Times I Felt Connected to Others Today",
        "Things I Want to Remember About Today",
        "Reflections on How Today Changed Me"
    )

    private val customColor = listOf(
        R.color.cream,
        R.color.peach_warm,
        R.color.mint_ice,
        R.color.lavender_white,
        R.color.sky_pale,
        R.color.pale_yellow,
        R.color.aqua_mist,
        R.color.pink_blush,
        R.color.tea_green,
        R.color.vanilla_cream,
        R.color.pale_sky,
        R.color.pearl_white,
        R.color.frosty_turquoise,
        R.color.cotton_candy,
        R.color.pale_lime,
        R.color.mauve_whisper,
        R.color.cloud_blue,
        R.color.garden_mist,
        R.color.lilac_milk,
        R.color.aloe_mist
    )


    fun getJournalTitlesList(): List<String> {
        return listOfJournalTitles
    }

    fun getCustomColorList(): List<Int> {
        return customColor
    }

}