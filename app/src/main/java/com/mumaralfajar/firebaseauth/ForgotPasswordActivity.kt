package com.mumaralfajar.firebaseauth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        initActionBar()

        btnSendEmail.setOnClickListener {
            val email : String = etEmailForgotPass.text.toString().trim()
            if (email.isEmpty()) {
                etEmailForgotPass.error = "Please fill your email"
                etEmailForgotPass.requestFocus()
                return@setOnClickListener
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmailForgotPass.error = "Please input a valid email"
                etEmailForgotPass.requestFocus()
                return@setOnClickListener
            } else {
                forgotPass(email)
            }
        }

        tbForgotPass.setNavigationOnClickListener {
            finish()
        }
    }

    private fun forgotPass(email: String) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Your reset password has been sent to your email",
                        Toast.LENGTH_LONG
                    ).show()
                    startActivity(Intent(this, SignInActivity::class.java))
                    finishAffinity()
                } else {
                    Toast.makeText(
                        this,
                        "Failed to reset password",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            .addOnFailureListener {
                exception ->
                Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
            }
    }

    private fun initActionBar() {
        setSupportActionBar(tbForgotPass)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
    }
}