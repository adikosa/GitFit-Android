package com.gitfit.android.ui.home.home.addActivity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.gitfit.android.AppConstants.Companion.ACTIVITY_COFFEE
import com.gitfit.android.AppConstants.Companion.ACTIVITY_GAME_CONSOLE_BREAK
import com.gitfit.android.AppConstants.Companion.ACTIVITY_TABLE_TENNIS
import com.gitfit.android.R
import com.gitfit.android.databinding.FragmentDialogAddActivityBinding
import kotlinx.android.synthetic.main.fragment_dialog_add_activity.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddActivityDialogFragment : DialogFragment(), AddActivityDialogNavigator {

    private val addActivityViewModel: AddActivityViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addActivityViewModel.setNavigator(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDialogAddActivityBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_dialog_add_activity, container, false
            )
        binding.viewModel = addActivityViewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setActivityTypesDropDown()
        setListeners()
    }

    private fun setActivityTypesDropDown() {
        val activityTypes = arrayOf(
            resources.getString(R.string.activity_drinking_coffee),
            resources.getString(R.string.activity_table_tennis),
            resources.getString(R.string.activity_console_game)
        ).sortedArray()

        val adapter =
            ArrayAdapter(
                context!!,
                R.layout.dropdown_menu_popup_item,
                activityTypes
            )

        mActivityTypeDropDown.setAdapter(adapter)
    }

    private fun setListeners() {
        mActivityTypeDropDown.setOnItemClickListener { adapterView, _, position, _ ->
            val item = adapterView.getItemAtPosition(position) as String

            addActivityViewModel.activityType.postValue(when(item) {
                resources.getString(R.string.activity_drinking_coffee) -> ACTIVITY_COFFEE
                resources.getString(R.string.activity_table_tennis) -> ACTIVITY_TABLE_TENNIS
                resources.getString(R.string.activity_console_game) -> ACTIVITY_GAME_CONSOLE_BREAK
                else -> throw Exception("Unknown activity selected!")
            })
        }

        mDateEditText.setOnFocusChangeListener { _, hasFocus ->
            addActivityViewModel.onDateEditTextClick(hasFocus)
        }

        mTimeEditText.setOnFocusChangeListener { _, hasFocus ->
            addActivityViewModel.onTimeEditTextClick(hasFocus)
        }
    }

    override fun closeDialog() {
        dismissDialog()
        findNavController().navigate(R.id.action_navigation_add_activity_to_navigation_home)
    }

    override fun dismissDialog() {
        dismiss()
    }

    override fun showDatePicker() {
        val dateTime = addActivityViewModel.dateTime.value!!
        val datePickerDialog = DatePickerDialog(
            context!!,
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                addActivityViewModel.saveSelectedDate(selectedYear, selectedMonth, selectedDayOfMonth)
            },
            dateTime.year,
            dateTime.monthValue - 1,
            dateTime.dayOfMonth
        )
        datePickerDialog.show()
    }

    override fun showTimePicker() {
        val dateTime = addActivityViewModel.dateTime.value!!
        val timePickerDialog = TimePickerDialog(
            context!!,
            TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
                addActivityViewModel.saveSelectedTime(selectedHour, selectedMinute)
            },
            dateTime.hour,
            dateTime.minute,
            true
        )
        timePickerDialog.show()
    }
}