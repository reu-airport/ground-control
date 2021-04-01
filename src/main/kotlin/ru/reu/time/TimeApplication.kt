package ru.reu.time

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class TimeApplication

fun main(args: Array<String>) {
    runApplication<TimeApplication>(*args)
}
