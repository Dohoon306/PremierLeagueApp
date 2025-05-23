package com.example.teamproject

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.teamproject.databinding.ActivityPlayerInfoBinding

class PlayerInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🟦 툴바 설정
        setSupportActionBar(binding.toolbarPlayer)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 🟦 선수 정보 출력
        val name = intent.getStringExtra("name")
        val pos = intent.getStringExtra("position")
        val num = intent.getIntExtra("number", -1)
        val team = intent.getStringExtra("team")
        val logo = intent.getStringExtra("logo")
        val resId = resources.getIdentifier(logo, "drawable", packageName)
        //팀 로고 설정
        binding.imgTeamLogo.setImageResource(resId)

        supportActionBar?.title = name

        binding.toolbarPlayer.title = intent.getStringExtra("name") ?: "선수 정보"
        binding.tvPosition.text = "포지션: ${intent.getStringExtra("position") ?: "?"}"
        binding.tvNumber.text = "등번호: ${intent.getIntExtra("number", -1)}"
        binding.tvTeam.text = "소속팀: ${intent.getStringExtra("team") ?: "?"}"



    }

    // 🟦 뒤로가기 버튼 동작
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

