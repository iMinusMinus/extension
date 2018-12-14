# Open Source Project Extension

## MyBatis Generator Extension
[![Build Status](https://travis-ci.org/iMinusMinus/extension.png)](https://travis-ci.org/iMinusMinus/extension)
### How to Use
1. pom

Add extension as mybatis-generator-maven-plugin dependency.
```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.7</version>
                <dependencies>
                    <dependency>
                        <groupId>ml.iamwhatiam</groupId>
                        <artifactId>extension-mygen</artifactId>
                        <version>1.0-SNAPSHOT</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>
    </build>
```
2. generatorConfig

Change mybatis-generator-maven-plugin runtime.
```xml
<generatorConfiguration>
    <context id="custom" targetRuntime="ml.iamwhatiam.extension.mygen.IntrospectedTableTemplateImpl">
        <property name="author" value="iMinusMinus" />
        <!-- 
        <property name="templatePath" value="your own freemarker template path.">
        Template name must be one of Domain.ftl, DAO.ftl, DAOImpl.ftl, SqlMap.ftl or Mapper.ftl.
        </property>
         -->
        <!-- <plugin type="" /> -->
        <!-- <commentGenerator type="" /> -->
        <jdbcConnection driverClass="" connectionURL="" userId="" password="" />
        <!-- 
        <javaTypeResolver>
            <property name="" value="" />
        </javaTypeResolver>
        -->
        <javaModelGenerator targetPackage="" targetProject="src/main/java">
            <property name="rootClass" value="" />
        </javaModelGenerator>  
        <!--
        <sqlMapGenerator targetPackage="" targetProject="src/main/resources" />
        -->   
        <javaClientGenerator type="" targetPackage="" targetProject="src/main/java">
            <property name="rootInterface" value="" />
            <property name="annotationClass" value="" />
        </javaClientGenerator>
        <table tableName="" domainObjectName=""/>
        <table tableName="" />
    </context>
</generatorConfiguration>
```

[Related project:](https://github.com/mybatis/generator/wiki/Third-Party-Tools)
https://github.com/itfsw/mybatis-generator-plugin
