package com.kevtech.jetheroes

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kevtech.jetheroes.data.HeroRepository
import com.kevtech.jetheroes.model.Hero
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.IllegalArgumentException

class JetHeroesViewModel(private val heroRepository: HeroRepository):ViewModel() {
    private val _groupedHeroes = MutableStateFlow(
        heroRepository.getHeroes()
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    )
    val groupHeroes: StateFlow<Map<Char, List<Hero>>> get() = _groupedHeroes

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun Search(newQuery:String){
        _query.value = newQuery
        _groupedHeroes.value = heroRepository.searchHeroes(_query.value)
            .sortedBy { it.name }
            .groupBy { it.name[0] }
    }
}
@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val repository: HeroRepository): ViewModelProvider.NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JetHeroesViewModel::class.java)){
            return JetHeroesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknow ViewModel Class" + modelClass.name)
    }
}