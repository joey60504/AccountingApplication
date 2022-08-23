package com.tom.accountingapplication

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class Accounting(
    private val incomeOrExpense: String? = "",
    private val typeChoice: String? = "",
    private val date: String? = "",
    private val typeRemark: String? = "",
    private val fillPrice: String = ""
) {
    fun toDict(): Map<String, *> {
        return mapOf(
            "IncomeOrExpense" to incomeOrExpense,
            "TypeChoice" to typeChoice,
            "Date" to date,
            "TypeRemark" to typeRemark,
            "FillPrice" to fillPrice
        )
    }
}

class Profile(
    val name: String? = "",
    val phone: String? = "",
    val birthday: String? = ""
) {}

class Test(
    val test: String? = ""
) {}

class Type(
    private val breakfast: String? = "",
    private val lunch: String? = "",
    private val dinner: String? = "",
    private val transportation: String? = "",
    private val drink: String = "",
    private val dessert: String = "",
    private val social: String = "",
    private val shopping: String = "",
    private val hospital: String = "",
    private val game: String = "",
    private val gift: String = "",
    private val other: String = "",
) {
    fun toDict(): Map<String, *> {
        return mapOf(
            "Breakfast" to breakfast,
            "Lunch" to lunch,
            "Dinner" to dinner,
            "Transportation" to transportation,
            "Drink" to drink,
            "Dessert" to dessert,
            "Social" to social,
            "Shopping" to shopping,
            "Hospital" to hospital,
            "Game" to game,
            "Gift" to gift,
            "Other" to other,
        )
    }
}
@Parcelize
data class Invest(
    val VirtualCurrencyOrStock: String? = "",
    val BuyOrSell: String? = "",
    val Date: String? = "",
    val TypeRemark: String? = "",
    val FillPrice: String = ""
): Parcelable {
    fun toDict(): Map<String,*> {
        return mapOf(
            "VirtualCurrencyOrStock" to VirtualCurrencyOrStock,
            "BuyOrSell" to BuyOrSell,
            "TypeRemark" to TypeRemark,
            "FillPrice" to FillPrice,
            "Date" to Date,
        )
    }
}