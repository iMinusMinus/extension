# Open Source Project Plugins

## mygen
1. pom
```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.mybatis.generator</groupId>
                <artifactId>mybatis-generator-maven-plugin</artifactId>
                <version>1.3.7</version>
                <dependencies>
                    <dependency>
                        <groupId>>ml.iamwhatiam</groupId>
                        <artifactId>mygen</artifactId>
                        <version>1.0-SNAPSHOT</version>
                    </dependency>
                </dependencies>
            </plugin>
        </plugins>
    </build>
```
2. configurationFile
```xml
<context id="cathay">
    <plugin type="ml.iamwhatiam.extension.mygen.LombokPlugin" />
    <commentGenerator type="ml.iamwhatiam.extension.mygen.CommentGenerator" />
    <!-- other -->
</context> 
```