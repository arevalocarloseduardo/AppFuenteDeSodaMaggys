package com.appsmaggys.caear.appfuentedesodamaggys

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.appmaggys.caear.appfuentedesodamaggys.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.appsmaggys.caear.appfuentedesodamaggys.Datos.DatosUsuario
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    lateinit var txtName: EditText
    lateinit var txtCorreo:EditText
    lateinit var txtPasword:EditText
    lateinit var progresBar:ProgressBar
    lateinit var dbRefernce:DatabaseReference
    lateinit var database:FirebaseDatabase
    lateinit var auth: FirebaseAuth
    lateinit var btnRegister:Button
    lateinit var sda:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        txtName = input_name
        txtCorreo = input_email
        txtPasword = input_password
        progresBar = progressBarHori
        btnRegister = btn_signup
        sda=link_login

        sda.setOnClickListener {
            startActivity((Intent(this,LoginActivity::class.java)))
        }
        database= FirebaseDatabase.getInstance()
        auth= FirebaseAuth.getInstance()
        dbRefernce=database.reference.child("User")
        btnRegister.setOnClickListener {
            createNewCuenta()
        }


    }

    private fun createNewCuenta() {
        val name:String=txtName.text.toString()
        val correo:String=txtCorreo.text.toString()
        val contraseña:String=txtPasword.text.toString()

        if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(correo)&&!TextUtils.isEmpty(contraseña)){
            progresBar.visibility=View.VISIBLE
            auth.createUserWithEmailAndPassword(correo,contraseña)
                .addOnCompleteListener(this){
                    task ->
                    if (task.isComplete){
                        val user:FirebaseUser?=auth.currentUser
                        verifyEmail(user)
                        val userBD=dbRefernce.child(user!!.uid)
                        userBD.child("name").setValue(name)
                        subirDatos(name,correo)
                    }
                }
        }

    }
    private fun subirDatos(nombre:String,correo:String) {

        val uid = FirebaseAuth.getInstance().uid ?:""//guardo en uid la autentificacion
        val ref = FirebaseDatabase.getInstance().getReference("/Users/$uid")//carlos= $uid creo una base de datos re piola
        val user = DatosUsuario(uid,nombre,"foto","edad",correo,"telefono","0","1","calle 10")//guardo la imagen y
        ref.setValue(user)
        startActivity(Intent(this,LoginActivity::class.java))
    }

    private fun verifyEmail(user:FirebaseUser?) {
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this){
                task ->
                if (task.isComplete){
                    Toast.makeText(this,"email enviado",Toast.LENGTH_LONG).show()
                }else{Toast.makeText(this,"Error de correo",Toast.LENGTH_LONG).show()}
            }
    }
}
