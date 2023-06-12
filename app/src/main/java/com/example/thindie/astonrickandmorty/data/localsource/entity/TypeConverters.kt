package com.example.thindie.astonrickandmorty.data.localsource.entity

import androidx.room.TypeConverter
import com.example.thindie.astonrickandmorty.domain.LinkPool
import com.example.thindie.astonrickandmorty.domain.personages.Location
import com.example.thindie.astonrickandmorty.domain.personages.Origin

class BaseListConverter(private val separator: String = BaseListConverter::class.java.name) {
    @TypeConverter
    fun fromBase(from: List<String>): String {
        return from.joinToString { separator }
    }

    @TypeConverter
    fun toBase(to: String): List<String> {
        return to.split(separator)
    }
}

class LinkPoolConverter(private val separator: String = LinkPoolConverter::class.java.name){
    @TypeConverter
    fun fromBase(pool: LinkPool): String {
        return pool.prev
            .plus(separator)
            .plus(pool.next)
    }

    @TypeConverter
    fun toBase(to: String): LinkPool {
         val list = to.split(separator)
        return LinkPool(list.first(), list.last())
    }
}

class LocationPersonageConverter(private val separator: String = LocationPersonageConverter::class.java.name){
    @TypeConverter
    fun fromBase(pool: Location): String {
        return pool.name
            .plus(separator)
            .plus(pool.url)
    }

    @TypeConverter
    fun toBase(to: String): Location {
        val list = to.split(separator)
        return Location(list.first(), list.last())
    }
}

class OriginPersonageConverter(private val separator: String = OriginPersonageConverter::class.java.name){
    @TypeConverter
    fun fromBase(pool: Origin): String {
        return pool.name
            .plus(separator)
            .plus(pool.url)
    }

    @TypeConverter
    fun toBase(to: String): Origin {
        val list = to.split(separator)
        return Origin(list.first(), list.last())
    }
}