# Exercise 6 - ViewModel, ViewModelFactory, LiveData and LiveData observers\
---
AUTHOR: [___JOHN KARL TUMPALAN___](https://github.com/JKBTumpalan)

## Set up gradle files

Configure the app level configuration of the gradle files. Add implementations for the fragments and their lifecycles, View Model , and also data binding.

For the __View Model__ : `implementation "androidx.lifecycle:lifecycle-extensions:2.0.0"`

For the __Data Binding__ : ` dataBinding { enabled = true }`

## Create the Layout for the fragments to be used.

I created 3 fragment layouts:

* ___budget_fragment.xml___\
contains the views for the name and initial budget of the user.
    * `Edit Texts, TextViews, Buttons`
    * `Root: Constraint Layout`

* ___form_fragment.xml___\
contains the forms for the expense data that will be filled up by the user.
	* `Edit Texts, TextViews, Buttons`
	* `Root: Constraint Layout`

* ___expense_fragment.xml___\
contains the list of all the expenses that were added by the user.
	* `TextViews`
	* `Root: Linear Layout`

> Remember to enclose your layout files to `<layout>` tags for data binding to the fragments.\

> These fragments will be added to the activity main layout via your specific view groups and containers.
> In my case, I created a `ScrollView` root with a `Linear Layout` child to house the `FrameLayout` that contains the fragments.

## Create the Kotlin Fragment Classes

* ___BudgetFragment___\
contains the logic for creating click listeners for manipulating visibility of the buttons and textviews in the budget layout fragment. The edit texts for name and budget send the user name and budget to create a ___userdata___ object that is being observed by the two text views.

* ___FormFragment___\
sends the data to the view model to create an ___expensedata___ object.

* ___ExpenseFragment___\
contains the observers to the live data of the array list of ___expensedata___ object.

## Create data classes for the user data and expense data

I created two data classes for this exercise.

* ___UserData___ :
```
data class UserData(var name: String = "Your Name", var budget: String = "0.0")
```
* ___ExpenseData___ :
``` 
data class ExpenseData(var name: String = "", var type: String="Others", var cost: Number = 0.0){
    val expenseString = "$name - $type - PHP $cost" //Creates the string to be displayed on the linear layout
} 
```

## Create the ViewModel

Since the View Model will be shared by the 3 fragments, we don't need to use the _factory method_ in creating view model classes.\
<br/>
The View model contains all the data to be referenced by the 3 UI controllers. View models are only destroyed in the OnDestroy() part of the activity lifecycle, thus, the data will only be lost once the activity or app's memory has been dumped by the android system.\
<br/>
We need the view model for our data to survive configuration changes.
```
class MainViewModel: ViewModel() {

    //Initialization of the initial User Data - Budget Fragment

    //Initialization of the initial Expense Data - Form Fragment

    //Live Data for the User Data - Budget Fragment

    //Live data for the array of expenses

    //Expense variable


    init {
        //initialization of userdata live data

        //initializeation of array list live data

        //Checking of active observers
    }

    //Functions for text view changes in the Budget Fragment

    //Function for adding expense objects to the array list
}
```

---
---
---
# Notes

## Bind the Fragments and inflate their layouts using databinding

In the onCreateView(), inflate the layouts using ` DataBindingUtil.inflate(inflater, layout_fragment.xml, container, false)` and returning `binding.root`.\
<br/>

In case you'll be referencing a text view's value to the data classes, make sure to create data element inside the xml file.
```
<data>
	<variable
		name = "_varName_"
		type = "_dataclasspath_"
	/>
</data>
```

## Connect to the shared view model by instantiating it inside onActivityCreated()

```
        activity?.let {
            viewModel = ViewModelProviders.of(it).get(MainViewModel::class.java)
        }
```

We used `it` instead of `this` to refer to the fragment activity instead of the fragment itself.

## Creating Text Views to be added in the Linear Layout

```
	private lateinit var textViewParams: LinearLayout.LayoutParams


	//Setting the Linear layout parameters

	textViewParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
	textViewParams.gravity = Gravity.CENTER_HORIZONTAL
	textViewParams.setMargins(8,8,8,8)


	//Creating the text view and applying the parameters to it
	newTextView = TextView(activity)
	newTextView.text = expense.expenseString
	newTextView.id = viewModel.expenseList.value!!.size //Set the index to its place in the array list
	newTextView.textSize = 24F
	newTextView.setPadding(8,8,8,8)
	newTextView.layoutParams = textViewParams

```


## Hiding the soft input keyboard
```
    fun View.hideVirtualKB() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
```
