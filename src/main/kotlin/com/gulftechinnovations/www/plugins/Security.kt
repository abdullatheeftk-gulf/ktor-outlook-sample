package com.gulftechinnovations.www.plugins

import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.config.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

fun Application.configureSecurity(
    httpClient: HttpClient,
    config:ApplicationConfig
) {
    val scopes = config.property("outlook.graphUserScopes").getList()

    val redirects = mutableMapOf<String, String>()
    authentication {
        oauth("microsoft-graph") {
            urlProvider = { "http://localhost:3039/callback" }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name="my-app",
                    authorizeUrl = "https://login.microsoftonline.com/common/oauth2/v2.0/authorize",
                    accessTokenUrl = "https://login.microsoftonline.com/common/oauth2/v2.0/token",
                    clientId = "e5d49319-e732-4d11-a787-245e44b0bbc2",
                    clientSecret = "gPm8Q~KI5_ygA93ZYcjo6aI3q2OuW6q.B8JdZc0s",
                    defaultScopes = scopes,
                    requestMethod = HttpMethod.Post,
                    onStateCreated = { call, state ->
                        println("----------------")
                        println("onState created")
                        //saves new state with redirect url value
                        call.request.queryParameters["redirectUrl"]?.let {
                            println("----------------")
                            println("query parameters")
                            redirects[state] = it
                        }
                    }
                )
            }
           client  = httpClient

        }
    }



}


