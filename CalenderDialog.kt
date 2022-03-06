//캘린더 다이얼로그
import android.app.DatePickerDialog

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
