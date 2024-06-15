package org.example

import bisq.client.kotlin.models.Info
import bisq.client.kotlin.operations.InfoOperations

class App {
    val greeting: String
        get() {
            return "Hello World!"
        }
}

suspend fun main() {
    println(App().greeting)

    val apiInstance = InfoOperations("http://localhost:2141")
    val result: Info = apiInstance.getInfo().body();
    println(result)
}
