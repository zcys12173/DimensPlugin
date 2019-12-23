package com.syc.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * dimens资源插件
 * 1.自动从res资源中提取dimens资源到dimens.xml文件中
 * 2.自动生成适配文件
 */
class DimensPlugin implements Plugin<Project> {
    private static Project project
    private static DimensConfigExtension config
    @Override
    void apply(Project project) {
        if (!project.android) {
            throw new IllegalArgumentException("the project must be a android project or library")
        }
        this.project = project
        init()
    }

    /**
     * 初始化插件
     */
    private def init(){
        initExtensions()
        LogHelper.init(project)
        initTasks()
    }

    private def initExtensions(){
        config = project.project.extensions.create("dimensConfig", DimensConfigExtension)
    }

    /**
     * 创建任务
     */
    private def initTasks(){
        createDimensBuilderTask()
        createDimensPickerTask()
    }

    /**
     * 创建dimens提取任务
     * @return
     */
    private def createDimensPickerTask(){
        def task = project.task("pickDimens")
        task.group = "dimens"
        task.description = "提取资源文件中的dp/sp到dimens.xml中并自动替换"
        task.doLast {
            DimensPicker.extractDimens(PathUtils.getPathFromProject(project))
        }
    }

    /**
     *
     * @return
     */
    private def createDimensBuilderTask(){
        def fileBuilderTask = project.task("createMatchFiles")
        fileBuilderTask.group = "dimens"
        fileBuilderTask.description = "生成适配dimens"
        fileBuilderTask.doLast {
            def path = PathUtils.getPathFromProject(project)
            MatchFileMaker.createMatchFiles(path,config)
        }
    }
}