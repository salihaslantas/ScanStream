package com.salihaslantas.scanstream.models

data class ScannedProduct(
    val barcode: String,
    val location: String,
    val scannedBy: String,
    val timeStamp: Long
)
