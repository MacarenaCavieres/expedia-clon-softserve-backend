package com.expediaclon.backend.service

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import jakarta.mail.internet.MimeMessage

@Service
class MailService(
    private val mailSender: JavaMailSender
) {
    /**
     * Envía el correo electrónico de restablecimiento de contraseña en formato HTML.
     * @param toEmail La dirección de correo del destinatario.
     * @param token El token generado para el restablecimiento de contraseña.
     */
    fun sendPasswordResetEmail(toEmail: String, token: String) {
        val message: MimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, "utf-8")

        val defaultFromEmail = "noreply@expediaclone.com"
        helper.setFrom(defaultFromEmail, "Expedia Clone Team")
        helper.setTo(toEmail)
        helper.setSubject("Expedia Clone - Password Reset")

        val resetUrl = "http://localhost:5173/reset-password?token=$token"

        val htmlContent = buildResetPasswordEmailHtml(resetUrl)

        helper.setText(htmlContent, true)

        try {
            mailSender.send(message)
        } catch (e: Exception) {
            println("ERROR al enviar email a $toEmail: ${e.message}")
        }
    }


    private fun buildResetPasswordEmailHtml(resetUrl: String): String {
        return """
            <html>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0;">
                <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td align="center" style="padding: 20px 0 30px 0;">
                            <h1 style="color: #191e3b;"><img src="https://upload.wikimedia.org/wikipedia/commons/thumb/3/3b/Expedia_Logo_2023.svg/1280px-Expedia_Logo_2023.svg.png" alt="Expedia logo" style="width: 200px;"/></h1>
                        </td>
                    </tr>
                </table>

                <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" style="max-width: 600px; background-color: #ffffff; border-radius: 8px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); margin: auto;">
                    <tr>
                        <td style="padding: 40px;">
                            <h2 style="color: #333333;">Password Reset Requested</h2>
                            <p style="color: #666666; line-height: 1.6;">
                                Hi,
                            </p>
                            <p style="color: #666666; line-height: 1.6;">
                                We received a request to reset your account password.
                                Click the button below to change your password:
                            </p>

                            <table role="presentation" border="0" cellpadding="0" cellspacing="0" style="margin: 25px 0;">
                                <tr>
                                    <td align="center" style="border-radius: 5px;" bgcolor="#1668e3">
                                        <a href="$resetUrl" target="_blank" style="font-size: 16px; font-weight: bold; text-decoration: none; color: #ffffff; padding: 12px 24px; border-radius: 5px; display: inline-block; background-color: #1668e3;">
                                            Reset Password
                                        </a>
                                    </td>
                                </tr>
                            </table>

                            <p style="color: #666666; line-height: 1.6;">
                                This link is valid for 15 minutes. If you did not request this change, please ignore this email.
                            </p>
                            <p style="color: #666666; line-height: 1.6;">
                                Greetings,<br>
                                The Expedia Clone Team
                            </p>
                        </td>
                    </tr>
                </table>
                
                <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td align="center" style="padding: 20px 0; font-size: 12px; color: #aaaaaa;">
                            &copy; ${java.time.Year.now().value} Expedia Clone. Todos los derechos reservados.
                        </td>
                    </tr>
                </table>
            </body>
            </html>
        """.trimIndent()
    }
}