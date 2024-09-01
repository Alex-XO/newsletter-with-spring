package com.example.newsletterwithspring

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.slf4j.LoggerFactory
import org.springframework.web.client.HttpClientErrorException

@Service
class NewsService {

    private val logger = LoggerFactory.getLogger(NewsService::class.java)
    private val apiUrl = "http://api.mediastack.com/v1/news?access_key=MY_KEY"

    fun getNews(language: String, tags: List<String>): List<String> {
        val restTemplate = RestTemplate()

        val languageParam = "languages=$language"
        val categoriesParam = "categories=${tags.joinToString(",")}"

        val url = "$apiUrl&$languageParam&$categoriesParam"
        val titles = mutableListOf<String>()

        try {
            val response = restTemplate.getForObject(url, JsonNode::class.java)
            response?.get("data")?.forEach { article ->
                titles.add(article.get("title").asText())
            }

            logger.info("Found ${titles.size} news articles for language: $language and tags: ${tags.joinToString(",")}")
        } catch (e: HttpClientErrorException) {
            logger.error("Error fetching news from API: ${e.message}")
        } catch (e: Exception) {
            logger.error("Unexpected error: ${e.message}")
        }

        return titles
    }
}


