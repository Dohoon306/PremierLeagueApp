package com.example.teamproject

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
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

    // 순위표 View 재사용을 위해 미리 생성
    private lateinit var rankingView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ViewBinding 초기화
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 툴바 설정
        setSupportActionBar(binding.toolbarMain)

        // 순위표 View 생성 및 세팅
        rankingView = layoutInflater.inflate(R.layout.view_ranking, binding.contentFrame, false)
        setupRankingRecycler(rankingView)

        // 탭 3개 추가
        val tabTitles = arrayOf("순위", "탭2", "탭3")
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
    }

    private fun showTab(position: Int) {
        binding.contentFrame.removeAllViews()

        when (position) {
            0 -> {
                // 순위표 뷰 추가
                binding.contentFrame.addView(rankingView)
            }

            1 -> {
                // SearchPlayerActivity로 이동 (별도 액티비티 실행)
                val intent = Intent(this, SearchPlayerActivity::class.java)
                startActivity(intent)

                // 현재 탭을 다시 0번(순위)으로 되돌림
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(0))
                // 또는 빈 뷰라도 추가해줘야 빈 FrameLayout이 보임
                val blankView = TextView(this).apply {
                    text = "검색 화면이 실행되었습니다."
                    gravity = Gravity.CENTER
                    layoutParams = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.MATCH_PARENT
                    )
                }
                binding.contentFrame.addView(blankView)
            }

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
