package com.example.newsletterwithspring

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import org.slf4j.LoggerFactory
import org.springframework.mail.MailException
import org.springframework.mail.javamail.MimeMessageHelper

@Service
class EmailService(private val mailSender: JavaMailSender) {

    private val logger = LoggerFactory.getLogger(EmailService::class.java)

    fun sendEmail(to: String, subject: String, text: String, isHtml: Boolean = false) {
        try {
            val message = MimeMessageHelper(mailSender.createMimeMessage(), true)

            message.setTo(to)
            message.setSubject(subject)
            if (isHtml) {
                message.setText(text, true)
            } else {
                message.setText(text)
            }

            mailSender.send(message.mimeMessage)
            logger.info("Email sent to $to with subject $subject")
        } catch (e: MailException) {
            logger.error("Failed to send email to $to: ${e.message}")
        } catch (e: Exception) {
            logger.error("Unexpected error while sending email to $to: ${e.message}")
        }
    }
}
