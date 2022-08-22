package com.tom.accountingapplication

class accounting(
    val IncomeOrExpense: String? = "",
    val TypeChoice: String? = "",
    val Date: String? = "",
    val TypeRemark: String? = "",
    val FillPrice: String = ""
) {
    fun to_dict(): Map<String, *> {
        return mapOf(
            "IncomeOrExpense" to IncomeOrExpense,
            "TypeChoice" to TypeChoice,
            "Date" to Date,
            "TypeRemark" to TypeRemark,
            "FillPrice" to FillPrice
        )
    }
}

class profile(
    val name: String? = "",
    val phone: String? = "",
    val birthday: String? = ""
) {}

class test(
    val test: String? = ""
) {}

class Type(
    val Breakfast: String? = "",
    val Lunch: String? = "",
    val Dinner: String? = "",
    val Transportation: String? = "",
    val Drink: String = "",
    val Dessert: String = "",
    val Social: String = "",
    val Shopping: String = "",
    val Hospital: String = "",
    val Game: String = "",
    val Gift: String = "",
    val Other: String = "",
) {
    fun to_dict(): Map<String, *> {
        return mapOf(
            "Breakfast" to Breakfast,
            "Lunch" to Lunch,
            "Dinner" to Dinner,
            "Transportation" to Transportation,
            "Drink" to Drink,
            "Dessert" to Dessert,
            "Social" to Social,
            "Shopping" to Shopping,
            "Hospital" to Hospital,
            "Game" to Game,
            "Gift" to Gift,
            "Other" to Other,
        )
    }
}

class invest(
    val VirtualCurrencyOrStock: String? = "",
    val BuyOrSell: String? = "",
    val TypeRemark: String? = "",
    val FillPrice: String = ""
) {
    fun to_dict(): Map<String,*> {
        return mapOf(
            "VirtualCurrencyOrStock" to VirtualCurrencyOrStock,
            "BuyOrSell" to BuyOrSell,
            "TypeRemark" to TypeRemark,
            "FillPrice" to FillPrice
        )
    }
}