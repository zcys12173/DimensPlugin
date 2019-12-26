package com.syc.plugin

class DimensIO {
    private String dimensContent = ""
    private String dimensPath = ""
    private static DimensIO instance
    private DimensIO() {}

    static def getInstance(){
        if(instance == null){
            instance = new DimensIO()
        }
        return instance
    }

    def init(String path) {
        dimensPath = "${path}/values/dimens.xml"
    }

    def checkDimensExist(String dimenValue, boolean isDp) {
        LogHelper.w("checkDimensExist:$dimensContent")
        if (dimensContent.isEmpty()) {
            LogHelper.w("readDimens")
            readDimens()
        }
        def key = dimenValue.replace(".", "_")
        if (isDp) {//处理dp
            def dimenItem = "<dimen name=\"dp_$key\">" + dimenValue + "dp</dimen>"
            if (!dimensContent.contains(dimenItem)) {
                def s = "\n     $dimenItem"
                appendDimens(s)
            }
        } else {//处理sp
            def dimenItem = "<dimen name=\"sp_$key\">" + dimenValue + "sp</dimen>"
            println("new dimen item : $dimenItem")
            if (!dimensContent.contains(dimenItem)) {
                def s = "\n     $dimenItem"
                appendDimens(s)
            }
        }
    }

    /**
     * 获取dimens.xml内容
     */
    private def readDimens() {
        if (dimensPath.isEmpty()) {
            return
        }
        File file = new File(dimensPath)
        if (!file.exists()) {//如果没有dimens.xml文件，手动创建一个文件
            File parentFile = file.parentFile
            if(!parentFile.exists()){
                parentFile.mkdirs()
            }
            StringBuilder sb = new StringBuilder()
            sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
            sb.append("<resources>\n")
            sb.append("</resources>")
            file.text = sb.toString()
            file.createNewFile()
        }
        dimensContent = file.text
    }


    private def appendDimens(String value) {
        int index = dimensContent.lastIndexOf("</resources>") - 1
        dimensContent = dimensContent.substring(0, index).concat(value).concat(dimensContent.substring(index))
    }

    def writeToDimens(){
        LogHelper.w("开始写入新内容到dimens.xml文件,内容：$dimensContent")
        if (dimensPath.isEmpty()) {
            return
        }
//        LogHelper.e("$dimensContent")
//        int index = dimensContent.indexOf("</resources>")
//        LogHelper.e("$index")
//        LogHelper.w("w$index")
//        dimensContent = dimensContent.substring(0, index).concat('\n').concat(dimensContent.substring(index))
        File file = new File(dimensPath)
        LogHelper.w("${file.exists()} ---${dimensContent.isEmpty()}}")
        if (file.exists() && !dimensContent.isEmpty()) {
            file.text = dimensContent
        }
        LogHelper.w("完成写入新内容到dimens.xml文件")
    }

}