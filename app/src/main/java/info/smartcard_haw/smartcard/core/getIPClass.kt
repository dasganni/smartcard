package info.smartcard_haw.smartcard.core

import java.net.NetworkInterface

object getIPClass {

    val ipAdress: String
        get() {
            var ip = ""
            try {
                val interfaces = NetworkInterface.getNetworkInterfaces()
                while (interfaces.hasMoreElements()) {
                    val iface = interfaces.nextElement()
                    if (iface.isLoopback || !iface.isUp) {
                        continue
                    }

                    val addresses = iface.inetAddresses
                    while (addresses.hasMoreElements()) {
                        val addr = addresses.nextElement()
                        ip = addr.hostAddress
                        if (ip.startsWith("192")) {
                            return ip
                        }
                    }
                }
            } catch (e: Exception) {
            }

            return ip

        }

}