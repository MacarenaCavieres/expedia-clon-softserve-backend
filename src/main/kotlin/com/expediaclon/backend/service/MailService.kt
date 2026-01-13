package com.expediaclon.backend.service

import com.expediaclon.backend.model.Booking
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class MailService(
    @Value("\${resend.api.key}") private val apiKey: String,
    @Value("\${resend.from}") private val from: String,
    @Value("\${frontend.url}") private val frontendUrl: String
) {
    private val client = OkHttpClient()

    fun sendPasswordResetEmail(toEmail: String, token: String) {
        val resetUrl = "$frontendUrl/auth/reset-password?token=$token"
        val htmlContent = buildResetPasswordEmailHtml(resetUrl)

        sendEmail(
            to = toEmail,
            subject = "Expedia Clone - Password Reset",
            html = htmlContent
        )
    }

    fun sendCreationBookingEmail(toEmail: String, booking: Booking) {
        val urlTrips = "$frontendUrl/my-trips"
        val htmlContent = buildCreationBookingEmailHtml(urlTrips, booking)

        sendEmail(
            to = toEmail,
            subject = "Expedia Clone - Booking Confirmation #${booking.id}",
            html = htmlContent
        )
    }

    private fun sendEmail(to: String, subject: String, html: String) {
        val json = """
        {
          "from": "$from",
          "to": ["$to"],
          "subject": "$subject",
          "html": ${jsonEscape(html)}
        }
        """.trimIndent()

        val request = Request.Builder()
            .url("https://api.resend.com/emails")
            .addHeader("Authorization", "Bearer $apiKey")
            .addHeader("Content-Type", "application/json")
            .post(json.toRequestBody("application/json".toMediaType()))
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw RuntimeException("Error sending email via Resend: ${response.body?.string()}")
            }
        }
    }

    private fun jsonEscape(html: String): String =
        "\"" + html.replace("\"", "\\\"").replace("\n", "") + "\""

    private fun buildResetPasswordEmailHtml(resetUrl: String): String {
        return """
            <html>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0;">
                <br/>
                <br/>
                <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" style="max-width: 600px; background-color: #ffffff; border-radius: 8px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); margin: auto">
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
                            &copy; ${java.time.Year.now().value} Expedia Clone. All rights reserved.
                        </td>
                    </tr>
                </table>
            </body>
            </html>
        """.trimIndent()
    }

    private fun buildCreationBookingEmailHtml(urlTrips: String, booking: Booking): String {
        val hotelName = booking.roomType.hotel.name
        val checkIn = booking.checkInDate
        val checkOut = booking.checkOutDate
        val totalPrice = booking.totalPrice
        val guests = booking.guestNames
        return """
            <html>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0;">
                <br/><br/>
                <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%" style="max-width: 600px; background-color: #ffffff; border-radius: 8px; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1); margin: auto">
                    <tr>
                        <td style="padding: 40px;">
                            <h2 style="color: #1668e3; margin-bottom: 20px;">You made a reservation!</h2>
                            <p style="color: #333333; font-size: 16px;">Hi <strong>${booking.user.name}</strong>,</p>
                            <p style="color: #666666; line-height: 1.6;">
                                Thanks for booking with us. Your reservation at <strong>$hotelName</strong> is all set.
                            </p>
                            
                            <div style="background-color: #f9f9f9; border-radius: 8px; padding: 20px; margin: 25px 0; border: 1px solid #eeeeee;">
                                <h3 style="margin-top: 0; color: #333333; font-size: 18px;">Reservation Details</h3>
                                <p style="margin: 5px 0; color: #555555;"><strong>Hotel:</strong> $hotelName</p>
                                <p style="margin: 5px 0; color: #555555;"><strong>Check-in:</strong> $checkIn</p>
                                <p style="margin: 5px 0; color: #555555;"><strong>Check-out:</strong> $checkOut</p>
                                <p style="margin: 5px 0; color: #555555;"><strong>Guests:</strong> $guests</p>
                                <hr style="border: 0; border-top: 1px solid #dddddd; margin: 15px 0;">
                                <p style="margin: 5px 0; color: #333333; font-size: 18px;"><strong>Total Price:</strong> $$totalPrice</p>
                            </div>

                            <table role="presentation" border="0" cellpadding="0" cellspacing="0" style="margin: 25px 0;">
                                <tr>
                                    <td align="center" style="border-radius: 5px;" bgcolor="#1668e3">
                                        <a href="$urlTrips" target="_blank" style="font-size: 16px; font-weight: bold; text-decoration: none; color: #ffffff; padding: 12px 24px; border-radius: 5px; display: inline-block; background-color: #1668e3;">
                                            Manage My Trips
                                        </a>
                                    </td>
                                </tr>
                            </table>

                            <p style="color: #666666; line-height: 1.6;">
                                If you need to make changes or cancel your reservation, please do so through our website.
                            </p>
                            <p style="color: #666666; line-height: 1.6;">
                                Enjoy your stay!<br>
                                <strong>The Expedia Clone Team</strong>
                            </p>
                        </td>
                    </tr>
                </table>
                
                <table role="presentation" border="0" cellpadding="0" cellspacing="0" width="100%">
                    <tr>
                        <td align="center" style="padding: 20px 0; font-size: 12px; color: #aaaaaa;">
                            &copy; ${java.time.Year.now().value} Expedia Clone. All rights reserved.
                        </td>
                    </tr>
                </table>
            </body>
            </html>
        """.trimIndent()
    }
}