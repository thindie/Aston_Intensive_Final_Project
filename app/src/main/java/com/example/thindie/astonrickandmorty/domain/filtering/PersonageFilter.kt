package com.example.thindie.astonrickandmorty.domain.filtering

import com.example.thindie.astonrickandmorty.domain.matchCriteria
import com.example.thindie.astonrickandmorty.domain.personages.PersonageDomain


data class PersonageFilter(
    val name: String = DEFAULT_VALUE,
    val status: Status = Status.UNSPECIFIED,
    val species: String = DEFAULT_VALUE,
    val type: String = DEFAULT_VALUE,
    val gender: Gender = Gender.UNSPECIFIED
) : Filter<PersonageDomain, PersonageFilter> {
    override val isDefault
        get() = isDefaults()


    private fun isDefaults() =
        name == defaultValue &&
                status == Status.UNSPECIFIED &&
                species == defaultValue &&
                type == defaultValue &&
                gender == Gender.UNSPECIFIED

    override fun detectActualFilteringCases(): PersonageFilter {
        diffList.clear()
        if (name != defaultValue) diffList += 1
        if (status != Status.UNSPECIFIED) diffList += 2
        if (species != defaultValue) diffList += 3
        if (type != defaultValue) diffList += 4
        if (gender != Gender.UNSPECIFIED) diffList += 5
        return this
    }

    override fun filter(list: List<PersonageDomain>): List<PersonageDomain> {
        val resultList = list.toMutableList()
        diffList.forEach { filter ->
            when (filter) {
                1 -> {
                    resultList.map { it.name.matchCriteria(name) }
                }
                2 -> {
                    resultList.map { it.status.matchCriteria(status.toString()) }
                }
                3 -> {
                    resultList.map { it.species.matchCriteria(species) }
                }
                4 -> {
                    resultList.map { it.type.matchCriteria(type) }
                }
                5 -> {
                    resultList.map { it.gender.matchCriteria(gender.toString()) }
                }
            }
        }
        if (resultList.isEmpty()) error(unSuccessFiltering)
        return resultList
    }

    companion object {
        private val diffList: MutableList<Int> = mutableListOf()

        private const val DEFAULT_VALUE = ""

        enum class Gender {
            FEMALE, MALE, GENDERLESS, UNKNOWN, UNSPECIFIED
        }

        enum class Status {
            ALIVE, DEAD, UNKNOWN, UNSPECIFIED
        }
    }
}





