package com.example.newsletterwithspring.articles

import org.springframework.stereotype.Service

@Service
class ArticlesService(private val articlesRepository: ArticlesRepository) {

    fun normalizeTitle(title: String): String {
        return title.replace(Regex("\\[Image \\d+ of \\d+\\]"), "").trim()
    }

    fun saveArticleIfNotExists(articles: List<Articles>) {
        val normalizedTitles = articles.map { normalizeTitle(it.title) }

        val existingArticles = articlesRepository.findByNormalizedTitleInAndPublishedToday(normalizedTitles)

        val newArticles = articles.filter { article ->
            existingArticles.none { existingArticle ->
                normalizeTitle(existingArticle.title) == normalizeTitle(article.title) &&
                        existingArticle.publishedAt.toLocalDate().isEqual(article.publishedAt.toLocalDate()) &&
                        existingArticle.description == article.description &&
                        existingArticle.source == article.source
            }
        }

        if (newArticles.isNotEmpty()) {
            articlesRepository.saveAll(newArticles)
        }
    }
}
