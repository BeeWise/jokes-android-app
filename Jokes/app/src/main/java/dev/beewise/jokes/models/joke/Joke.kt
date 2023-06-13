package dev.beewise.jokes.models.joke

import dev.beewise.jokes.models.like.Like
import dev.beewise.jokes.models.like.LikeType
import dev.beewise.jokes.models.user.User
import java.io.Serializable

class Joke(
    var id: Int?,
    var uuid: String?,
    var text: String?,
    var likeCount: Int = 0,
    var dislikeCount: Int = 0,
    var commentCount: Int = 0,
    var createdAt: String?,
    var source: String?,
    var type: Int?,
    var status: Int = JokeStatus.pending.value,
    var user: User?,
    var like: Like?
    ): Serializable {

    fun isLiked(): Boolean {
        if (this.like != null) {
            return this.like?.type == LikeType.like.value
        }
        return false;
    }

    fun isDisliked(): Boolean {
        if (this.like != null) {
            return this.like?.type == LikeType.dislike.value
        }
        return false
    }
}

enum class JokeStatus(val value: Int) {
    pending(0),
    approved(1),
    rejected(2),
    adminRemoved(3),
    ownerRemoved(4);

    companion object {
        fun from(value: Int?): JokeStatus? {
            if (value == null) {
                return null
            }
            for (type in JokeStatus.values()) {
                if (type.value == value) {
                    return type
                }
            }
            return null
        }
    }

    fun title(): String? {
        return when (this.value) {
            approved.value -> "Approved" //ApplicationLocalization.instance.jokeStatusApprovedTitle();
            pending.value -> "Pending" //ApplicationLocalization.instance.jokeStatusPendingTitle();
            rejected.value -> "Rejected" //ApplicationLocalization.instance.jokeStatusRejectedTitle();
            adminRemoved.value -> "AdminRemoved" //ApplicationLocalization.instance.jokeStatusAdminRemovedTitle();
            ownerRemoved.value -> "OwnerRemoved" //ApplicationLocalization.instance.jokeStatusOwnerRemovedTitle();
            else -> return null
        }
    }
}

enum class JokeOrderBy(val value: Int) {
    points(0),
    latest(1);

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

enum class JokeType(val value: Int) {
    text(0),
    qna(1);

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