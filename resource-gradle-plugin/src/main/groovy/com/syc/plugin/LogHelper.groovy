package com.syc.plugin

import org.gradle.api.Project
import org.gradle.api.logging.Logger

class LogHelper {
    private static Logger logger
    private static boolean loggable = true
    private LogHelper(){}

    static def init(Project project){
        logger = project.getLogger()
    }

    static def switchLogable(boolean loggable){
        this.loggable = loggable
    }

    static def d(String msg){
        if(loggable){
            logger.debug(msg)
        }

    }
}