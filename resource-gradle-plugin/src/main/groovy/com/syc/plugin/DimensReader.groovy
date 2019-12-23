package com.syc.plugin

class DimensReader {
    private String dimensContent = ""
    private String dimensPath = ""
    private static DimensReader instance
    private DimensReader() {}

    static def getInstance(){
        if(instance == null){
            instance = new DimensReader()
        }
        return instance
    }

    def init(String path) {
        dimensPath = "${path}/values/dimens.xml"
    }

    def checkDimensExist(String dimenValue, boolean isDp) {
        if (dimensContent.isEmpty()) {
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
            def dimenItem = "<dimen name=\"text_size_$key\">" + dimenValue + "sp</dimen>"
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
        if (file.exists()) {
            dimensContent = file.text
        }
    }


    private def appendDimens(String value) {
        int index = dimensContent.lastIndexOf("</resources>")
        dimensContent = dimensContent.substring(0, index).concat(value).concat(dimensContent.substring(index))
        LogHelper.d(dimensContent)
    }

    def writeToDimens(){
        if (dimensPath.isEmpty()) {
            return
        }
        File file = new File(dimensPath)
        if (file.exists()) {
            file.text = dimensContent
        }

    }

}