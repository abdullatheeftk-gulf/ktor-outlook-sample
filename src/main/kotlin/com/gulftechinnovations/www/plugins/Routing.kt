package com.gulftechinnovations.www.plugins

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import model.to_send.SentMail

fun Application.configureRouting(
    client: HttpClient
) {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }



        post("/sendMail") {
            try {
                val sentMail = call.receive<SentMail>()
                val token = call.request.header("Authorization") ?: throw  Exception("Invalid authorization code")
                val response = client.post("https://graph.microsoft.com/v1.0/me/sendMail") {
                    contentType(ContentType.Application.Json)
                    header("Authorization", "Bearer $token")
                    setBody(sentMail)
                }

                if(response.status.value == 202){
                    call.respond("Message Send")
                }else{
                    throw  Exception(response.status.description)
                }

            } catch (e: Exception) {
                call.respond(status = HttpStatusCode.BadRequest, message = e.message ?: "There have some problem")
            }
        }


        authenticate("microsoft-graph") {
            get("/login") {
                call.respondRedirect("/callback")
            }

            get("/callback") {
                try {
                    println("------------")
                    println("called call back")
                    val principal: OAuthAccessTokenResponse.OAuth2? = call.authentication.principal()
                    println("------------ code")
                    println(principal?.accessToken.toString())
                    val code = principal?.accessToken.toString()
                    call.respond(code)
                }catch (e:Exception){
                    call.respond(status = HttpStatusCode.BadRequest,message = e.message?:"There have some problem")
                }
            }

        }
    }
}
