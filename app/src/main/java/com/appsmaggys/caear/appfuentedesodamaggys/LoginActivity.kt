package com.appsmaggys.caear.appfuentedesodamaggys

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.appmaggys.caear.appfuentedesodamaggys.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    lateinit var txtUsers:EditText
    lateinit var txtPassword:EditText
    lateinit var proggrees:ProgressBar
    lateinit var btnEnviar:Button
    lateinit var btnreg:TextView
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtUsers = txtUser
        txtPassword=txtPass
        proggrees=progressBarLog
        btnEnviar=btn_Iniciar
        btnreg=txtNotenes

        auth= FirebaseAuth.getInstance()
        proggrees.visibility=View.GONE
        btnEnviar.setOnClickListener {  loginUser()}
        btnreg.setOnClickListener { registrarte() }

    }

    private fun registrarte() {
        val intent = Intent(this,RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun loginUser() {
        val user:String=txtUsers.text.toString()
        val passw:String=txtPassword.text.toString()

        if(!TextUtils.isEmpty(user)&&!TextUtils.isEmpty(passw)){
            proggrees.visibility=View.VISIBLE
            auth.signInWithEmailAndPassword(user,passw)
                .addOnCompleteListener(this){
                    task ->
                    if (task.isSuccessful){
                        action()
                    }else{
                        proggrees.visibility=View.GONE
                        Toast.makeText(this,"error en la autentificacion",Toast.LENGTH_LONG).show()
                    }
                }
        }
    }
    private fun action() {

        val intent = Intent(this,MenuActivity::class.java)
        intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
