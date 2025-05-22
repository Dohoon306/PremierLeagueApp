package com.example.teamproject

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.teamproject.databinding.ActivitySearchPlayerBinding

class SearchPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 툴바 설정
        setSupportActionBar(binding.toolbarSearch)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "선수 검색"

        // 검색 실행
        binding.editSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchPlayer(binding.editSearch.text.toString().trim())
                true
            } else {
                false
            }
        }
    }

    private fun searchPlayer(keyword: String) {
        if (keyword.isEmpty()) {
            Toast.makeText(this, "검색어를 입력하세요", Toast.LENGTH_SHORT).show()
            return
        }

        val teams = getTeamList(this)
        val result = buildString {
            for (team in teams) {
                val matched = team.players.filter { it.name.contains(keyword, ignoreCase = true) }
                for (player in matched) {
                    append("${player.name} (${player.position})  #${player.number} - ${team.name}\n")
                }
            }
        }

        binding.tvResult.text = if (result.isEmpty()) "일치하는 선수가 없습니다." else result.trim()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}