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

    static def i(String msg){
        if(loggable){
            logger.info(msg)
        }
    }

    static def w(String msg){
        if(loggable){
            logger.warn(msg)
        }
    }

    static def e(String msg){
        if(loggable){
            logger.error(msg)
        }
    }
}