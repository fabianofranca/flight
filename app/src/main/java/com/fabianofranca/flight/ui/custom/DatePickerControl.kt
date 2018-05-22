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
    activity: Activity,
    editText: EditText,
    private val dateFormat: SimpleDateFormat
) {

    private val picker: DatePickerDialog
    private val today = Calendar.getInstance()
    private var year = today.get(Calendar.YEAR)
    private var month = today.get(Calendar.MONTH)
    private var dayOfMonth = today.get(Calendar.DAY_OF_MONTH)

    init {

        val dateSet: (View, Int, Int, Int) -> Unit = { _, year, month, dayOfMonth ->
            val date = Calendar.getInstance()
            date.set(Calendar.YEAR, year)
            date.set(Calendar.MONTH, month)
            date.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            editText.setText(dateFormat.format(date.time))
        }

        picker = DatePickerDialog(activity, dateSet, year, month, dayOfMonth)

        editText.isFocusableInTouchMode = false

        editText.setOnClickListener {
            picker.updateDate(year, month, dayOfMonth)
            activity.hideKeyboard()
            picker.show()
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrBlank()) {
                    val date = Calendar.getInstance()
                    date.time = dateFormat.parse(s.toString())

                    year = date.get(Calendar.YEAR)
                    month = date.get(Calendar.MONTH)
                    dayOfMonth = date.get(Calendar.DAY_OF_MONTH)
                }
            }
        })
    }
}