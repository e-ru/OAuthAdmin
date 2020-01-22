package eu.rudisch.oauthadmin.database

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class ConvertersTest {

    @Test
    fun shouldReturnStringFromStringListSeparatedByComma() {
        val converters = Converters()
        assertEquals("foo,bar", converters.fromStringList(listOf("foo", "bar")))
    }

    @Test
    fun shouldReturnListFromStringToStringList() {
        val converters = Converters()
        assert(converters.stringToStringList("foo,bar").containsAll(listOf("foo","bar")))
    }
}