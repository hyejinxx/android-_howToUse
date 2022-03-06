
import android.app.AlertDialog
import android.content.DialogInterface

var plusButton: Button = findViewById(R.id.PlusButton)
plusButton.setOnClickListener {
    showDialog(user, pets_Name,pets_Age,pets_Weight,pets_Gender)
}

fun showDialog(uId: String?, name:String, age:Int, weight:Int, gender:String){
    AlertDialog.Builder(this)
        .setTitle("정보확인")
        .setMessage("이름 : $name\n나이 : $age\n체중 : $weight\n성 : $gender")
        .setPositiveButton("확인",{dialogInterface : DialogInterface, i:Int->
            AddPet(uId, name, age, weight, gender)
        })
        .setNegativeButton("취소",{dialogInterface:DialogInterface, i:Int->
        })
        .show()
    }
}
