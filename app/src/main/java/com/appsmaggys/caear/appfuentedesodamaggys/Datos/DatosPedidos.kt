package com.appsmaggys.caear.appfuentedesodamaggys.Datos

class DatosPedidos(val id: String,val cliente: String,val menu: String,val llevar : String,val cant : String,val precio:String,val telefono:String,val estado:String,val sumaPuntos:String,val descripcion:String,val nota:String,val Hora:String,val fecha:String){
    constructor():this("","","","","","","","","","","","",""){
    }
}