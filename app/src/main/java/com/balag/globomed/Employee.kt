package com.balag.globomed

class Employee (
    val id: String,
    val name: String,
    val dob: Long,
    val designation: String,
    val isSurgeon: Int) {
    override fun toString(): String {
        return "$id---$name----$dob----$designation"
    }
}