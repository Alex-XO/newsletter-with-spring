package com.example.newsletterwithspring.news

import com.example.newsletterwithspring.articles.Articles
import com.example.newsletterwithspring.articles.ArticlesRepository
import com.example.newsletterwithspring.articles.ArticlesService
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.client.HttpClientErrorException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class NewsService(
    private val articlesService: ArticlesService,
    private val newsProcessor: NewsProcessor,
    private val articlesRepository: ArticlesRepository
)
{

    private val logger = LoggerFactory.getLogger(NewsService::class.java)
    private val apiUrl = "http://api.mediastack.com/v1/news?access_key=426ce5efe4575478f589ed2f0b313bb5"

    /**
     * Receiving and saving all news
     */
    @Scheduled(fixedRate = 60000)
    fun fetchAndSaveAllNews(): Boolean {
        val restTemplate = RestTemplate()

        return try {
            val url = "$apiUrl&timestamp=${System.currentTimeMillis()}"
            val response = restTemplate.getForObject(url, JsonNode::class.java)
            response?.let {
                val newsData = it.get("data")
                val newsList = newsData.toList()
                newsProcessor.processNewsData(newsList)
            }
            logger.info("Fetched and saved all available news.")
            true
        } catch (e: HttpClientErrorException) {
            logger.error("Error fetching news from API: ${e.message}")
            false
        } catch (e: Exception) {
            logger.error("Unexpected error: ${e.message}")
            false
        }
    }

    fun findArticlesByDateAndCategory(date: LocalDate?, category: String?): List<Articles> {
        val startOfDay = date?.atStartOfDay()
        val endOfDay = date?.atTime(LocalTime.MAX)

        return articlesRepository.findByPublishedAtBetweenAndCategory(startOfDay, endOfDay, category)
    }

    fun getNewsAfter(lastSentAt: LocalDateTime?, language: String, categories: List<String>): List<Articles> {
        return articlesRepository.findNewsAfter(lastSentAt, language, categories)
    }
}

