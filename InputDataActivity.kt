package com.example.mypetdiary

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.mypetdiary.data.PetData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class InputDataActivity : AppCompatActivity() {

    val auth = Firebase.auth
    val db = Firebase.firestore

    private val REQUEST_CODE = 99

    //갤러리 권한 여부
    private fun requestPermission() : Boolean{
        var writePermission: Int = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        var readPermission: Int = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if(writePermission == PackageManager.PERMISSION_GRANTED && readPermission == PackageManager.PERMISSION_GRANTED){
            return true
        }
        else(return false)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_CODE -> {
                if (grantResults.isNotEmpty()) {
                    var isAllGranted = true
                    for (grant in grantResults) {
                        if (grant != PackageManager.PERMISSION_GRANTED) { //권한 거부
                            isAllGranted = false
                            break
                        }
                        if (isAllGranted) {  } //권한 동의
                        else { //권한 거부했을경우
                            if (!ActivityCompat.shouldShowRequestPermissionRationale(
                                    this,
                                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                                ) ||
                                !ActivityCompat.shouldShowRequestPermissionRationale(
                                    this,
                                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                                )
                            ) {requestPermission() } else { break }
                        }
                    }
                }
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_data)

        var actionBar : ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        //반려동물 사진
        var image: ImageView = findViewById(R.id.openGallery)

        var getContent : ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
                result: ActivityResult ->
            if(result.resultCode == Activity.RESULT_OK){
                var uri = result.data?.data
                image.setImageURI(uri)

            }
        }
        image.setOnClickListener {
            if (requestPermission()) {
            var intent = Intent(Intent.ACTION_PICK)

            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            intent.type = "image/*"
            getContent.launch(intent)
            } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE)
            }
        }

        //*************************************************************

        //반려동물 생년월일
/*
        val years: Array<Int> = Array(20, { 0 })
        for (i in 2002..2021) {
            years[i - 2002] = i
        }
        val months : Array<Int> = Array(12, {0})
        for (i in 1..12){
            months[i-1] = i
        }
        val days: Array<Int> = Array(31, { 0 })
        for (i in 1..31) {
            days[i - 1] = i
        }
        var petsBirth = Array(3) { 0 }

        var spinnerBrithyears : Spinner = findViewById(R.id.spinner_year)
        var spinnerBirthMonths : Spinner = findViewById(R.id.spinner_month)
        var spinnerBirthDays : Spinner = findViewById(R.id.spinner_day)

        spinnerBrithyears.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
        spinnerBirthMonths.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, months)
        spinnerBirthDays.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, days)

        spinnerBrithyears.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                petsBirth[0] = Integer.parseInt(spinnerBrithyears.selectedItem.toString())
            }
        }
        spinnerBirthMonths.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                petsBirth[1] = Integer.parseInt(spinnerBirthMonths.selectedItem.toString())
            }
        }
        spinnerBirthDays.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                petsBirth[2] = Integer.parseInt(spinnerBrithyears.selectedItem.toString())
            }
        }

*/
        //반려동물 이름, 나이, 체중
        var inputName: EditText = findViewById(R.id.inputName)
        var inputAge: EditText = findViewById(R.id.inputAge)
        var inputWeight: EditText = findViewById(R.id.inputWeight)

        var pets_Name = inputName.text.toString()
//        var pets_Age = Integer.parseInt(inputAge.toString())
//        var pets_Weight = Integer.parseInt(inputWeight.toString())

        var pets_Weight : Int
        var pets_Age : Int
        try{
            pets_Age = Integer.parseInt(inputAge.text.toString())
        }catch (e :NumberFormatException){pets_Age = 0}
        try{
            pets_Weight = Integer.parseInt(inputWeight.text.toString())
        }catch (e :NumberFormatException){pets_Weight=0}

        //반려동물 성별
        lateinit var pets_Gender : String

        var spinner_Gender = findViewById<Spinner>(R.id.spinner_Gender)

        spinner_Gender.adapter = ArrayAdapter.createFromResource(this, R.array.genderArray, android.R.layout.simple_spinner_item)

        spinner_Gender.onItemSelectedListener  = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when(position) {
                    0 -> pets_Gender = "선택안함"
                    1 -> pets_Gender = "남성"
                    2 -> pets_Gender = "여성"

                }
            }
        }

        var user = auth.currentUser?.uid

        var plusButton: Button = findViewById(R.id.PlusButton)
        plusButton.setOnClickListener {
            showDialog(user, pets_Name,pets_Age,pets_Weight,pets_Gender)
        }

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
}