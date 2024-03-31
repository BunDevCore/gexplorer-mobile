package me.thefen.gexplorerapi.dtos

data class LeaderboardEntryDto<V>(val user: ShortUserDto, val value: V)