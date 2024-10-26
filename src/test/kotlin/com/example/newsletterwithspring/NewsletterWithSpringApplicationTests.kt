package com.example.newsletterwithspring

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.mail.Message
import jakarta.mail.internet.MimeMessage
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.mail.MailSendException
import org.springframework.mail.javamail.JavaMailSender

class EmailServiceTests {
    private lateinit var mailSender: JavaMailSender
    private lateinit var emailService: EmailService

    @BeforeEach
    fun setup() {
        mailSender = mockk(relaxed = true)
        emailService = EmailService(mailSender)
    }

    val to = "test@example.com"
    val subject = "Test Subject"
    val text = "Test email body"
    val isHtml = true

    @Test
    fun `should send email with correct parameters`() {
        val mimeMessage: MimeMessage = mockk(relaxed = true)
        every { mailSender.createMimeMessage() } returns mimeMessage

        emailService.sendEmail(to, subject, text, isHtml)

        verify {
            mailSender.createMimeMessage()
            mailSender.send(mimeMessage)
        }
    }

    @Test
    fun `should log error when MailException occurs`() {
        every { mailSender.send(any<MimeMessage>()) } throws MailSendException("Something went wrong")

        emailService.sendEmail(to, subject, text, isHtml)

        verify(exactly = 1) { mailSender.send(any<MimeMessage>()) }
    }
}
