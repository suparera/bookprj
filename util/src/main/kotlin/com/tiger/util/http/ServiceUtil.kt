package com.tiger.util.http

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.net.InetAddress
import java.net.UnknownHostException

@Component
class ServiceUtil(@Value("\${server.port:0}") private val port: String) {

    val serviceAddress: String by lazy {
        "${findMyHostname()}/${findMyIpAddress()}:$port"
    }

    private fun findMyHostname(): String =
        try {
            InetAddress.getLocalHost().hostName
        } catch (e: UnknownHostException) {
            "Unknown host name"
        }

    private fun findMyIpAddress(): String =
        try {
            InetAddress.getLocalHost().hostAddress
        } catch (ex: UnknownHostException) {
            "Unknown IP Address"
        }
}