package com.ramphal.mysimplejournal

object Constant {

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

    val demoJournalList = listOf<DailyJournalModel>(
        DailyJournalModel(
            id = 1,
            title = "Welcoming the New Year with a Positive Mindset",
            date = "2025-01-01",
            content = "Today marks the beginning of a new year, and I’ve decided to focus on growth, learning, and staying consistent in my goals.",
            image = R.drawable.demo,
            color = 1
        ),
        DailyJournalModel(
            id = 2,
            title = "Peaceful Morning Walk Through the Neighborhood",
            date = "2025-01-02",
            content = "I enjoyed a calm morning walk today. The cool breeze and chirping birds made it a refreshing start to my day.",
            image = R.drawable.demo,
            color = 14
        ),
        DailyJournalModel(
            id = 3,
            title = "Highly Productive Day at Work with Major Progress",
            date = "2025-01-03",
            content = "Finished all tasks on time and made significant progress on my project. Productivity was at its peak and felt truly rewarding.",
            image = R.drawable.demo,
            color = 7
        ),
        DailyJournalModel(
            id = 4,
            title = "Tried a New Coffee Shop and Loved the Ambience",
            date = "2025-01-04",
            content = "Discovered a new café nearby. The cappuccino was delightful and the interior design gave a very cozy and relaxing vibe.",
            image = null,
            color = 18
        ),
        DailyJournalModel(
            id = 5,
            title = "Quality Time Spent with Family Over Games and Talks",
            date = "2025-01-05",
            content = "Played card games and laughed endlessly with my family. These moments are truly priceless and create lasting memories.",
            image = R.drawable.demo,
            color = 3
        ),
        DailyJournalModel(
            id = 6,
            title = "Enjoyed the Rain While Listening to Soothing Music",
            date = "2025-01-06",
            content = "It rained all afternoon, and I sat by the window listening to calming instrumental music. It helped me feel relaxed and thoughtful.",
            image = R.drawable.demo,
            color = 11
        ),
        DailyJournalModel(
            id = 7,
            title = "Started Reading a New Mystery Novel by a Famous Author",
            date = "2025-01-07",
            content = "Began a thrilling new book today. The first few chapters are already intriguing, and I can’t wait to uncover the mystery.",
            image = null,
            color = 5
        ),
        DailyJournalModel(
            id = 8,
            title = "Improving My Android App with UI Design Updates",
            date = "2025-01-08",
            content = "Spent the entire day refining layouts, icons, and themes in my app. The interface looks cleaner and more user-friendly now.",
            image = null,
            color = 16
        ),
        DailyJournalModel(
            id = 9,
            title = "Energizing Workout Session with Strength Training Focus",
            date = "2025-01-09",
            content = "Went to the gym early in the morning. Focused on strength training, and I’m already seeing some improvement in my routine.",
            image = R.drawable.demo,
            color = 2
        ),
        DailyJournalModel(
            id = 10,
            title = "Exciting Movie Night with Friends and Great Snacks",
            date = "2025-01-10",
            content = "Watched an intense action movie with friends and had popcorn, nachos, and soda. It was a fun night with lots of laughter.",
            image = R.drawable.demo,
            color = 1
        ),
        DailyJournalModel(
            id = 11,
            title = "Relaxing and Reflective Sunday with No Distractions",
            date = "2025-01-11",
            content = "Did absolutely nothing today, and it felt amazing. I took time to think, rest, and recharge for the week ahead.",
            image = R.drawable.demo,
            color = 9
        )
    )



    fun getJournalTitlesList(): List<String> {
        return listOfJournalTitles
    }

    fun getCustomColorList(): List<Int> {
        return customColor
    }

}