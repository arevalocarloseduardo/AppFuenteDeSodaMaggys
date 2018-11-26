package com.appsmaggys.caear.appfuentedesodamaggys

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.appmaggys.caear.appfuentedesodamaggys.R
import com.appsmaggys.caear.appfuentedesodamaggys.Datos.DatosUsuario
import com.appsmaggys.caear.appfuentedesodamaggys.Fragments.PedidosFragment
import com.appsmaggys.caear.appfuentedesodamaggys.Fragments.PedirFragment
import com.appsmaggys.caear.appfuentedesodamaggys.Fragments.PerfilFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.app_bar_menu.*
import kotlinx.android.synthetic.main.content_menu.*


class MenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var pedirFragment: PedirFragment//Primer Caso para crear un fragment
    lateinit var pedidosFragment: PedidosFragment
    lateinit var perfilFragment: PerfilFragment

    lateinit var referenciaUsuarios : DatabaseReference
    lateinit var usuariosList:MutableList<DatosUsuario>
    lateinit var auth: FirebaseAuth
    lateinit var txtWelcome: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        setSupportActionBar(toolbar)


        referenciaUsuarios = FirebaseDatabase.getInstance().getReference("Users")
        usuariosList= mutableListOf()

        referenciaUsuarios.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    for (h in p0.children)
                    {
                        val user =FirebaseAuth.getInstance().uid
                        val cliente = h.getValue(DatosUsuario::class.java)?.uid
                        val hero = h.getValue(DatosUsuario::class.java)
                        if (cliente==user){
                            usuariosList.add(hero!!)
                        }
                    }
                    for (h in usuariosList){
                        txtWelcome.text = "Bienvenido "+h.nombre
                    }
                }
            }
        })
        pedirFragment=PedirFragment.newInstance()//segundo Paso para crear un fragment
        pedidosFragment=PedidosFragment.newInstance()
        perfilFragment=PerfilFragment.newInstance()
        txtWelcome=txtBien
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_settings ->  {
                var firse = FirebaseAuth.getInstance()
                firse.signOut()
                finish()
                startActivity(Intent(this,LoginActivity::class.java))
            }

        }
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_camera -> {
                txtWelcome.visibility= View.INVISIBLE
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.contenedorFragments,perfilFragment)
                    //.addToBackStack(PedidosFragment.toString())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()

            }
            R.id.nav_gallery -> {
                txtWelcome.visibility= View.INVISIBLE
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.contenedorFragments,pedirFragment)
                    //.addToBackStack(PedidosFragment.toString())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.nav_slideshow -> {
                txtWelcome.visibility= View.INVISIBLE
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.contenedorFragments,pedidosFragment)
                    //.addToBackStack(PedidosFragment.toString())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    .commit()
            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
