package com.eun.sample.util

import android.util.Log

object LogManager {

    fun log(tag: String? , message: String) {
        var msg = message
        var stop = false

        while (!stop && msg.isNotEmpty()) {
            if (msg.length > 4000) {
                Log.d(
                    tag ,
                    "##### (" + msg.length + ") " + msg.substring(0 , 4000).replace("," , ",\n")
                )
                msg = msg.substring(4000)
                stop = false
            } else {
                Log.d(tag , "##### (" + msg.length + ") " + msg.replace("," , ",\n"))
                stop = true
            }
        }

    }

    fun error(tag: String? , message: String) {
        var msg: String = message
        var stop = false

        while (!stop && msg.isNotEmpty()) {
            if (msg.length > 4000) {
                Log.w(
                    tag ,
                    "##### (" + msg.length + ") " + msg.substring(0 , 4000).replace("," , ",\n")
                )
                msg = msg.substring(4000)
                stop = false
            } else {
                Log.w(tag , "##### (" + msg.length + ") " + msg.replace("," , ",\n"))
                stop = true
            }
        }
    }
}
