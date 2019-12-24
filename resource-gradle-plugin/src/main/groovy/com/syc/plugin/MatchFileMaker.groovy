package com.syc.plugin


/**
 * 生成适配后的Dimens.xml文件
 */
class MatchFileMaker {
    private static def resPath
    private static def baseScreenWidth = 375

    static def createMatchFiles(String resourcePath ,DimensConfigExtension config) {
        println(config.toString())
        if(config.baseSize == 0){
            LogHelper.e("没有配置基准尺寸，请设置baseSize")
            return
        }
        if(config.matchSizes.size() == 0){
            LogHelper.e("没有配置适配尺寸，请设置matchSizes")
            return
        }
        println(config.baseSize)
        config.matchSizes.each {
            println(it)
        }
        List<Integer> configs = config.matchSizes
        baseScreenWidth = config.baseSize
        resPath = resourcePath
        configs.each { value ->
            def matchFileDirPath = "${resPath}/values-sw${value}dp"
            println(matchFileDirPath)
            File dirFile = new File(matchFileDirPath)
            if (!dirFile.exists()) {
                dirFile.mkdirs()
            }
            File dimensFile = new File(dirFile, 'dimens.xml')
            float ratio = baseScreenWidth/value
            writeContent2File(dimensFile, ratio)
        }
    }

    private static def writeContent2File(File file, float ratio) {
        StringBuffer sb = new StringBuffer('<?xml version="1.0" encoding="utf-8"?>')
        sb.append('\n')
        sb.append('<resources>')
        sb.append('\n')
        def baseDimensPath = "${resPath}/values/dimens.xml"
        def baseFile = new File(baseDimensPath)
        if (!baseFile.exists()) {
            return
        }
        XmlParser parser = new XmlParser()
        def dimenValues = parser.parse(baseFile)
        dimenValues.each {
            String value = it.text()
            String name = it.attribute('name')
            sb.append("    <dimen name=\"${name}\">")
            def regEx = "[0-9]+\\.?[0-9]*"
            def matcher = value =~ regEx
            def isMatched = matcher.find()
//            println("是否找到匹配到相关内容：$isMatched")
            if (isMatched) {
//                println(value.substring(matcher.start(), matcher.end()))
                def dimenValue = Float.parseFloat(value.substring(matcher.start(), matcher.end()))
                def realValue = dimenValue/ratio
                sb.append(String.format('%.4f',realValue))
                def dimenUnit = value.substring(matcher.end())
                sb.append(dimenUnit)
            }
            sb.append("</dimen>")
            sb.append('\n')
        }
        sb.append('</resources>')
        file.text = sb.toString()
    }
}