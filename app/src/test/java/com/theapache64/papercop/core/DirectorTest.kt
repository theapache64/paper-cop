package com.theapache64.papercop.core

import com.theapache64.expekt.should
import org.junit.Test

/**
 * Created by theapache64 : Dec 11 Fri,2020 @ 18:20
 */
class DirectorTest {
    @Test
    fun `provide less than available characters`() {
        val names = listOf("A", "B", "C")
        val chars = Director.provideCharacters(names)
        println(chars)
        chars.size.should.equal(names.size)
    }

    @Test
    fun `provide more than available characters`() {
        val names = ('A'..'Z').map { it.toString() }.toList()
        val chars = Director.provideCharacters(names)
        chars.forEach { println(it) }
        chars.size.should.equal(names.size)
    }
}