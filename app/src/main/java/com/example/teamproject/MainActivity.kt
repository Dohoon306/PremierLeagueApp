package com.example.teamproject

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var searchView: View

    // 순위표 View 재사용을 위해 미리 생성
    private lateinit var rankingView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding 초기화
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 툴바 설정
        setSupportActionBar(binding.toolbarMain)
        supportActionBar?.title = "Premier League 24/25"
        // 순위표 View 생성 및 세팅
        rankingView = layoutInflater.inflate(R.layout.view_ranking, binding.contentFrame, false)
        setupRankingRecycler(rankingView)

        // 탭 3개 추가
        val tabTitles = arrayOf("순위", "선수 검색", "팔로잉")
        tabTitles.forEach { title ->
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(title))
        }

        // 기본 탭 0번(순위) 표시
        binding.contentFrame.removeAllViews()
        binding.contentFrame.addView(rankingView)

        // 탭 선택 이벤트 리스너 설정
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                showTab(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        //2번째 탭 생성코드

        searchView = layoutInflater.inflate(R.layout.view_search_player, binding.contentFrame, false)

        val editSearch = searchView.findViewById<EditText>(R.id.editSearch)
        val rvResult = searchView.findViewById<RecyclerView>(R.id.rvSearchResult)

        editSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val keyword = editSearch.text.toString().trim()
                val teams = getTeamList(this)
                val matched = mutableListOf<Triple<String, String, Player>>() // teamName, logo, player

                teams.forEach { team ->
                    team.players.filter { it.name.contains(keyword, ignoreCase = true) }
                        .forEach { player ->
                            matched.add(Triple(team.name, team.logo, player))
                        }
                }

                rvResult.layoutManager = LinearLayoutManager(this)
                rvResult.adapter = SearchPlayerAdapter(matched) { (teamName, logo, player) ->
                    val intent = Intent(this, PlayerInfoActivity::class.java)
                    intent.putExtra("name", player.name)
                    intent.putExtra("position", player.position)
                    intent.putExtra("number", player.number)
                    intent.putExtra("team", teamName)
                    intent.putExtra("logo", logo)
                    startActivity(intent)
                }

                true
            } else false
        }

    }

    private fun showTab(position: Int) {
        binding.contentFrame.removeAllViews()
        when (position) {
            0 -> binding.contentFrame.addView(rankingView)
            1 -> binding.contentFrame.addView(searchView)
            2 -> {
                val textView = TextView(this).apply {
                    text = "콘텐츠 준비 중"
                    textSize = 18f
                    gravity = Gravity.CENTER
                    layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                    )
                }
                binding.contentFrame.addView(textView)
            }
        }
    }



    private fun setupRankingRecycler(root: View) {
        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerViewRank)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TeamAdapter(getTeamList(this))
    }
}
