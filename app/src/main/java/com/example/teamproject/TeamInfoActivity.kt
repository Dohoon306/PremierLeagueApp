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

        // ① 전달받은 팀 ID
        val teamId = intent.getIntExtra("team_id", -1)
        if (teamId == -1) finish()

        // ② JSON에서 팀 찾아오기
        val team = getTeamList(this).first { it.id == teamId }

        // ③ 툴바 제목 설정
        binding.toolbarInfo.title = team.name
        setSupportActionBar(binding.toolbarInfo)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // ④ 헤더(순위·승점) 표시
        binding.tvHeader.text = "순위 ${team.rank}  |  승점 ${team.points}"

        // ⑤ 선수 목록을 하나의 문자열로 구성
        val playersText = buildString {
            team.players.forEachIndexed { idx, p ->
                append("${idx + 1}. ${p.name} (${p.position})  #${p.number}\n")
            }
        }
        binding.tvPlayers.text = playersText.trimEnd()
    }

    // 뒤로가기 화살표 지원
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
