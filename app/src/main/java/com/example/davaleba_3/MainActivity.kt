package com.example.davaleba_3

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.switchmaterial.SwitchMaterial


class MainActivity : AppCompatActivity() {

    var switchFlag = false

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val inputNumber: EditText = findViewById(R.id.input_number)
        val convertButton: Button = findViewById(R.id.my_button)
        val resultText: TextView = findViewById(R.id.result)
        val languageSwitch: SwitchMaterial = findViewById(R.id.switch_language)

        languageSwitch.setOnCheckedChangeListener{ buttonView, isChecked ->
            switchFlag = isChecked
            buttonView.text = if (isChecked) "switch to English" else "switch to Georgian"
        }

        convertButton.setOnClickListener{
            var num = inputNumber.text.toString().toIntOrNull()
            if (num != null)
            {
                var result = convertText(num,switchFlag)
                resultText.text = result
            }
            else
            {
                resultText.text = "Enter number from 1 to 1000 !!!"
            }
        }
    }
}

fun convertText(number: Int,switchFlag:Boolean) : String{
    return if (switchFlag)
        convertToGeorgian(number)
    else
        convertToEnglish(number)
}

// ეს მეთოდი არ მუშაობს კარგად
fun convertToGeorgian(number:Int) :String{
    val ones = listOf("", "ერთი", "ორი", "სამი", "ოთხი", "ხუთი", "ექვსი", "შვიდი", "რვა", "ცხრა")
    val tens = listOf("","ათი","ოცდა","ორმოცდა","სამოცდა","ოთხმოცდა")
    val tenToTwenty = listOf("ათი", "თერთმეტი", "თორმეტი", "ცამეტი", "თოთხმეტი", "თხუთმეტი", "თექვსმეტი", "ჩვიდმეტი", "თვრამეტი", "ცხრამეტი")
    val hundreds = listOf("", "ას", "ორას", "სამას", "ოთხას", "ხუთას", "ექვსას", "შვიდას", "რვაას", "ცხრაას")

    if(number == 1000)
        return "ათასი"

    val first = number % 10
    val second = (number % 100) / 10
    val third = number / 100

    if (number % 100 in 10 until 20) {
        val tenToTwentyText = tenToTwenty[number%10]
        return "${hundreds[third]} $tenToTwentyText".trim().replace(" ","")
    }

    val hundredText = hundreds[third]
    val tensText = if(second > 1) tens[second] else if (second == 1) "ათი" else ""
    val onesText = ones[first]

    return listOf(hundredText,tensText,onesText).filter {it.isNotEmpty()}.joinToString(" ").trim()
}

fun convertToEnglish(number:Int) :String{
    val ones = listOf("", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
    val tenToTwenty = listOf("ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen","sixteen", "seventeen", "eighteen", "nineteen")
    val tens = listOf("", "", "twenty", "thirty", "forty", "fifty","sixty", "seventy", "eighty", "ninety")
    val hundreds = listOf("", "one hundred", "two hundred", "three hundred", "four hundred","five hundred", "six hundred", "seven hundred", "eight hundred", "nine hundred")

    if(number == 1000)
        return "thousand"

    val first = number % 10
    val second = (number % 100) / 10
    val third = number / 100

    if (number % 100 in 10 until 20) {
        val tenToTwentyText = tenToTwenty[number % 10]
        return "${hundreds[third]} $tenToTwentyText".trim()
    }

    val hundredText = hundreds[third]
    val tensText = tens[second]
    val onesText = if(first > 0) ones[first] else ""

    return listOf(hundredText,tensText,onesText).filter {it.isNotEmpty()}.joinToString(" ").trim()
}