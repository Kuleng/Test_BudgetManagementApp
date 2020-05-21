package com.example.git_budgetmanagementapp

data class ExpenseData(var name: String = "", var type: String="Others", var cost: Number = 0.0){
    val expenseString = "$name - $type - PHP $cost"
}