package com.example.newsletterwithspring

import com.example.newsletterwithspring.news.NewsService
import com.example.newsletterwithspring.user.UserService
import com.example.newsletterwithspring.user.UserRepository
import jakarta.transaction.Transactional
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class NotificationService(
    private val userService: UserService,
    private val newsService: NewsService,
    private val emailService: EmailService,
    private val userRepository: UserRepository
) {

    @Scheduled(fixedRate = 60000)
    @Transactional
    fun checkForNewsAndSendEmails() {
        val users = userService.getUsers()

        users.forEach { user ->
            val newNews = newsService.getNewsAfter(user.lastSentAt, user.language, user.categories.map { it.name })
                .take(5)

            if (newNews.size == 5) {
                val message = StringBuilder("New articles:<br/><ul>")
                newNews.forEach { article ->
                    message.append("<li><a href='${article.url}'>${article.title}</a></li>")
                }
                message.append("</ul>")

                emailService.sendEmail(
                    user.email,
                    "Latest News",
                    message.toString(),
                    isHtml = true
                )

                user.lastSentAt = OffsetDateTime.now()
                userRepository.save(user)
            }
        }
    }
}
