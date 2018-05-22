package com.fabianofranca.flight.ui.custom

import android.app.Activity
import android.app.DatePickerDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.fabianofranca.flight.infrastructure.hideKeyboard
import java.text.SimpleDateFormat
import java.util.*

class DatePickerControl(
    private val activity: Activity,
    private val editText: EditText,
    private val dateFormat: SimpleDateFormat
) {

    private var picker: DatePickerDialog
    private val date = Calendar.getInstance()

    init {
        picker = DatePickerDialog(
            activity,
            ::dateSet,
            date.get(Calendar.YEAR),
            date.get(Calendar.MONTH),
            date.get(Calendar.DAY_OF_MONTH)
        )

        editText.isFocusableInTouchMode = false

        editText.setOnClickListener {
            activity.hideKeyboard()
            picker.show()
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
                    date.time = dateFormat.parse(s.toString())
                    picker.updateDate(
                        date.get(Calendar.YEAR),
                        date.get(Calendar.MONTH),
                        date.get(Calendar.DAY_OF_MONTH))
                }
            }
        })
    }

    private fun dateSet(view: View, year: Int, month: Int, dayOfMonth: Int) {
        val date = Calendar.getInstance()
        date.set(Calendar.YEAR, year)
        date.set(Calendar.MONTH, month)
        date.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        editText.setText(dateFormat.format(date.time))
    }

    fun setMinDate(date: Calendar) {
        picker.dismiss()

        var year = this.date.get(Calendar.YEAR)
        var month = this.date.get(Calendar.MONTH)
        var dayOfMonth = this.date.get(Calendar.DAY_OF_MONTH)

        if (this.date < date) {
            year = date.get(Calendar.YEAR)
            month = date.get(Calendar.MONTH)
            dayOfMonth = date.get(Calendar.DAY_OF_MONTH)
        }

        picker = DatePickerDialog(activity, ::dateSet, year, month, dayOfMonth)
        picker.datePicker.minDate = date.timeInMillis
    }
}