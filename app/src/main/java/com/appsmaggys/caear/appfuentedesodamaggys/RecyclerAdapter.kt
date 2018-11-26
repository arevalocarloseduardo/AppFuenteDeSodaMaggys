package com.appsmaggys.caear.appfuentedesodamaggys

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.appmaggys.caear.appfuentedesodamaggys.R
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import com.appsmaggys.caear.appfuentedesodamaggys.Datos.DatosPedidos
import com.appsmaggys.caear.appfuentedesodamaggys.Datos.DatosImagenes
import kotlinx.android.synthetic.main.menus.view.*
import java.lang.Exception

class RecyclerAdapter(var list: MutableList<DatosImagenes>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.menus,parent,false)
    return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        fun bindItems(data: DatosImagenes){
            val checkBox1 = itemView.cbLLevarMenu
            val cant =itemView.edCant
            val btn=itemView.btn_agregarMenu
            val text = data.mimageUrl

            btn.setOnClickListener {

                var paraLlevar: String
                paraLlevar = if (checkBox1.isChecked) "Para LLevar"
                else ""
                val referenciaPedidos = FirebaseDatabase.getInstance().getReference("Pedidos")

                val fecha ="17/07/2018"
                val hora = "20:20"

                val heroId = referenciaPedidos.push().key.toString()
                if (cant.text.isNotEmpty()){
                    val hero = DatosPedidos(heroId,"",data.mName,paraLlevar,cant.text.toString().trim(),data.precio,"","","","","",hora,fecha)
                    referenciaPedidos.child(heroId).setValue(hero)
                }
        }
            itemView.progressBarMenu.visibility = View.VISIBLE
            Picasso.get().load(text).into(itemView.imagenMenu,
                object : com.squareup.picasso.Callback {
                    override fun onError(e: Exception?) {
                    }
                    override fun onSuccess() {
                        itemView.progressBarMenu.visibility = View.GONE
                    }
                })
//${data.descripcion}
            itemView.setOnClickListener{
                Toast.makeText(itemView.context,"${data.descripcion}",Toast.LENGTH_LONG).show()
            }
        }
    }
}
