package com.example.teamproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.teamproject.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "로그인"

        val prefs = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        binding.btnLogin.setOnClickListener {
            val id = binding.editUserId.text.toString().trim()
            val pw = binding.editPassword.text.toString().trim()

            if (id.isBlank() || pw.isBlank()) {
                Toast.makeText(this, "아이디와 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val savedId = prefs.getString("user_id", null)
            val savedPw = prefs.getString("user_pw", null)

            if (savedId == null || savedPw == null) {
                // 첫 로그인 시 정보 저장
                prefs.edit()
                    .putString("user_id", id)
                    .putString("user_pw", pw)
                    .putString("login_user", id) // 로그인 상태 표시
                    .apply()
                Toast.makeText(this, "회원가입 완료 및 로그인", Toast.LENGTH_SHORT).show()
            } else if (savedId == id && savedPw == pw) {
                // 로그인 성공
                prefs.edit().putString("login_user", id).apply()
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "아이디 또는 비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 로그인 성공 → MainActivity 이동
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}
