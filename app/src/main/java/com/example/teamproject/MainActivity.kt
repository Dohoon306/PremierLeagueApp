package com.example.teamproject

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teamproject.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var searchView: View
    private lateinit var toggle: ActionBarDrawerToggle

    // 순위표 View 재사용을 위해 미리 생성
    private lateinit var rankingView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // ViewBinding 초기화
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //로그인 사용자 표시
        val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val loginUser = prefs.getString("login_user", null)

        val menu = binding.navigationView.menu
        val userItem = menu.findItem(R.id.menu_user)
        val logoutItem = menu.findItem(R.id.menu_logout) // 로그아웃 메뉴 항목

        if (!loginUser.isNullOrBlank()) {
            userItem.title = loginUser
            logoutItem.isVisible = true  // 로그인 상태면 로그아웃 보임
        } else {
            userItem.title = "로그인"
            logoutItem.isVisible = false // 로그인 전이면 로그아웃 숨김
        }


        // 툴바 설정
        setSupportActionBar(binding.toolbarMain)

        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbarMain,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()



        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_user -> {
                    val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
                    val currentUser = prefs.getString("login_user", null)

                    if (currentUser == null) {
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                }

                R.id.menu_logout -> {
                    val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)
                    val currentUser = prefs.getString("login_user", null)

                    if (currentUser != null) {
                        prefs.edit().remove("login_user").apply()
                        Toast.makeText(this, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()
                        recreate()
                    } else {
                        Toast.makeText(this, "현재 로그인 상태가 아닙니다", Toast.LENGTH_SHORT).show()
                    }
                }

                R.id.menu_settings -> {
                    // 설정 등 필요 시 추가
                }
            }
            binding.drawerLayout.closeDrawers()
            true
        }



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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showTab(position: Int) {
        binding.contentFrame.removeAllViews()
        when (position) {
            0 -> binding.contentFrame.addView(rankingView)
            1 -> binding.contentFrame.addView(searchView)
            2 -> binding.contentFrame.addView(FollowedTeamView(this))
            }
        }
    }





private fun setupRankingRecycler(root: View) {
    val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerViewRank)
    recyclerView.layoutManager = LinearLayoutManager(root.context)
    recyclerView.adapter = TeamAdapter(getTeamList(root.context)) { team ->
        val intent = Intent(root.context, TeamInfoActivity::class.java)
        intent.putExtra("team_id", team.id)
        root.context.startActivity(intent)
    }
}

