package eu.rudisch.oauthadmin.database

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromStringList(stringList: List<String>) : String {
        return stringList.joinToString(",")
    }

    @TypeConverter
    fun stringToStringList(listString: String) : List<String> {
        return listString.split(",")
    }
}