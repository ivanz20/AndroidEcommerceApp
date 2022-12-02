package com.example.pia_moviles.Modelos

data class CarritoModel (
        var id:Int? = null,
        var talla:String? = null,
        var color:String? = null,
        var imagen:String?=null,
        var nombreproducto: String?=null,
        var precio:Float?=null,
        var comprado:Boolean?=null,
        var created_at:String?=null,
        var iduser:Int? = null
        )