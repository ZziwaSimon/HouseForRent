package com.example.sasularent.model

class TenantInfo {
    var FirstName: String? = null
    var LastName: String? = null
    var Email: String? = null
    var phone: String? = null
    var password: String? = null

    constructor(fname:String?, lname: String?, email: String?, phone: String?, pwd: String?){
        this.FirstName = fname
        this.LastName = lname
        this.Email = email
        this.phone = phone
        this.password = pwd
    }
}