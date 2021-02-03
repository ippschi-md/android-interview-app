package digital.metro.companion.interviewapp.utils.extensions

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

object GsonObject {
    var gson: Gson = GsonBuilder().setLenient().create()

    inline fun <reified T> fromJson(json: String): T =
        gson.fromJson(json, object : TypeToken<T>() {}.type)

    fun toJson(any: Any): String =
        gson.toJson(any)
}

inline fun <reified T> String.fromJson(): T = GsonObject.fromJson(this)

fun Any.toJson(): String = GsonObject.toJson(this)
