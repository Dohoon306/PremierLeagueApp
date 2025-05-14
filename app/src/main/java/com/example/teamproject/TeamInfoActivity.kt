package com.example.teamproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.teamproject.databinding.ActivityTeamInfoBinding

class TeamInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTeamInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // MainActivity → team_name 전달
        val teamName = intent.getStringExtra("team_name") ?: "Unknown Team"

        // 툴바 제목 및 본문 텍스트에 팀 이름 표시
        binding.toolbarInfo.title = teamName
        binding.tvTeamName.text  = teamName
    }
}