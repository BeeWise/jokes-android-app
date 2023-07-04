package dev.beewise.jokes.models.user

import dev.beewise.jokes.models.photo.Photo
import java.io.Serializable

class User(var id: Int? = null, var uuid: String? = null, var createdAt: String? = null, var username: String? = null, var email: String? = null, var name: String? = null, var photo: Photo? = null): Serializable {
    companion object {
        var currentUser: User? = null
    }
}
