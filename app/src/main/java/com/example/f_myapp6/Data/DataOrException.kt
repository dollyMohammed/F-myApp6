package com.example.f_myapp6.Data

 data class DataOrException<T,Boolean,E:Exception>(
     var data:T?=null,
 var loading:Boolean?=null,
var e:E?=null )
