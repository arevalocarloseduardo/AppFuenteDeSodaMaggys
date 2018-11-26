package com.appsmaggys.caear.appfuentedesodamaggys.Fragments.AdaptadoresFragments

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.appmaggys.caear.appfuentedesodamaggys.R
import com.google.firebase.database.FirebaseDatabase
import com.appsmaggys.caear.appfuentedesodamaggys.Datos.DatosPedidos

import kotlinx.android.synthetic.main.datos_pedidos.view.*

class PedidosAdapter(var list: MutableList<DatosPedidos>): RecyclerView.Adapter<PedidosAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.datos_pedidos,parent,false)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return list.size
    }
    override fun onBindViewHolder(holder: PedidosAdapter.ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        lateinit var estado:TextView
        fun bindItems(data: DatosPedidos){

            val menu = itemView.tvMenu1
            val cant= itemView.tvCant1
            val llevar= itemView.tvLlevar1
            val btnDelete = itemView.btnEliminar1
            estado = itemView.tvPreparando

            menu.text = data.menu
            cant.text = data.cant
            llevar.text = data.llevar

            when (data.estado) {
                "A confirmar" -> {
                    estado.setTextColor(Color.RED)
                    estado.text = data.estado}
                "Preparando..." -> {
                    estado.setTextColor(Color.BLUE)
                    estado.text = data.estado}
                "Listo" -> {
                    estado.setTextColor(Color.GREEN)
                    estado.text = data.estado}
            }
            estado.text = data.estado

            btnDelete.setOnClickListener {
                deleteInfo(data)
            }
        }
        private fun deleteInfo(data: DatosPedidos) {
            val myBaseDeDatos = FirebaseDatabase.getInstance().getReference("Confirmados")
            myBaseDeDatos.child(data.telefono).removeValue()
        }
    }
}