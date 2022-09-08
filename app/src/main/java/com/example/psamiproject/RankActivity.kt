package com.example.psamiproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.psamiproject.data.PointRepo
import com.example.psamiproject.databinding.ActivityRankBinding
import com.example.psamiproject.history.ActivitiesHistory

class RankActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRankBinding
    private val adapter by lazy { RankAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRankBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.leaderboardView.adapter = adapter

        PointRepo.getAllUsersPoint {
            adapter.submitList(it)
        }

        binding.backButton.setOnClickListener {
            val intent = Intent(this, ActivitiesHistory::class.java)
            startActivity(intent)
        }
    }
}