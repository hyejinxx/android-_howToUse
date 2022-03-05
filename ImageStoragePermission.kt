//build.gradle
dependencies {
    implementation platform('com.google.firebase:firebase-bom:29.0.4')
    implementation 'com.google.firebase:firebase-auth-ktx'
    implementation 'com.google.firebase:firebase-firestore-ktx'
    implementation 'com.google.firebase:firebase-analytics'
    implementation 'com.google.android.gms:play-services-auth:20.1.0'
    implementation 'com.google.firebase:firebase-database-ktx'
}
//갤러리 권한
//AndroidManifest.xml
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" /> <!-- 카메라 권한 -->
    <uses-permission android:name="andrioid.permission.CAMERA" />
    <uses-permission android:name="android.permission.INTERNET" />

//ImageUpload.kt
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

    private val REQUEST_CODE = 99

    //갤러리 권한 요청
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
    
    //권한 요청 결과 수신
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
                        if (grant != PackageManager.PERMISSION_GRANTED) { //권한 동의 여부
                            isAllGranted = false
                            break
                        }
                        if (isAllGranted) {  } //권한 동의
                        else { // 권한 거부했을 경우
                            if (!ActivityCompat.shouldShowRequestPermissionRationale(
                                    this,
                                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                                ) ||
                                !ActivityCompat.shouldShowRequestPermissionRationale(
                                    this,
                                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                                ) //다시 묻지 않기 체크하면서 권한 거부했을경우 다시 요청
                            )/ {requestPermission() } else { break }
                        }
                    }
                }
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        //Image 가져오기
        var image: ImageView = findViewById(R.id.openGallery)

        image.setOnClickListener {
            if (requestPermission()) { //권한 확인
            var intent = Intent(Intent.ACTION_PICK)

            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            intent.type = "image/*"
            getContent.launch(intent)
            } else { //권한 요청
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE)
            }
        }
        // 이미지 가져오기[권한 동의 후]
        var getContent : ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
                result: ActivityResult ->
            if(result.resultCode == Activity.RESULT_OK){
                var uri = result.data?.data
                image.setImageURI(uri)
            }
        }
    }
