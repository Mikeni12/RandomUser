package com.mikeni.randomuser.data.remote

import com.mikeni.randomuser.data.entities.User
import com.mikeni.randomuser.utils.EntityMapper
import javax.inject.Inject

class NetworkMapper @Inject constructor() : EntityMapper<UserNetworkEntity, User> {

    override fun mapFromEntity(entity: UserNetworkEntity): User {
        with(entity) {
            return User(
                cell,
                User.Dob(dob.age, dob.date),
                email,
                if (gender == "male") "masculino" else "femenino",
                User.Id(id.name, id.value ?: "null"),
                with(location) {
                    User.Location(
                        city,
                        User.Location.Coordinates(coordinates.latitude, coordinates.longitude),
                        country,
                        postcode,
                        state,
                        User.Location.Street(street.name, street.number),
                        User.Location.Timezone(timezone.description, timezone.offset)
                    )
                },
                with(login) {
                    User.Login(md5, password, salt, sha1, sha256, username, uuid)
                },
                User.Name(name.first, name.last, name.title),
                nat,
                phone,
                User.Picture(picture.large, picture.medium, picture.thumbnail),
                User.Registered(registered.age, registered.date)
            )
        }
    }

    override fun mapToEntity(domainModel: User): UserNetworkEntity {
        with(domainModel) {
            return UserNetworkEntity(
                cell,
                UserNetworkEntity.Dob(dob.age, dob.date),
                email,
                gender,
                UserNetworkEntity.Id(id.name, id.value),
                with(location) {
                    UserNetworkEntity.Location(
                        city,
                        UserNetworkEntity.Location.Coordinates(
                            coordinates.latitude,
                            coordinates.longitude
                        ),
                        country,
                        postcode,
                        state,
                        UserNetworkEntity.Location.Street(street.name, street.number),
                        UserNetworkEntity.Location.Timezone(timezone.description, timezone.offset)
                    )
                },
                with(login) {
                    UserNetworkEntity.Login(md5, password, salt, sha1, sha256, username, uuid)
                },
                UserNetworkEntity.Name(name.first, name.last, name.title),
                nat,
                phone,
                UserNetworkEntity.Picture(picture.large, picture.medium, picture.thumbnail),
                UserNetworkEntity.Registered(registered.age, registered.date)
            )
        }
    }

    fun mapFromEntityList(entities: List<UserNetworkEntity>): List<User> =
        entities.map { mapFromEntity(it) }
}