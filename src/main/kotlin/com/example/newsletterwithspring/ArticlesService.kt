package com.example.newsletterwithspring

import org.springframework.stereotype.Service

@Service
class ArticlesService(private val articlesRepository: ArticlesRepository) {


    fun saveArticleIfNotExists(article: List<Articles>) {
        article.filter { article ->
            articlesRepository.findByTitleAndPublishedAt(
                article.title,
                article.publishedAt
            ) == null
        }
        articlesRepository.saveAll(article)

    }

    fun getFilteredNews(language: String, categories: List<String>): List<Articles> {
        return articlesRepository.findByLanguageAndCategoryIn(language, categories)
    }
}

