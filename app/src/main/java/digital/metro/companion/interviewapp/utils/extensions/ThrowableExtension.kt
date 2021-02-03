package digital.metro.companion.interviewapp.utils.extensions

import android.util.Log

fun Throwable?.log(clazz: Class<*>, message: String = "") {
    Log.e(clazz::class.java.simpleName, message, this)
}

fun Throwable?.log(clazz: Any, message: String = "") {
    Log.e(clazz::class.java.simpleName, message, this)
}
