package hash.application.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import hash.application.R
import hash.application.dataType.NewUser
import hash.application.managers.UserManager
import hash.application.managers.WebManager
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)//initalize layout
    }

    override fun onStart() {
        super.onStart()
        // jump to account register page
        val btnToLogin = button7
        btnToLogin.setOnClickListener{
//            Toast.makeText(this, "DEBUG: Jump to login page.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        // send entered user info to server and create a new user
        lateinit var servResult: String
        val btnRegister = button6
        btnRegister.setOnClickListener{
//            Toast.makeText(this, "DEBUG: Clicked on register.", Toast.LENGTH_SHORT).show()
            // connect to server
            val webThread = Thread ({
                val userInfo = NewUser(editText3.text.toString(), editText6.text.toString(), editText7.text.toString())
                servResult = WebManager.userSignup(userInfo)
            })
            webThread.start()
            webThread.join()
            // check result returned by server
            if (servResult == "Sign up success!") {
                UserManager.setUsername(editText3.text.toString())
                UserManager.setPassword(editText7.text.toString())
                UserManager.changeLoginState(true)
                finish()
            }
            else {
                Toast.makeText(this, servResult, Toast.LENGTH_SHORT).show()
            }
        }
    }
}