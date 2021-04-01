package ru.reu.time.controllers

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import ru.reu.time.services.GroundControlService

@WebFluxTest
internal class TimeControllerTest {

    private lateinit var timeService: GroundControlService

    @BeforeEach
    fun setUp() {

//        timeService = TimeService()

    }

    @Test
    fun currentTime() {
    }

    @Test
    fun changeTime() {
    }

}
