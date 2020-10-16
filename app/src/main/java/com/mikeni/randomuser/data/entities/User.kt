package com.mikeni.randomuser.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val cell: String,
    val dob: Dob,
    val email: String,
    val gender: String,
    val id: Id,
    val location: Location,
    val login: Login,
    val name: Name,
    val nat: String,
    val phone: String,
    val picture: Picture,
    val registered: Registered
) : Parcelable {

    @Parcelize
    data class Dob(
        val age: Int,
        val date: String
    ) : Parcelable

    @Parcelize
    data class Id(
        val name: String,
        val value: String
    ) : Parcelable

    @Parcelize
    data class Location(
        val city: String,
        val coordinates: Coordinates,
        val country: String,
        val postcode: String,
        val state: String,
        val street: Street,
        val timezone: Timezone
    ) : Parcelable {

        @Parcelize
        data class Coordinates(
            val latitude: String,
            val longitude: String
        ) : Parcelable

        @Parcelize
        data class Street(
            val name: String,
            val number: Int
        ) : Parcelable

        @Parcelize
        data class Timezone(
            val description: String,
            val offset: String
        ) : Parcelable
    }

    @Parcelize
    data class Login(
        val md5: String,
        val password: String,
        val salt: String,
        val sha1: String,
        val sha256: String,
        val username: String,
        val uuid: String
    ) : Parcelable

    @Parcelize
    data class Name(
        val first: String,
        val last: String,
        val title: String
    ) : Parcelable

    @Parcelize
    data class Picture(
        val large: String,
        val medium: String,
        val thumbnail: String
    ) : Parcelable

    @Parcelize
    data class Registered(
        val age: Int,
        val date: String
    ) : Parcelable
}
