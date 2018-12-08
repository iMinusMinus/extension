package ml.iamwhatiam.extension.mygen;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.List;
import java.util.Properties;
import java.util.Set;

/**
 * Use column comment as field javadoc.
 *
 * @author iMinusMinus
 */
public class CommentGenerator implements org.mybatis.generator.api.CommentGenerator {

    public void addConfigurationProperties(Properties properties) {
        //NO-OP
    }

    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if(StringUtility.stringHasValue(introspectedColumn.getRemarks())) {
            field.addJavaDocLine("/**");
            addRemark(field, introspectedColumn.getRemarks());
            field.addJavaDocLine(" */");
        }
    }

    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        //NO-OP
    }

    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if(StringUtility.stringHasValue(introspectedTable.getRemarks())) {
            topLevelClass.addJavaDocLine("/**");
            addRemark(topLevelClass, introspectedTable.getRemarks());
            topLevelClass.addJavaDocLine(" */");
        }
    }

    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        //NO-OP
    }

    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsNotDelete) {
        //NO-OP
    }

    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        //NO-OP
    }

    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        //NO-OP
    }

    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        //NO-OP
    }

    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        //NO-OP
    }

    public void addJavaFileComment(CompilationUnit compilationUnit) {
        //NO-OP
    }

    public void addComment(XmlElement xmlElement) {
        //NO-OP
    }

    public void addRootComment(XmlElement xmlElement) {
        //NO-OP
    }

    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {
        //NO-OP
    }

    public void addGeneralMethodAnnotation(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {
        //NO-OP
    }

    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {
        //NO-OP
    }

    public void addFieldAnnotation(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn, Set<FullyQualifiedJavaType> set) {
        //NO-OP
    }

    public void addClassAnnotation(InnerClass innerClass, IntrospectedTable introspectedTable, Set<FullyQualifiedJavaType> set) {
        //NO-OP
    }

    private void addRemark(JavaElement javaElement, String remark) {
        String[] remarkLines = remark.split(System.getProperty("line.separator"));
        for(String line : remarkLines) {
            javaElement.addJavaDocLine(" * " +  line);
        }

    }
}
