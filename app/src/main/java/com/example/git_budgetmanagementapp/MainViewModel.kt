package com.example.git_budgetmanagementapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    //Initialization of the initial User Data - Budget Fragment
    private val dataHolder = UserData()

    //Initialization of the initial Expense Data - Form Fragment
    var newExpense = ExpenseData()

    //Live Data for the User Data - Budget Fragment
    private val _userdata = MutableLiveData<UserData>()
    val userdata: LiveData<UserData>
        get() = _userdata


    //Live data for the array of expenses
    private val _expenseList = MutableLiveData<ArrayList<ExpenseData>>()
    val expenseList: LiveData<ArrayList<ExpenseData>>
        get() = _expenseList

    private var expense = 0F

    init {
        //initialization of userdata live data
        _userdata.value = dataHolder
        _expenseList.value = ArrayList<ExpenseData>()

        Log.i("MainViewModel", "USER DATA: ${userdata.hasActiveObservers()}")
    }

    //Functions for text view changes - Budget Fragment
    fun onNameChange(newName: String) {
        _userdata.value?.name = newName
    }

    fun onBudgetChange(newBudget: String){
        _userdata.value?.budget = newBudget
    }

    fun onAddExpense(name: String, type:String, cost:Number){
        newExpense = ExpenseData(name, type, cost)                  //new Expense object
        expense = expense.plus(newExpense.cost.toFloat())           //update expense variable to the cost of the new expense object
        _expenseList.value?.add(newExpense)                         //Add expense object to the array list
        _expenseList.value = _expenseList.value                     //Notify array list observer
        _userdata.value?.budget = getBudget().toString()            //Update user budget based on the newly added expense
        expense = 0F                                                //reset expense for preparation to the future addition of expense objects

        _userdata.value = _userdata.value                           //notify user data observer

        //Logging
        Log.i("MainViewModel", "New budget: ${_userdata.value?.budget}")
        for (expense in _expenseList.value!!) {
            Log.i("MainViewModel", expense.expenseString)
        }
    }

    fun getBudget(): Float{
        Log.i("MainViewModel", "Budget: ${_userdata.value?.budget}, Latest Expense: ${expense}")
        return (_userdata.value?.budget?.toFloat()!! - (expense))
    }
}