package com.syc.plugin

import org.gradle.api.Project

class PathUtils {
    /**
     * 通过project获取资源路径
     */
    static def getPathFromProject(Project project){
        return "${project.getProjectDir()}/src/main/res/"
    }
}