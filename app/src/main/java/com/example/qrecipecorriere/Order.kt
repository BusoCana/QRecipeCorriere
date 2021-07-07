package com.example.qrecipecorriere

import java.text.SimpleDateFormat
import java.util.*

class Order(var ingredients: String, var userName: String, var userSurname: String, var userAddress: String, var userCellular: String, var userEmail: String, var orderState: String) {
    var id: String
    var placeDate: String
    var arriveDate: String

    init {
        this.id = userSurname + "-" + getCurrentDateTime().toString("ddMMyyyy-HHmmss")
        this.placeDate = getCurrentDateTime().toString("dd/MM/yyyy HH:mm:ss")
        this.arriveDate = ""
    }

    constructor(id: String, ingredients: String, userName: String, userSurname: String, userAddress: String, userCellular: String, userEmail: String, placeDate: String, arriveDate: String, orderState: String): this(ingredients, userName, userSurname, userAddress, userCellular, userEmail, orderState) {
        this.id = id
        this.placeDate = placeDate
        this.arriveDate = arriveDate
    }

    constructor(): this("", "", "", "", "", "", "", "", "", "") {}

    private fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    private fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    fun set(newOrder: Order) {
        this.id = newOrder.id
        this.ingredients = newOrder.ingredients
        this.userName = newOrder.userName
        this.userSurname = newOrder.userSurname
        this.userAddress = newOrder.userAddress
        this.userCellular = newOrder.userCellular
        this.userEmail = newOrder.userEmail
        this.placeDate = newOrder.placeDate
        this.arriveDate = newOrder.arriveDate
        this.orderState = newOrder.orderState
    }

    override fun toString(): String {
        return "OrderID: " + id + "\nIngredients stamp: " + ingredients + "\nUser name: " + userName + "\nUser surname: " + userSurname + "\nUser address: " + userAddress +
                "\nUser cellular: " + userCellular + "\nUser email: " + userEmail + "\nPlace date: " + placeDate + "\nArrive date: " + arriveDate + "\nOrder state: " + orderState
    }
}