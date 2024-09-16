package com.example.newsletterwithspring.articles

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.OffsetDateTime

interface ArticlesRepository : JpaRepository<Articles, Long> {
    fun findByLanguageAndCategoryIn(language: String, categories: List<String>): List<Articles>

    @Query("SELECT a FROM Articles a WHERE a.language = :language AND a.category IN :categories AND a.savedAt > :lastSentAt")
    fun findNewsAfter(
        @Param("lastSentAt") lastSentAt: OffsetDateTime?,
        @Param("language") language: String,
        @Param("categories") categories: List<String>
    ): List<Articles>

    @Query("SELECT a FROM Articles a WHERE CAST(a.publishedAt AS date) = CURRENT_DATE AND a.title IN :titles")
    fun findByNormalizedTitleInAndPublishedToday(@Param("titles") titles: List<String>): List<Articles>
}
