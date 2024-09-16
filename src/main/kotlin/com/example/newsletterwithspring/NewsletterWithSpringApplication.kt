package com.example.newsletterwithspring

import com.example.newsletterwithspring.news.NewsService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class NewsletterWithSpringApplication

fun main(args: Array<String>) {
    val context = runApplication<NewsletterWithSpringApplication>(*args)
    val newsService = context.getBean(NewsService::class.java)
    newsService.fetchAndSaveAllNews()
}
