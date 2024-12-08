package com.meetfootball.application.team.entity

class TeamEntity(
    val name: String,
    val shortName: String,
    val tla: String,
    val thumbnail: String?, // crest in api
    val address: String,
    val website: String?,
    val founded: Int,
    val clubColors: List<String> = listOf(),
    val venue: String?,
)