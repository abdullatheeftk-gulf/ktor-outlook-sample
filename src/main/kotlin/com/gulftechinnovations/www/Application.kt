package com.gulftechinnovations.www

import com.gulftechinnovations.www.plugins.configureHTTP
import com.gulftechinnovations.www.plugins.configureRouting
import com.gulftechinnovations.www.plugins.configureSecurity
import com.gulftechinnovations.www.plugins.configureSerialization
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.server.application.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val client = HttpClient(Apache) {
        install(ContentNegotiation) {
            json()
        }
    }

    val config = environment.config

    val scopes = config.property("outlook.graphUserScopes").getList()
    println(scopes)

    configureSerialization()
    configureHTTP()
    configureSecurity(httpClient = client,config = config)
    configureRouting(client = client)
}
