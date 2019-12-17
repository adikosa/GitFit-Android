package com.gitfit.android.ui.home.journal.editactivity


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.gitfit.android.AppConstants.Companion.ACTIVITY_COFFEE
import com.gitfit.android.AppConstants.Companion.ACTIVITY_GAME_CONSOLE_BREAK
import com.gitfit.android.AppConstants.Companion.ACTIVITY_TABLE_TENNIS
import com.gitfit.android.R
import com.gitfit.android.databinding.FragmentDialogEditActivityBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.fragment_dialog_edit_activity.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class EditActivityDialogFragment : DialogFragment(), EditActivityNavigator {
    private val viewModel: EditActivityViewModel by viewModel { parametersOf(arguments!!.getLong("activityId")) }

    private val activities by lazy {
        arrayOf(
            getString(R.string.activity_console_game),
            getString(R.string.activity_drinking_coffee),
            getString(R.string.activity_table_tennis)
        )
    }

    private val sortedActivities by lazy { activities.sortedArray() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setNavigator(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentDialogEditActivityBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_dialog_edit_activity, container, false
            )
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setActivityTypesMenu()
        setListeners()
    }

    private fun setActivityTypesMenu() {
        val activityTypeResId = when (viewModel.activity.type) {
            ACTIVITY_COFFEE -> R.string.activity_drinking_coffee
            ACTIVITY_TABLE_TENNIS -> R.string.activity_table_tennis
            ACTIVITY_GAME_CONSOLE_BREAK -> R.string.activity_console_game
            else -> throw Exception("Unknown activity!")
        }

        filled_exposed_dropdown.setText(activityTypeResId)

        val adapter = ArrayAdapter(
            context!!,
            R.layout.dropdown_menu_popup_item,
            sortedActivities
        )

        filled_exposed_dropdown.setAdapter(adapter)
    }

    private fun setListeners() {
        filled_exposed_dropdown.setOnItemClickListener { _, _, position, _ ->
            viewModel.activityType.value = when (sortedActivities[position]) {
                activities[0] -> ACTIVITY_GAME_CONSOLE_BREAK
                activities[1] -> ACTIVITY_COFFEE
                activities[2] -> ACTIVITY_TABLE_TENNIS
                else -> throw Exception("Unknown activity selected!")
            }
        }

        date_edit_text.setOnFocusChangeListener { _, hasFocus ->
            viewModel.onDateLayoutClick(hasFocus)
        }

        time_edit_text.setOnFocusChangeListener { _, hasFocus ->
            viewModel.onTimeLayoutClick(hasFocus)
        }
    }

    override fun showDatePicker(year: Int, month: Int, dayOfMonth: Int) {
        val datePickerDialog = DatePickerDialog(
            context!!,
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                viewModel.saveSelectedDate(selectedYear, selectedMonth, selectedDayOfMonth)
            },
            year,
            month,
            dayOfMonth
        )
        datePickerDialog.show()
    }

    override fun showTimePicker(hour: Int, minute: Int) {
        val timePickerDialog = TimePickerDialog(
            context!!,
            TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
                viewModel.saveSelectedTime(selectedHour, selectedMinute)
            },
            hour,
            minute,
            true
        )
        timePickerDialog.show()
    }

    override fun closeDialog() {
        dismiss()
    }

    override fun showDeleteDialog() {
        MaterialAlertDialogBuilder(context)
            .setMessage(R.string.delete_dialog_message)
            .setPositiveButton(R.string.yes_button) { _, _ -> viewModel.deleteActivity() }
            .setNegativeButton(R.string.no_button) { dialog, _ -> dialog.dismiss() }
            .show()
    }
}