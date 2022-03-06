//데이터 클래스
//data/PetData.kt
data class PetData(var uId: String? = null,
                   var name:String? = null,
                   var age:Int? = null,
                   var weight: Int? = null,
                   var gender:String? = null){

}

//main.kt
import com.example.mypetdiary.data.PetData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

    val auth = Firebase.auth
    val db = Firebase.firestore
    var user = auth.currentUser?.uid

    var plusButton: Button = findViewById(R.id.PlusButton)
    plusButton.setOnClickListener {
        showDialog(user, pets_Name,pets_Age,pets_Weight,pets_Gender)
    }
    
    fun showDialog(uId: String?, name:String, age:Int, weight:Int, gender:String){
        AlertDialog.Builder(this)
            .setTitle("정보확인")
            .setMessage("이름 : $name\n나이 : $age\n체중 : $weight\n성 : $gender")
            .setPositiveButton("확인",{dialogInterface : DialogInterface, i:Int->
                AddPet(uId, name, age, weight, gender) // 데이터 저장
            })
            .setNegativeButton("취소",{dialogInterface:DialogInterface, i:Int->
            })
            .show()
    }

    //데이터 파이어 베이스에 저장
    fun AddPet(uId : String?, name:String, age:Int, weight:Int, gender:String){
        val pet = PetData(uId,name, age, weight, gender)

        if (uId != null) {
            db.collection("Pets").document(uId).set(pet)
                .addOnSuccessListener { Toast.makeText(getApplicationContext(), "저장완료", Toast.LENGTH_SHORT).show() }
                .addOnFailureListener{Toast.makeText(getApplicationContext(), "저장실패", Toast.LENGTH_SHORT).show() }
        }
        val toMain = Intent(this, StartActivity::class.java)
        startActivity(toMain)
    }
