package com.example.git_budgetmanagementapp

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.git_budgetmanagementapp.databinding.ExpenseFragmentBinding


class ExpenseFragment : Fragment() {

//    companion object {
//        fun newInstance() = ExpenseFragment()
//    }

    private lateinit var viewModel: MainViewModel
    private lateinit var newTextView: TextView
    private lateinit var binding: ExpenseFragmentBinding
    private lateinit var textViewParams: LinearLayout.LayoutParams

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.expense_fragment, container, false)


        //Initialization of Linear Layout parameter for the new Text views to be added to the main layout
        textViewParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
        textViewParams.gravity = Gravity.CENTER_HORIZONTAL
        textViewParams.setMargins(8,8,8,8)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.let {
            viewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
        }
        viewModel.expenseList.observe(viewLifecycleOwner,
            Observer { list ->

                //Remove all views to avoid duplicate text views every after addition of expense object
                binding.expenseLayout.removeAllViews()
                for (expense in list) {
                    //Create a new text view
                    newTextView = TextView(activity)
                    newTextView.text = expense.expenseString
                    newTextView.id = viewModel.expenseList.value!!.size //Set the index to its place in the array list
                    newTextView.textSize = 24F
                    newTextView.setPadding(8,8,8,8)
                    newTextView.layoutParams = textViewParams


                    binding.expenseLayout.addView(newTextView)
                }
            })


    }
}
