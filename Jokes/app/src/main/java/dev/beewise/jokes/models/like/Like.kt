package dev.beewise.jokes.models.like

import java.io.Serializable
import dev.beewise.jokes.models.user.User
import dev.beewise.jokes.models.joke.Joke

class Like(var id: Int?, var uuid: String?, var createdAt: String?, var joke: Joke?, var user: User?, var type: Int?): Serializable

enum class LikeType(val value: Int) {
    like(0),
    dislike(1);

    companion object {
        fun from(value: Int?): LikeType? {
            if (value == null) {
                return null
            }
            for (type in LikeType.values()) {
                if (type.value == value) {
                    return type
                }
            }
            return null
        }
    }
}