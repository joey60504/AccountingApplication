package com.tom.accountingapplication

class accounting(val IncomeOrExpense: String? ="",
                 val TypeChoice : String ? = "",
                 val Date: String? ="",
                 val TypeRemark: String? ="",
                 val FillPrice :String=""){
    fun to_dict():Map<String,*>{
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
    val name:String?="",
    val phone:String?="",
    val birthday:String?=""
){}
class test(
    val test:String?=""
){}