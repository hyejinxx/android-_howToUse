
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


auth = FirebaseAuth.getInstance()
val pageMove = Intent(this, SignActivity::class.java)


Handler(Looper.getMainLooper()).postDelayed({
    val account = GoogleSignIn.getLastSignedInAccount(this)
    if(account!==null){ // 이미 로그인 되어있을시 바로 메인 액티비티로 이동
        toMainActivity(auth.currentUser)
    }
    else{startActivity(pageMove)} // 회원가입 페이지로 이동
}, 2500L)

}
//로그인 되어있을 시 스타트 페이지 거쳐서 메인으로 이동
fun toMainActivity(user: FirebaseUser?) {
    if (user != null) {
        startActivity(Intent(this, MainActivity::class.java))
        this.finish()
    }
}
