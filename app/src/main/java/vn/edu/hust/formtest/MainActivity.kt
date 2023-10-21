package vn.edu.hust.formtest

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import android.widget.Toast.makeText
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_no_constraint);
        val BirthdaySelect = findViewById(R.id.selectBirthday) as Button
        val Register = findViewById(R.id.register) as Button
        val first: EditText = findViewById(R.id.editFirstName) as EditText
        val last: EditText = findViewById(R.id.editLastName) as EditText
        val birth: EditText = findViewById(R.id.editBirthday) as EditText
        val addr: EditText = findViewById(R.id.editAddress) as EditText
        val emai: EditText = findViewById(R.id.editEmail) as EditText
        val sex: RadioGroup = findViewById(R.id.genderGroup) as RadioGroup
        val agree: CheckBox = findViewById(R.id.agree) as CheckBox
        var toast1: Toast

        first.setOnClickListener {
            first.setText("");
        }
        last.setOnClickListener {
            last.setText("");
        }
        birth.setOnClickListener {
            birth.setText("");
        }
        addr.setOnClickListener {
            addr.setText("");
        }
        emai.setOnClickListener {
            emai.setText("");
        }

        Register.setOnClickListener{

          if (isFilled(first, last, birth, addr, emai, sex, agree))
          {
              toast1 = makeText(this, getString(R.string.yes), Toast.LENGTH_SHORT)
              toast1.show()
              Register.setBackgroundColor(Color.CYAN)
          }
            else{
              toast1 = makeText(this, getString(R.string.no), Toast.LENGTH_SHORT)
              toast1.show()
              Register.setBackgroundColor(Color.RED)
          }
        }
        BirthdaySelect.setOnClickListener {
            val sdf = SimpleDateFormat("'Date\n'dd-MM-yyyy '\n\nand\n\nTime\n'HH:mm:ss z")
            val currentDateAndTime = sdf.format(Date())
            birth.setText(currentDateAndTime)
        }
    }
    private fun isFilled(first: EditText, last: EditText, birth: EditText, addr: EditText, emai: EditText, sex: RadioGroup, agree: CheckBox): Boolean {
        if (sex.checkedRadioButtonId == -1) return false
        if (first.text.toString().isEmpty()) return false
        if (last.text.toString().isEmpty()) return false
        if (addr.text.toString().isEmpty()) return false
        if (emai.text.toString().isEmpty()) return false
        if (birth.text.toString().isEmpty()) return false
        if (!agree.isChecked) return false
        return true
    }
}