package dev.beewise.jokes.models.user

import dev.beewise.jokes.models.photo.Photo
import java.io.Serializable

class User(var id: Int?, var uuid: String?, var createdAt: String?, var username: String?, var email: String?, var name: String?, var photo: Photo?): Serializable {
    companion object {
        var currentUser: User? = null
    }
}
