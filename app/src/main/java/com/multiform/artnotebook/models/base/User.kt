package com.multiform.artnotebook.models.base

data class User(
    val id: String = "",
    var username: String = "",
    var fullname: String = "",
    var phone: String = "",
    var bio: String = "",
    var status: String = "",
    var photoUrl: String = ""
)