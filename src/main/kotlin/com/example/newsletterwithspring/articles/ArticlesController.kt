package com.example.newsletterwithspring.articles

import com.example.newsletterwithspring.news.NewsService
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
class ArticlesController(
    private val newsService: NewsService
) {
    @GetMapping("/articles")
    fun getArticles(
        @RequestParam category: String?,
        @RequestParam publishedAt: LocalDate?
    ): List<Articles> {
        return newsService.findArticlesByDateAndCategory(publishedAt, category)
    }
}