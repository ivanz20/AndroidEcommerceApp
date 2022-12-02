package com.example.pia_moviles.Modelos

import java.sql.Blob

data class UsuarioModel (
    var id:Int? = null,
    var nombre:String? = null,
    var apellido:String? = null,
    var email:String?=null,
    var password:String?=null,
    var telefono:String?=null,
    var imagen:String?=null,
    var created_at:String?=null,
    var updated_at:String?=null,
    val status: String?=null
        ) {

}