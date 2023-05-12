# DimensPlugin  

不再维护，请切换到https://github.com/zcys12173/ScreenMatchPlugin

1.自动提取资源文件中的dp，sp到dimens.xml中，  
2.  根据配置生成适配dimens文件  
## 添加依赖
最外层的build.gralde中添加 
buildscript {  
  dependencies {  
    classpath 'com.syc.plugin:resource:0.0.1'  
  }  
}  
![Image text](https://raw.githubusercontent.com/zcys12173/DimensPlugin/master/img-folder/build_gradle.png)
在app/module工程的build.gradle中添加下面内容  
apply plugin: "com.syc.plugin.DimensPlugin"  

## 生成适配文件配置  
以最小宽度规则生成dimens.xml文件    
app/module工程的build.gradle中添加下面内容  
dimensConfig {  
    baseSize=375//基准（值为屏幕的宽度dp值）  
    matchSizes=[320,360]//所需适配的尺寸（值为屏幕的宽度dp值）  
}  
![Image text](https://raw.githubusercontent.com/zcys12173/DimensPlugin/master/img-folder/app_build_gradle.png)
## 使用方法
1.提取xml中的dp sp值  
gradle app:pickDimens  
2.根据配置生成适配文件  
gradle app:createMatchFiles  

在右侧的gradle task列表中的dimens下可找到对应的task  
![Image text](https://raw.githubusercontent.com/zcys12173/DimensPlugin/master/img-folder/run_task.png)


