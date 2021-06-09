package com.team28.thehiker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.team28.thehiker.SharedPreferenceHandler.SharedPreferenceHandler
import kotlinx.android.synthetic.main.step_count_history.*

class StepCountHistoryActivity : AppCompatActivity(){

    lateinit var sharedPreferenceHandler: SharedPreferenceHandler

    inline fun <reified T> genericType() = object: TypeToken<T>() {}.type

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.step_count_history)

        sharedPreferenceHandler = SharedPreferenceHandler()

        val item1 = ListItem("01.01.2000", "10 steps")
        val item2 = ListItem("02.01.2000", "20 steps")
        val item3 = ListItem("03.01.2000", "30 steps")
        val item4 = ListItem("04.01.2000", "40 steps")
        val item5 = ListItem("05.01.2000", "50 steps")
        val item6 = ListItem("06.01.2000", "60 steps")
        val item7 = ListItem("07.01.2000", "70 steps")
        val item8 = ListItem("08.01.2000", "80 steps")
        val item9 = ListItem("09.01.2000", "90 steps")
        val item10 = ListItem("10.01.2000", "100 steps")
        val item11 = ListItem("11.01.2000", "110 steps")
        val item12 = ListItem("12.01.2000", "120 steps")
        val item13 = ListItem("13.01.2000", "130 steps")
        val item14 = ListItem("14.01.2000", "140 steps")
        val item15 = ListItem("15.01.2000", "150 steps")
        val item16 = ListItem("16.01.2000", "160 steps")
        val item17 = ListItem("17.01.2000", "170 steps")
        val item18 = ListItem("18.01.2000", "180 steps")
        val item19 = ListItem("19.01.2000", "190 steps")
        val item20 = ListItem("20.01.2000", "200 steps")
        //val itemList = listOf(item1, item2, item3, item4, item5, item6, item7, item8, item9, item10, item11, item12, item13, item14, item15, item16, item17, item18, item19, item20)

        val gson = Gson()
        var step_history_json = sharedPreferenceHandler.getStepCountHistory(this)
        var step_history = mutableListOf<String>()

        if(step_history_json != "")
        {
            val type = genericType<MutableList<String>>()
            step_history = gson.fromJson(step_history_json, type)
        }

        var item_list = mutableListOf<ListItem>()

        step_history.forEach()
        {
            item_list.add(ListItem(it.split("_")[0], it.split("_")[1]))
        }

        val adapter = ItemAdapter(this, item_list)
        step_list.adapter = adapter
    }
}