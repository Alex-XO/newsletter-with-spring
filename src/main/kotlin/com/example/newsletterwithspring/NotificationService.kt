package com.example.newsletterwithspring

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.slf4j.LoggerFactory

@Service
class NotificationService(
    @Autowired private val userService: UserService,
    @Autowired private val newsService: NewsService,
    @Autowired private val emailService: EmailService
) {

    private val logger = LoggerFactory.getLogger(NotificationService::class.java)

    @Scheduled(fixedRate = 60000)
    fun checkForNewsAndSendEmails() {
        userService.getUsers().forEach { user ->
            val news = newsService.getNews(user.language, user.tags)
            if (news.size >= 5) {
                val message = "New articles:\n" + news.joinToString("\n")
                emailService.sendEmail(user.email, "Latest News", message)
                logger.info("Sent email to ${user.email} with ${news.size} new articles")
            } else {
                logger.info("Not enough news for ${user.email}. Only found ${news.size} articles.")
            }
        }
    }
}
