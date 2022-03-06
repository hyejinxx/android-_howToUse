package com.example.mypetdiary

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity

class AddScadule : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_scadule)

        var actionBar : ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

     //   var date = arrayOf(0,0m0)



        var year = 0
        var month = 0
        var day = 0

        if(intent.hasExtra("year") && intent.hasExtra("month")&&intent.hasExtra("day")){
            year = intent.getIntExtra("year", 0)
            month = intent.getIntExtra("month", 0)
            day = intent.getIntExtra("day", 0)
        }
        var dateView : TextView = findViewById(R.id.dateView)
        dateView.text = String.format("★%d년 %d월 %d일★",year, month, day)

        lateinit var repeat_scadule : String
        val repeat_Scadule_spinner : Spinner = findViewById(R.id.spinnerRepeat)
        repeat_Scadule_spinner.adapter = ArrayAdapter.createFromResource(this,R.array.repeatScadule, android.R.layout.simple_spinner_item)

        repeat_Scadule_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position){
                    0 -> repeat_scadule = "없음"
                    1 -> repeat_scadule = "매일"
                    2 -> repeat_scadule = "매주"
                    3 -> repeat_scadule = "매달"
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_scadule_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.cancel
            -> {
                val intentBack = Intent(this, MainActivity::class.java)
                startActivity(intentBack)
            }
            R.id.ok->{
                val intentBack = Intent(this, MainActivity::class.java)
                startActivity(intentBack)
            }
        }
        return super.onOptionsItemSelected(item)

    }
    fun addScadule(){

    }

}