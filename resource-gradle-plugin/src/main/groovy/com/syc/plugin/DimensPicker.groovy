package com.syc.plugin
/**
 * dimens工具
 */
class DimensPicker {
    /**
     * 从layout.xml中提取dimens到dimens.xml中
     * @param path 遍历启始路径
     */
    static def extractDimens(String path) {
        DimensIO.getInstance().init(path)
        File file = new File(path)
        if (!file.exists()) {
            return
        }
        traversalFile(file)
        DimensIO.getInstance().writeToDimens()
    }

    /**
     * 遍历文件处理.xml结尾的文件
     */
    private static def traversalFile(File file) {
        if (!file.exists()) {
            return
        }
        if (file.isDirectory()) {
            file.listFiles().each {
                traversalFile(it)
            }
        } else {
            if (file.getName().endsWith(".xml") && file.getName() != "dimens.xml") {
                handlerLayoutFile(file)
            }
        }
    }

    /**
     * 写入新的内容到layout.xml中
     * @param content
     */
    private static def handlerLayoutFile(File file) {
        LogHelper.w("处理文件：${file.name} 开始")
        String content = file.text
        def result = replaceDpByDependencies(content)
        file.text = result
        LogHelper.w("处理文件：${file.name} 结束")
    }


    /**
     * 替换xml中的dp值为Dimens.xml中的值（例：width="20dp" -> width="@dimen/dp_20"）
     * @param content
     * @return
     */
    private static def replaceDpByDependencies(String content) {
        def regEx = "\"[0-9]+\\.?[0-9]*(dp|sp)\""
        //	def regEx = "\"([1-9]|(0\\.))+[0-9]*(dp|sp)\""
        def matcher = content =~ regEx
        def isMatched = matcher.find()
        LogHelper.w("isMatched=$isMatched")
        if (isMatched) {
            def temp = content.substring(matcher.start(), matcher.end())
//            println("temp = $temp")
            if (temp.contains("dp")) {
                DimensIO.getInstance().checkDimensExist(temp.substring(1, temp.lastIndexOf("dp")), true)
                def dimenKey = temp.substring(1, temp.lastIndexOf("dp")).replace(".", "_")
                content = content.replace(temp, "\"@dimen/dp_$dimenKey\"")
            } else {
                DimensIO.getInstance().checkDimensExist(temp.substring(1, temp.lastIndexOf("sp")), false)
                def dimenKey = temp.substring(1, temp.lastIndexOf("sp")).replace(".", "_")
                content = content.replaceAll(temp, "\"@dimen/sp_$dimenKey\"")
            }
            return replaceDpByDependencies(content)
        } else {
            return content
        }
    }
}