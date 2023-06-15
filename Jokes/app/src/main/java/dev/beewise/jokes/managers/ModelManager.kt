package dev.beewise.jokes.managers

import dev.beewise.jokes.models.joke.Joke
import dev.beewise.jokes.models.joke.JokeStatus
import dev.beewise.jokes.models.like.Like
import dev.beewise.jokes.models.photo.Photo
import dev.beewise.jokes.models.user.User
import org.json.JSONArray
import org.json.JSONObject

class ModelManager {
    public companion object {
        public val instance = ModelManager()
    }

    fun parseJokes(jsonArray: JSONArray?): ArrayList<Joke> {
        if (jsonArray == null) return arrayListOf()
        val array = ArrayList<Joke>()
        for (i in 0 until jsonArray.length()) {
            this.parseJoke(jsonArray.optJSONObject(i))?.let { joke ->
                array.add(joke)
            }
        }
        return array
    }

    fun parseJoke(jsonObject: JSONObject?): Joke? {
        if (jsonObject == null) return null
        if (!jsonObject.has("id")) return null
        if (!jsonObject.has("uuid")) return null

        val id = jsonObject.getInt("id")
        val uuid = jsonObject.getString("uuid")
        val text = jsonObject.optString("text")
        val answer = jsonObject.optString("answer")
        val likeCount = jsonObject.optInt("like_count")
        val dislikeCount = jsonObject.optInt("dislike_count")
        val commentCount = jsonObject.optInt("comment_count")
        val createdAt = jsonObject.optString("created_at")
        val source = jsonObject.optString("source")
        val type = jsonObject.optInt("type", JokeStatus.pending.value)
        val status = jsonObject.optInt("status")
        val user = this.parseUser(jsonObject.optJSONObject("user"))
        val like = this.parseLike(jsonObject.optJSONObject("like"))
        return Joke(id, uuid, text, answer, likeCount, dislikeCount, commentCount, createdAt, source, type, status, user, like)
    }

    fun parseUser(jsonObject: JSONObject?): User? {
        if (jsonObject == null) return null
        if (!jsonObject.has("id")) return null
        if (!jsonObject.has("uuid")) return null

        val id = jsonObject.getInt("id")
        val uuid = jsonObject.getString("uuid")
        val createdAt = jsonObject.optString("created_at")
        val username = jsonObject.optString("username")
        val email = jsonObject.optString("email")
        val name = jsonObject.optString("name")
        val photo = this.parsePhoto(jsonObject.optJSONObject("photo"))
        return User(id, uuid, createdAt, username, email, name, photo)
    }

    fun parsePhoto(jsonObject: JSONObject?): Photo? {
        if (jsonObject == null) return null
        if (!jsonObject.has("id")) return null
        if (!jsonObject.has("uuid")) return null

        val id = jsonObject.getInt("id")
        val uuid = jsonObject.getString("uuid")
        val createdAt = jsonObject.optString("created_at")
        val url = jsonObject.optString("url")
        val url150 = jsonObject.optString("url_150")
        val url450 = jsonObject.optString("url_450")
        return Photo(id, uuid, createdAt, url, url150, url450)
    }

    fun parseLike(jsonObject: JSONObject?): Like? {
        if (jsonObject == null) return null
        if (!jsonObject.has("id")) return null
        if (!jsonObject.has("uuid")) return null

        val id = jsonObject.getInt("id")
        val uuid = jsonObject.getString("uuid")
        val createdAt = jsonObject.optString("created_at")
        val type = jsonObject.optInt("type")
        return Like(id, uuid, createdAt, null, null, type)
    }
}