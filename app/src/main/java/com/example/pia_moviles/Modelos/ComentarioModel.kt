package com.example.pia_moviles.Modelos

data class ComentarioModel (
    var id:Int? = null,
    var idproducto:Int? = null,
    var iduser:Int? = null,
    var comentario:String? = null,
    var usuario:String? = null,
    var calificacion:Int?=null,

)