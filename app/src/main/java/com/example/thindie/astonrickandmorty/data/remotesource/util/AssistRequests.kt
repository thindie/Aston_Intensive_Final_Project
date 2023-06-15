package com.example.thindie.astonrickandmorty.data.remotesource.util

private const val COMMA = ","
private const val NETWORK_QUERY_PATTERN_FOR_TAGS = "%s$COMMA"


fun commaQueryEncodedBuilder(list: Iterable<String>): String {
    var string = ""
    list.forEach {
        string += String.format(NETWORK_QUERY_PATTERN_FOR_TAGS, it)
    }
    return string
}
