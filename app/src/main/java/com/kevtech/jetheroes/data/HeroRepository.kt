package com.kevtech.jetheroes.data

import com.kevtech.jetheroes.model.Hero
import com.kevtech.jetheroes.model.HeroesData

class HeroRepository {
    fun getHeroes(): List<Hero>{
        return HeroesData.heroes
    }
    fun searchHeroes(query: String): List<Hero>{
        return HeroesData.heroes.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }
}