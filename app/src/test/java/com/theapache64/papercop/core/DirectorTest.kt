package com.theapache64.papercop.core

import com.theapache64.expekt.should
import com.theapache64.papercop.data.local.entities.players.PlayerEntity
import org.junit.Test

/**
 * Created by theapache64 : Dec 11 Fri,2020 @ 18:20
 */
class DirectorTest {
    @Test
    fun `provide less than available characters`() {
        val names = listOf("A", "B", "C").map {
            PlayerEntity(0, it, 0)
        }
        val chars = Director.provideRoles(names)
        chars.size.should.equal(names.size)
    }

    @Test
    fun `provide more than available characters`() {
        val names = ('A'..'Z').map {
            PlayerEntity(0, it.toString(), 0)
        }.toList()
        val chars = Director.provideRoles(names)
        chars.size.should.equal(names.size)
    }
}