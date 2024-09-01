package com.example.newsletterwithspring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class NewsletterWithSpringApplication

fun main(args: Array<String>) {
    runApplication<NewsletterWithSpringApplication>(*args)
}
