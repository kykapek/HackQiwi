package ru.teamview.hackqiwi.ui.utils

import android.app.Activity
import android.content.Context
import android.os.SystemClock
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.teamview.hackqiwi.R

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

fun Fragment.onPopBackStack() {
    hideKeyboard()
    findNavController().popBackStack()
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Fragment.showKeyboard() {
    view?.let { activity?.showKeyboard(it) }
}

fun Fragment.toast(text: String, length: Int = Toast.LENGTH_SHORT) = Toast.makeText(this.requireContext(), text, length).show()

fun Fragment.getBaseCountryCode() : String {
    return resources.getString(R.string.base_country_code)
}

fun Activity?.hideKeyboard(view: View? = this?.currentFocus) {
    this ?: return
    view ?: return
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity?.showKeyboard(view: View? = this?.currentFocus) {
    this ?: return
    view ?: return
    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}