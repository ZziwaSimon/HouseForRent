package com.example.sasularent.model

class LandlordInfo {
    var FirstName: String? = null
    var LastName: String? = null
    var Location: String? = null
    var RentalID: String? = null
    var email: String? = null
    var password: String? = null
    var phoneNumber: String? = null
    var hashMap: HashMap<String, List<Any>>?

    constructor(fname: String?, lname: String?, location: String?, rentalID: String?, email: String?, pwd: String?, phone: String?,map: HashMap<String, List<Any>>?){
        this.FirstName = fname
        this.LastName = lname
        this.Location = location
        this.RentalID = rentalID
        this.email = email
        this.password = pwd
        this.phoneNumber = phone
        this.hashMap = map
    }
}