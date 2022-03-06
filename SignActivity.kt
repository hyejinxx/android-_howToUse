package com.example.mypetdiary

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class SignActivity : AppCompatActivity() {

    private lateinit var googleSignInClient : GoogleSignInClient
    private val RC_SIGN_IN = 1313
    private lateinit var auth :FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        var actionBar : ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        val btn_google : SignInButton = findViewById(R.id.btn_googleLogin)

        // 처음 앱 실행시
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("559569220716-tcrktnppmv5d8ssp57p60vj3etfht2kp.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = FirebaseAuth.getInstance()

        btn_google.setOnClickListener {
            googleSignIn()
        }
    }

    // 구글 회원가입
    fun googleSignIn(){
    val signInIntent = googleSignInClient.signInIntent
    startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    fun toInputDataActivity(user: FirebaseUser?){
        if(user !=null){
            startActivity(Intent(this, InputDataActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //구글 회원가입 Methods
        if(requestCode == RC_SIGN_IN){
            var task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                var account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            }
            catch (e : ApiException){}
        }
        else{
        }
    }

        fun firebaseAuthWithGoogle(account : GoogleSignInAccount?){
            val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
            auth.signInWithCredential(credential).addOnCompleteListener(this) {
                    task ->
                if (task.isSuccessful){

                    toInputDataActivity(auth.currentUser)
                }
            }
        }

}