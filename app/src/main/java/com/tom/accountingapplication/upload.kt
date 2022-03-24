package com.tom.accountingapplication

class accounting(val IncomeOrExpense: String? ="",
                 val Date: String? ="",
                 val TypeChoice: String? ="",
                 val FillPrice :String=""){
    fun to_dict():Map<String,*>{
        return mapOf(
            "IncomeOrExpense" to IncomeOrExpense,
            "Date" to Date,
            "TypeChoice" to TypeChoice,
            "FillPrice" to FillPrice
        )
    }
}

class profile(
    val name:String?="",
    val phone:String?="",
    val birthday:String?=""
){}