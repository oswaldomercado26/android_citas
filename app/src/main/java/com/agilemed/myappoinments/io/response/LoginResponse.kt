package com.agilemed.myappoinments.io.response

import com.agilemed.myappoinments.model.User

data class LoginResponse(val success: Boolean, val user: User, val jwt: String)