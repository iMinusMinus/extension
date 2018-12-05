package ml.iamwhatiam.extension.mygen;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.List;

/**
 * Lombok plugin can generate annotation on model class, and you can get out of EqualsHashCodePlugin, ToStringPlugin.
 *
 * @author iMinusMinus
 */
public class LombokPlugin extends PluginAdapter {

    private static final String GETTER = "lombok.Getter";

    private static final String SETTER = "lombok.Setter";

    private static final String EQUALS_AND_HASHCODE = "lombok.EqualsAndHashCode";

    private static final String TO_STRING = "lombok.ToString";

    private static final String DATA = "lombok.Data";

    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        generateLombok(topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        generateLombok(topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        generateLombok(topLevelClass, introspectedTable);
        return true;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass,
                                              IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable,
                                              ModelClassType modelClassType) {
        return !StringUtility.isTrue(getProperties().getProperty(DATA)) && !StringUtility.isTrue(getProperties().getProperty(GETTER));
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass,
                                              IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable,
                                              ModelClassType modelClassType) {
        return !StringUtility.isTrue(getProperties().getProperty(DATA)) && ! StringUtility.isTrue(getProperties().getProperty(SETTER));
    }

    protected void generateLombok(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if(StringUtility.isTrue(getProperties().getProperty(DATA))) {
            topLevelClass.addImportedType(DATA);
            topLevelClass.getAnnotations().add(getSimpleName(DATA));
            return;
        }
        generateGetter(topLevelClass, introspectedTable);
        generateSetter(topLevelClass, introspectedTable);
        generateToString(topLevelClass, introspectedTable);
    }

    protected void generateGetter(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if(StringUtility.isTrue(getProperties().getProperty(GETTER))) {
            topLevelClass.addImportedType(GETTER);
            topLevelClass.getAnnotations().add(getSimpleName(GETTER));
        }
    }

    protected void generateSetter(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if(StringUtility.isTrue(getProperties().getProperty(SETTER))) {
            topLevelClass.addImportedType(SETTER);
            topLevelClass.getAnnotations().add(getSimpleName(SETTER));
        }
    }

    protected void generateEqualsAndHashCode(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if(StringUtility.isTrue(getProperties().getProperty(EQUALS_AND_HASHCODE))) {
            topLevelClass.addImportedType(EQUALS_AND_HASHCODE);
            topLevelClass.getAnnotations().add(getSimpleName(EQUALS_AND_HASHCODE));
        }
    }

    protected void generateToString(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if(StringUtility.isTrue(getProperties().getProperty(TO_STRING))) {
            topLevelClass.addImportedType(TO_STRING);
            topLevelClass.getAnnotations().add(getSimpleName(TO_STRING));
        }
    }

    private String getSimpleName(String fqcn) {
        return fqcn.substring(fqcn.lastIndexOf(".") + 1);
    }

}
