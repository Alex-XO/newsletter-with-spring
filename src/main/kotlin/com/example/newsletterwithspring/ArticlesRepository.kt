package com.example.newsletterwithspring

import org.springframework.data.jpa.repository.JpaRepository
import java.time.OffsetDateTime

interface ArticlesRepository : JpaRepository<Articles, Long> {
    fun findByLanguageAndCategoryIn(language: String, categories: List<String>): List<Articles>

    fun findByTitleAndPublishedAt(title: String, publishedAt: OffsetDateTime): Articles?
}
