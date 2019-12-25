# DimensPlugin
 1.自动提取资源文件中的dp，sp到dimens.xml中，
 2.根据配置生成适配dimens文件
 
 #添加依赖
 buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.com.syc.plugin:resource-gradle-plugin:1.0.0"
  }
}
//在需要的工程中添加如下插件
apply plugin: "com.syc.plugin.DimensPlugin"

#生成适配文件配置
以最小宽度规则生成dimens.xml文件
dimensConfig {
    baseConfig=375//基准（值为屏幕的宽度dp值）
    matchSizes=[320,360]//所需适配的尺寸（值为屏幕的宽度dp值）
}

