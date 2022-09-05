package com.example.psamiproject

import android.content.Intent
import android.os.Bundle
import android.widget.RadioGroup
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.psamiproject.data.UserActivity
import com.example.psamiproject.data.UserActivityRepo
import com.example.psamiproject.data.UserRepo
import com.example.psamiproject.databinding.ActivityCompleteActivityBinding
import com.example.psamiproject.history.ActivitiesHistory
import java.util.*

class CompleteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCompleteActivityBinding
    private lateinit var radioGroup: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompleteActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        radioGroup = findViewById(R.id.radioGroup)

        binding.sendToDbBtn.setOnClickListener {
            val id = radioGroup.checkedRadioButtonId
            val name = findViewById<RadioButton>(id).text.toString()
            val date = Date()
            val activity = UserActivity("", UserRepo.userId(), name, 0, date.time)
            //UserActivityRepo.addUserActivity(activity) {
                val intent = Intent(this, ExcerciseActivity::class.java)
                intent.putExtra("ACTIVITY", activity)
                startActivity(intent)
            //}
        }
    }
}