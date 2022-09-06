package com.example.psamiproject.history

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.psamiproject.CompleteActivity
import com.example.psamiproject.data.Point
import com.example.psamiproject.data.UserActivityRepo
import com.example.psamiproject.data.UserRepo
import com.example.psamiproject.data.PointRepo
import com.example.psamiproject.data.UserRepo.userEmail
import com.example.psamiproject.databinding.ActivityActivitiesHistoryBinding

class ActivitiesHistory : AppCompatActivity() {

    private lateinit var binding: ActivityActivitiesHistoryBinding
    private val adapter by lazy { HistoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActivitiesHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.emailTextView.text = "Witaj " + userEmail()
        PointRepo.getUserPoints(UserRepo.userId()) {
            if(it != "-1") {
                binding.pointsTextView.text = "Punkty = " + it
            }
            else
            {
                PointRepo.addUserPoint(Point(0), UserRepo.userId()) {
                    binding.pointsTextView.text = "Punkty = " + it
                }
            }
        }
        binding.listView.adapter = adapter

        UserActivityRepo.getUserActivities(UserRepo.userId()) {
            adapter.submitList(it)
        }


        binding.addBtn.setOnClickListener {
            val intent = Intent(this, CompleteActivity::class.java)
            startActivity(intent)
        }
    }
}