package ar.com.unlam.enlazar.ui.pickers

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import ar.com.unlam.enlazar.R
import java.util.*

class DatePickerFragent(val listener:(day:Int,month:Int,year:Int)->Unit):DialogFragment(),
    DatePickerDialog.OnDateSetListener {
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        listener(day, month, year)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c: Calendar =Calendar.getInstance()
        val day=c.get(Calendar.DAY_OF_MONTH)
        val month= c.get(Calendar.MONTH)
        val year=c.get(Calendar.YEAR)
        val picker= DatePickerDialog(activity as Context,
            R.style.datePickerTheme,this,year,month,day)
        picker.datePicker.minDate=c.timeInMillis
        c.add(Calendar.MONTH, +1)
        return picker
    }
}