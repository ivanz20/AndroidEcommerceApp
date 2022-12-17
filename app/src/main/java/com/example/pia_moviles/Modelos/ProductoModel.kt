package com.example.pia_moviles.Modelos

data class ProductoModel (
        var idproducto:Int? = null,
        var nombreproducto:String? = null,
        var descripcion:String? = null,
        var precio:Float?=null,
        var categoria:String?=null,
        var imagen:String?=null,
        var created_at:String?=null,
        var iduser:Int? = null,
        val status: String?=null,
        var calificacion:Int? = null,
        var publicado: Boolean?= null

        )