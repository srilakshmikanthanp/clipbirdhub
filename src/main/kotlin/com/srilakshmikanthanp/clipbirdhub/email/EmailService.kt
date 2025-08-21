package com.srilakshmikanthanp.clipbirdhub.email

import jakarta.mail.internet.InternetAddress
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

@Service
class EmailService(private val mailSender: JavaMailSender) {
  @Value("\${spring.mail.from.address}")
  private lateinit var mailFromAddress: String

  @Value("\${templates}")
  private lateinit var templateDir: String

  fun send(
    template: Template,
    to: String,
    subject: String,
    bcc: List<String> = emptyList(),
    cc: List<String> = emptyList(),
    attributes: Map<String, Any> = emptyMap()
  ) {
    val templateResolver = ClassLoaderTemplateResolver().apply {
      prefix = templateDir
      suffix = ".html"
      characterEncoding = "UTF-8"
    }
    val templateEngine = SpringTemplateEngine().apply {
      setTemplateResolver(templateResolver)
    }
    val context = Context().apply {
      setVariables(attributes)
    }
    val htmlContent = templateEngine.process(
      template.file, context
    )
    val message = mailSender.createMimeMessage()
    val helper = MimeMessageHelper(
      message, true, "UTF-8"
    )
    if (bcc.isNotEmpty()) {
      helper.setBcc(bcc.toTypedArray())
    }
    if (cc.isNotEmpty()) {
      helper.setCc(cc.toTypedArray())
    }
    helper.setSubject(subject)
    helper.setTo(to)
    helper.setText(htmlContent, true)
    helper.setFrom(mailFromAddress)
    mailSender.send(message)
  }
}
