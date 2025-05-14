package com.example.teamproject

data class Team(
    val id: Int,
    val name: String,
    val rank: Int,
    val logo: String,     // ← drawable 리소스 이름 (확장자 없이)
    val points: Int,
    val players: List<Player>
)