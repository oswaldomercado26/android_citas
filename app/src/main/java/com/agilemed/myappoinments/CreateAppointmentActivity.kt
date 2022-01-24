package com.agilemed.myappoinments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_create_appointment.*
import kotlinx.android.synthetic.main.card_view_step_one.*
import kotlinx.android.synthetic.main.card_view_step_three.*
import kotlinx.android.synthetic.main.card_view_step_two.*
import java.util.*

class CreateAppointmentActivity : AppCompatActivity() {
    private var calendar =Calendar.getInstance()
    private var selectedTimeRadioBtn: RadioButton? = null

 override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_appointment)

     btnNext.setOnClickListener {
         if (etDescription.text.toString().length < 3) {
             etDescription.error = getString(R.string.validate_appointment_description)
         } else {
             // continue to step 2
             cvStep1.visibility = View.GONE
             cvStep2.visibility = View.VISIBLE
         }
     }

     btnNext2.setOnClickListener {
         when {
             etScheduledDate.text.toString().isEmpty() ->
                 etScheduledDate.error = getString(R.string.validate_appointment_date)

             selectedTimeRadioBtn == null ->
                 Snackbar.make(createAppointmentLinearLayout,
                     R.string.validate_appointment_time, Snackbar.LENGTH_SHORT).show()

             else -> {
                 // continue to step 3
                 //showAppointmentDataToConfirm()
                 cvStep2.visibility = View.GONE
                 cvStep3.visibility = View.VISIBLE
             }
         }
     }

        btnConfirmAppointment.setOnClickListener {
            Toast.makeText(this,"Cita registrar correctamente", Toast.LENGTH_SHORT).show()
        }
        val specialtyOptions= arrayOf("esciacialidad a","esciacialidad b")
        spinnerSpecialties.adapter=ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,specialtyOptions)

        val doctorOptions= arrayOf("doctor a","doctor b")
        spinnerDoctors.adapter=ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,doctorOptions)
    }


    fun onClickScheduleDate(v: View?){

        val year =calendar.get(Calendar.YEAR)
        val month= calendar.get(Calendar.MONTH)
        val dayOfMonth=calendar.get(Calendar.DAY_OF_MONTH)

        val listener=DatePickerDialog.OnDateSetListener { datePicker, y, m, d ->
           // Toast.makeText(this,"$y-$m-$d",Toast.LENGTH_SHORT).show()
           calendar.set(y,m,d)
            etScheduledDate.setText(
                resources.getString(
                    R.string.date_format,
                    y,
                    (m+1).twoDigits(),
                    d.twoDigits()
                )
            )
            displayRadioButtons()
        }

        val datePickerDialog = DatePickerDialog(this, listener, year, month, dayOfMonth)
        // set limits
        val datePicker = datePickerDialog.datePicker
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        datePicker.minDate = calendar.timeInMillis // +1
        calendar.add(Calendar.DAY_OF_MONTH, 29)
        datePicker.maxDate = calendar.timeInMillis // +30

        // show dialog
        datePickerDialog.show()
    }


    private fun displayRadioButtons(){
        selectedTimeRadioBtn = null
        radioGroupLeft.removeAllViews()
        radioGroupRight.removeAllViews()

        val hours = arrayOf("3:00 PM","3:30 PM","4:00 PM","4:30 PM")
        var goToLeft = true
        hours.forEach {
            val radioButton = RadioButton(this)
            radioButton.id = View.generateViewId()
            radioButton.text = it

            radioButton.setOnClickListener { view ->
                selectedTimeRadioBtn?.isChecked = false

                selectedTimeRadioBtn = view as RadioButton?
                selectedTimeRadioBtn?.isChecked = true
            }

            if (goToLeft)
                radioGroupLeft.addView(radioButton)
            else
                radioGroupRight.addView(radioButton)
            goToLeft = !goToLeft
        }
    }

    private fun Int.twoDigits()
            = if (this>=10) this.toString() else "0$this"

    override fun onBackPressed() {

        when {
            cvStep3.visibility == View.VISIBLE -> {
                cvStep3.visibility = View.GONE
                cvStep2.visibility = View.VISIBLE

            }
            cvStep2.visibility == View.VISIBLE -> {
                cvStep2.visibility = View.GONE
                cvStep1.visibility = View.VISIBLE

            }
            cvStep1.visibility == View.VISIBLE -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle(getString(R.string.dialog_create_appointment_exit_title))
                builder.setMessage(getString(R.string.dialog_create_appointment_exit_message))
                builder.setPositiveButton(getString(R.string.dialog_create_appointment_exit_positive_btn)) { _, _ ->
                    finish()
                }
                builder.setNegativeButton(getString(R.string.dialog_create_appointment_exit_negative_btn)) { dialog, _ ->
                    dialog.dismiss()
                }

                val dialog = builder.create()
                dialog.show()
            }
        }

    }
}