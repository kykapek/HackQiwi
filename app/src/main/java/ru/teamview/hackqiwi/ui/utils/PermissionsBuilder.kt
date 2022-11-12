package ru.teamview.hackqiwi.ui.utils

class PermissionsBuilder {

    var allGranted: () -> Unit = {}

    var denied: (List<String>) -> Unit = {}

    fun allGranted(callback: () -> Unit) {
        this.allGranted = callback
    }

    fun denied(callback: (List<String>) -> Unit) {
        this.denied = callback
    }
}