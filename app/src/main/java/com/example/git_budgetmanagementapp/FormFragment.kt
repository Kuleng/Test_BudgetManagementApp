package com.example.git_budgetmanagementapp

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.git_budgetmanagementapp.databinding.FormFragmentBinding


class FormFragment : Fragment() {

    private lateinit var binding: FormFragmentBinding

//    companion object {
//        fun newInstance() = FormFragment()
//    }

    private lateinit var viewModel: MainViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.form_fragment, container, false)
//        return inflater.inflate(R.layout.form_fragment, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            viewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
        }

        binding.apply {
            addExpenseBtn.setOnClickListener {
                if(checkForms()) {
                    //Create an expense object in the viewmodel

                    viewModel.onAddExpense(
                        expenseNameEdit.text.toString(),
                        expenseTypeEdit.text.toString(),
                        expenseCostEdit.text.toString().toFloat()
                    )

                    view?.hideVirtualKB()
                    expenseNameEdit.setText("")
                    expenseTypeEdit.setText("")
                    expenseCostEdit.setText("")
                }
            }
        }
    }

    private fun checkForms(): Boolean{
        binding.apply {
            if (expenseNameEdit.text.toString() == ""){
                Toast.makeText(activity, "Expense name cannot be empty", Toast.LENGTH_SHORT).show()
                return false
            } else if (expenseTypeEdit.text.toString() == ""){
                Toast.makeText(activity, "Expense type cannot be empty", Toast.LENGTH_SHORT).show()
                return false
            } else if (expenseCostEdit.text.toString() == ""){
                Toast.makeText(activity, "Expense cost cannot be empty", Toast.LENGTH_SHORT).show()
                return false
            } else {

                if(viewModel.getBudget() < 0 || viewModel.getBudget() < expenseCostEdit.text.toString().toFloat()){
                    Toast.makeText(activity, "Budget not enough for additional expense", Toast.LENGTH_SHORT).show()
                    return false
                }
                else
                    return true
            }
        }
    }

    fun View.hideVirtualKB() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

}
