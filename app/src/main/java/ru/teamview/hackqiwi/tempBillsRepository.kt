package ru.teamview.hackqiwi

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class tempBillsRepository @Inject constructor(
    private val tempApiService: tempApiService

) {

}