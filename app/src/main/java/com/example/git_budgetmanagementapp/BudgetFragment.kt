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
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.git_budgetmanagementapp.databinding.BudgetFragmentBinding


class BudgetFragment : Fragment() {

    private lateinit var binding: BudgetFragmentBinding

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.budget_fragment, container, false)
        
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //Binding of the fragment to the view model
        activity?.let {
            viewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)

            //binding of the data class to the budget fragment
            //Live data observer for the budget fragment's text views
            viewModel.userdata.observe(viewLifecycleOwner, Observer { newData ->
                binding.userData = newData
            })

        }


        //Listeners for the buttons and text views of the budget fragment
        binding.apply {
            nameText.setOnClickListener { onViewClick(nameText, nameButton, nameEdit) }
            budgetText.setOnClickListener { onViewClick(budgetText, budgetButton, budgetEdit) }

            nameButton.setOnClickListener {
                onButtonClick(nameButton, nameText, nameEdit)
                viewModel.onNameChange(nameEdit.text.toString())
            }
            budgetButton.setOnClickListener{
                onButtonClick(budgetButton, budgetText, budgetEdit)
                viewModel.onBudgetChange(budgetEdit.text.toString())
            }
        }


    }

    private fun onButtonClick(changeButton: Button, changedTV: TextView, editBox: TextView) {
        changeButton.visibility = View.GONE
        changedTV.visibility = View.VISIBLE
        editBox.visibility = View.GONE

//        changedTV.text = editBox.text.toString()**** changed to live data observer
        view?.hideVirtualKB()
        binding.invalidateAll()
    }

    private fun onViewClick(tv_clicked: TextView, hid_button: Button, hid_edit: TextView){
        tv_clicked.visibility = View.GONE
        hid_button.visibility = View.VISIBLE
        hid_edit.visibility = View.VISIBLE

        binding.invalidateAll()
    }

    fun View.hideVirtualKB() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

}
