package com.example.calendarapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initControls()
        navigateActivity()
    }

    private fun navigateActivity() {
        if(getRefs()==null){
            val intent = Intent(this,MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    private fun initControls() {
        val tvPass = findViewById<EditText>(R.id.edt_password)
        val btnLogin = findViewById<Button>(R.id.btn_login)
        btnLogin.setOnClickListener {
            val value = tvPass.text.toString()
            if(value==getRefs()){
                val intent = Intent(this,MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            } else Toast.makeText(this,"Check Your PassWord",Toast.LENGTH_SHORT).show()
        }
    }

    private fun getRefs() : String?{
        val sharedPref = this.getSharedPreferences("Pass",Context.MODE_PRIVATE) ?: return null
        val defaultValue: String? = null
        val pass = sharedPref.getString(getString(R.string.key), defaultValue)
        return pass
    }
}