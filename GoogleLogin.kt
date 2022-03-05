
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


auth = FirebaseAuth.getInstance()


Handler(Looper.getMainLooper()).postDelayed({
    val account = GoogleSignIn.getLastSignedInAccount(this)
    if(account!==null){ // 이미 로그인 되어있을시 바로 메인 액티비티로 이동
        toMainActivity(auth.currentUser)
    }
    else{startActivity(pageMove)}
}, 2500L)

}
fun toMainActivity(user: FirebaseUser?) {
    if (user != null) {
        startActivity(Intent(this, MainActivity::class.java))
        this.finish()
    }
}
