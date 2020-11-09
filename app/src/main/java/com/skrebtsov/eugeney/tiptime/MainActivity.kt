package com.skrebtsov.eugeney.tiptime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.skrebtsov.eugeney.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calculateButton.setOnClickListener {
            calculateTip()
        }
        binding.costOfServiceEditText.setOnKeyListener{ view, keyCode, _ -> handleKeyEvent(view, keyCode) }
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
    if (keyCode == KeyEvent.KEYCODE_ENTER){
        calculateTip()
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        return true
    }
        return false
    }

    private fun calculateTip() {
        val cost = binding.costOfServiceEditText.text.toString().toDoubleOrNull()
        if (cost == null) {
            binding.tipResult.text = ""
            return
        }
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_eighteen_percent -> 0.18
            R.id.option_fifteen_percent -> 0.15
            else -> 0.20
        }
        var tip = tipPercentage * cost
        if (binding.roundUpSwitch.isChecked) {
            tip = ceil(tip)
        }
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}