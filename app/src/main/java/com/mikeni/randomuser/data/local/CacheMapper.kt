package com.mikeni.randomuser.data.local

import com.mikeni.randomuser.data.entities.User
import com.mikeni.randomuser.utils.EntityMapper
import javax.inject.Inject

class CacheMapper @Inject constructor(): EntityMapper<UserCacheEntity, User> {
    override fun mapFromEntity(entity: UserCacheEntity): User {
        with(entity) {
            return User(
                cell,
                User.Dob(dob.age, dob.date),
                email,
                gender,
                User.Id(id.name, id.value),
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

    override fun mapToEntity(domainModel: User): UserCacheEntity {
        with(domainModel) {
            return UserCacheEntity(
                cell,
                UserCacheEntity.Dob(dob.age, dob.date),
                email,
                gender,
                UserCacheEntity.Id(id.name, id.value),
                with(location) {
                    UserCacheEntity.Location(
                        city,
                        UserCacheEntity.Location.Coordinates(
                            coordinates.latitude,
                            coordinates.longitude
                        ),
                        country,
                        postcode,
                        state,
                        UserCacheEntity.Location.Street(street.name, street.number),
                        UserCacheEntity.Location.Timezone(timezone.description, timezone.offset)
                    )
                },
                with(login) {
                    UserCacheEntity.Login(md5, password, salt, sha1, sha256, username, uuid)
                },
                UserCacheEntity.Name(name.first, name.last, name.title),
                nat,
                phone,
                UserCacheEntity.Picture(picture.large, picture.medium, picture.thumbnail),
                UserCacheEntity.Registered(registered.age, registered.date)
            )
        }
    }

    fun mapFromEntityList(entities: List<UserCacheEntity>): List<User> =
        entities.map { mapFromEntity(it) }
}