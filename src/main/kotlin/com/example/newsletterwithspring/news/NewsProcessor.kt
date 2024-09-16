package com.example.newsletterwithspring.news

import com.example.newsletterwithspring.articles.Articles
import com.example.newsletterwithspring.articles.ArticlesService
import com.fasterxml.jackson.databind.JsonNode
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneOffset
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class NewsProcessor(private val articlesService: ArticlesService) {

    private val logger: Logger = LoggerFactory.getLogger(NewsProcessor::class.java)

    fun processNewsData(newsData: List<JsonNode>) {
        val todayNews = newsData
            .map { articleJson ->
                Articles(
                    author = articleJson.get("author").asText(null),
                    title = articleJson.get("title").asText(),
                    description = articleJson.get("description").asText(),
                    url = articleJson.get("url").asText(),
                    source = articleJson.get("source").asText(),
                    image = articleJson.get("image").asText(null),
                    category = articleJson.get("category").asText(),
                    language = articleJson.get("language").asText(),
                    country = articleJson.get("country").asText(),
                    publishedAt = OffsetDateTime.parse(articleJson.get("published_at").asText())
                )
            }
            .filter { article ->
                article.publishedAt.toLocalDate().isEqual(LocalDate.now(ZoneOffset.UTC))
            }

        articlesService.saveArticleIfNotExists(todayNews)

        if (todayNews.isEmpty()) {
            logger.info("No articles published today.")
        } else {
            logger.info("Processed and saved ${todayNews.size} articles published today.")
        }
    }
}
