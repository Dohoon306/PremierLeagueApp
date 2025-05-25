package com.example.teamproject


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamproject.databinding.ViewFollowListBinding


class FollowedTeamView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private val binding = ViewFollowListBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        setupFollowedTeamList()
    }

    private fun setupFollowedTeamList() {
        val prefs = context.getSharedPreferences("followed_teams", Context.MODE_PRIVATE)

        // 👀 현재 로그인한 사용자 ID 불러오기
        val userId = context.getSharedPreferences("user_info", Context.MODE_PRIVATE)
            .getString("login_user", "") ?: ""

        // 👀 해당 사용자 기준으로 팔로우한 팀만 필터링
        val followedTeams = getTeamList(context).filter {
            prefs.getBoolean("user_${userId}_team_${it.id}", false)}

        binding.recyclerFollowList.layoutManager = LinearLayoutManager(context)
        binding.recyclerFollowList.adapter = TeamAdapter(followedTeams) { team ->
            val intent = Intent(context, TeamInfoActivity::class.java)
            intent.putExtra("team_id", team.id) // ✅ 필수: 클릭된 팀의 ID 전달
            (context as? Activity)?.startActivity(intent)
        }

    }
}
