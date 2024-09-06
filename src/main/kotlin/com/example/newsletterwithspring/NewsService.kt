package com.example.newsletterwithspring

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.client.HttpClientErrorException

@Service
class NewsService(
    private val articlesService: ArticlesService,
    private val newsProcessor: NewsProcessor
) {

    private val logger = LoggerFactory.getLogger(NewsService::class.java)
    private val apiUrl = "http://api.mediastack.com/v1/news?access_key=MY_KEY"

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

    /**
     * Receive filtered news for the user based on categories and language
     */
    fun getFilteredNews(language: String, categories: List<String>): List<Articles> {
        return try {
            val articles = articlesService.getFilteredNews(language, categories)
            logger.info("Found ${articles.size} news articles for language: $language and categories: ${categories.joinToString(",")}")
            articles
        } catch (e: Exception) {
            logger.error("Error fetching filtered news from DB: ${e.message}")
            emptyList()
        }
    }
}

