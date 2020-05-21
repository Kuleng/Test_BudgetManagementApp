package com.example.git_budgetmanagementapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        supportFragmentManager.beginTransaction().add(R.id.budget_fragment, BudgetFragment()).commit()
//        supportFragmentManager.beginTransaction().add(R.id.form_fragment, FormFragment()).commit()
//        supportFragmentManager.beginTransaction().add(R.id.expense_fragment, ExpenseFragment()).commit()

//        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }
}
