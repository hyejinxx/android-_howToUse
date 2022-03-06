package com.example.mypetdiary

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import java.util.*
//
//import com.example.mypetdiary.fragment.MainPost
//import com.example.mypetdiary.fragment.Diary

class MainActivity : AppCompatActivity() {


    var date = arrayOf<Int>(0,0,0)
    var dateString : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.mainfragment, MainPost())
        transaction.commit()

        var showCalender : ImageButton = findViewById(R.id.calender_btn)
        showCalender.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener{ _, year, month, dayOfMonth
                -> dateString = "${year}년 ${month+1}월 ${dayOfMonth}일"
                date[0] = cal.get(Calendar.YEAR)
                date[1] = cal.get(Calendar.MONTH)
                date[2] = cal.get(Calendar.DAY_OF_MONTH)
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()

        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var showCalender : ImageButton = findViewById(R.id.calender_btn)
        val transaction = supportFragmentManager.beginTransaction()
        showCalender.visibility = View.VISIBLE
        when(item.itemId){
            R.id.scaduale_management -> {
                transaction.replace(R.id.mainfragment, MainPost())
                transaction.addToBackStack(null)
                transaction.commit()
            }
            R.id.diary->{
                transaction.replace(R.id.mainfragment, Diary())
                transaction.addToBackStack(null)
                transaction.commit()
            }
            R.id.pet_page-> {
                showCalender.visibility = View.GONE
                transaction.replace(R.id.mainfragment, PetPage())
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
        return super.onOptionsItemSelected(item)

    }


}