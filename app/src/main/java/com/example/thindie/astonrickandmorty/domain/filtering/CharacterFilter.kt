package com.example.thindie.astonrickandmorty.domain.filtering


data class CharacterFilter(
    val name: String = DEFAULT_VALUE,
    val status: Status = Status.UNSPECIFIED,
    val species: String = DEFAULT_VALUE,
    val type: String = DEFAULT_VALUE,
    val gender: Gender = Gender.UNSPECIFIED
): Filter {
    override val isDefault
        get() = isDefaults()

    private fun isDefaults() =
        name == DEFAULT_VALUE &&
                status == Status.UNSPECIFIED &&
                species == DEFAULT_VALUE &&
                type == DEFAULT_VALUE &&
                gender == Gender.UNSPECIFIED

    companion object {
        fun CharacterFilter.getActualFilteringCases(): List<Int> {
            val diffList: MutableList<Int> = mutableListOf()
            if (name != DEFAULT_VALUE) diffList += 1
            if (status != Status.UNSPECIFIED) diffList += 2
            if (species != DEFAULT_VALUE) diffList += 3
            if (type != DEFAULT_VALUE) diffList += 4
            if (gender != Gender.UNSPECIFIED) diffList += 5
            return diffList
        }


    }

}


enum class Gender {
    FEMALE, MALE, GENDERLESS, UNKNOWN, UNSPECIFIED
}

enum class Status {
    ALIVE, DEAD, UNKNOWN, UNSPECIFIED
}

private const val DEFAULT_VALUE = ""