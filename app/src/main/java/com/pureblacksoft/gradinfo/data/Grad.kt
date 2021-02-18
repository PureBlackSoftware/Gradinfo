package com.pureblacksoft.gradinfo.data

data class Grad(var number: Int, var name: String, var degree: String, var year: Int, var image: ByteArray) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Grad

        if (!image.contentEquals(other.image)) return false

        return true
    }

    override fun hashCode(): Int {
        return image.contentHashCode()
    }
}