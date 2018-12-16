# MyBatis Generator Extension
[![Build Status](https://travis-ci.org/iMinusMinus/extension.png)](https://travis-ci.org/iMinusMinus/extension)
This extension handle freemarker template, then generate java or xml file.
It enable lombok as default, so you can make rid of plugins, like ToStringPlugin, EqualsAndHashCode.
It does not differ xKey, x, xWithBlobs, so that it generate only one model per table!
As example clause is too hard to read, I use model as parameter instead.
So, if you have difficult SQL, please write yourself.
You can change statement id by add properties under tag 'javaClientGenerator'.
## How to Test
```
mvn install
java ml.iamwhatiam.MainTest
mvn mybatis-generator:generate
```

## How to Use
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
            <property name="markerInterface" value="" />
            <property name="insertStatementId" value="insert" />
        </javaClientGenerator>
        <table tableName="" domainObjectName=""/>
        <table tableName="" />
    </context>
</generatorConfiguration>
```

[Related project:](https://github.com/mybatis/generator/wiki/Third-Party-Tools)
https://github.com/itfsw/mybatis-generator-plugin