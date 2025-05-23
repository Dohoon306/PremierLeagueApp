package com.example.teamproject

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
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
        binding.tvRank.text = "순위: ${team.rank}"
        binding.tvPoints.text = "승점: ${team.points}"


        val container = binding.llPlayers  // LinearLayout in activity_team_info.xml

        team.players.forEach { player ->
            val tv = TextView(this).apply {
                text = player.name
                textSize = 16f
                setPadding(10, 10, 10, 10)
                setOnClickListener {
                    // 선수 정보 화면으로 이동
                    val intent = Intent(this@TeamInfoActivity, PlayerInfoActivity::class.java)
                    intent.putExtra("name", player.name)
                    intent.putExtra("position", player.position)
                    intent.putExtra("number", player.number)
                    intent.putExtra("team", team.name)
                    intent.putExtra("logo", team.logo)
                    startActivity(intent)
                }
            }
            container.addView(tv)
        }

    }

    // 뒤로가기 화살표 지원
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
