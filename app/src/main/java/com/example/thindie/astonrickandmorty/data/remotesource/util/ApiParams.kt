package com.example.thindie.astonrickandmorty.data.remotesource.util

object ApiParams {
    const val ENDPOINT = "https://rickandmortyapi.com/api/"
    const val CHARACTER = "${ENDPOINT}character"
    const val LOCATION = "${ENDPOINT}location"
    const val EPISODE = "${ENDPOINT}episode"
    const val EPISODE_ID = "${EPISODE}/{id}"
    const val CHARACTER_ID = "${CHARACTER}/{id}"

    const val PAGE = "page"
    const val CHARACTER_NAME = "name"
    const val CHARACTER_STATUS = "status"
    const val STATUS_ALIVE = "alive"
    const val STATUS_DEAD = "dead"
    const val STATUS_UNKNOWN = "unknown"
    const val CHARACTER_SPECIES = "species"
    const val CHARACTER_TYPE = "type"
    const val CHARACTER_GENDER = "gender"
    const val GENDER_MALE = "male"
    const val GENDER_FEMALE = "female"
    const val GENDER_GENDERLESS = "female"
    const val GENDER_UNKNOWN = "unknown"
    const val LOCATION_NAME = "name"
    const val LOCATION_TYPE = "type"
    const val LOCATION_DIMENSION = "dimension"
    const val EPISODE_NAME = "name"
    const val EPISODE_CODE = "episode"
    const val ON_FAIL = " Failed fetch data "
    const val ON_SUCCESS = " SO WHAT!? "
    const val ON_NULL_RESPONSE = " Null success response body "
    const val QUERY_PARAM_PAGE = "page"
}

