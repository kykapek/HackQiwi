package ru.teamview.hackqiwi.ui.utils

import android.os.SystemClock
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

inline fun View.onClick(delayMillis: Long = 500, crossinline clickListener: (View) -> Unit) {
    var clickMillis = 0L
    setOnClickListener {
        val elapsedRealTime = SystemClock.elapsedRealtime()
        if (elapsedRealTime > clickMillis) {
            clickMillis = elapsedRealTime + delayMillis
            clickListener.invoke(it)
        }
    }
}

fun Fragment.permissions(
    vararg permissions: String,
    extension: PermissionsBuilder.() -> Unit
) {
    val builder = PermissionsBuilder()
    builder.apply(extension)
    requestMultiplePermissions(
        permissions = *permissions,
        allGranted = builder.allGranted,
        denied = builder.denied
    )
}

private inline fun Fragment.requestMultiplePermissions(
    vararg permissions: String,
    crossinline allGranted: () -> Unit = {},
    crossinline denied: (List<String>) -> Unit = {}
) {
    requireActivity().activityResultRegistry.register("permissionsKey", ActivityResultContracts.RequestMultiplePermissions()) { result: Map<String, Boolean> ->
        val deniedList = result.filter { !it.value }.map { it.key }
        when {
            deniedList.isNotEmpty() -> {
                denied.invoke(deniedList)
            }
            else -> {
                allGranted.invoke()
            }
        }
    }.launch(permissions as Array<String>?)
}

inline fun View.show() {
    visibility = View.VISIBLE
}

inline fun View.invisible() {
    visibility = View.INVISIBLE
}

inline fun View.hide() {
    visibility = View.GONE
}