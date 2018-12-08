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

    public boolean validate(List<String> warnings) {
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
        return false;
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass,
                                              IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable,
                                              ModelClassType modelClassType) {
        return false;
    }

    protected void generateLombok(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if(StringUtility.isTrue(getProperties().getProperty(DATA))) {
            generate(topLevelClass, DATA);
            return;
        }
        generate(topLevelClass, GETTER);
        generate(topLevelClass, SETTER);
        if(StringUtility.isTrue(getProperties().getProperty(EQUALS_AND_HASHCODE))) {
            generate(topLevelClass, EQUALS_AND_HASHCODE);
        }
        generate(topLevelClass, TO_STRING);
    }

    protected void generate(TopLevelClass topLevelClass, String fqcn) {
        topLevelClass.addImportedType(fqcn);
        topLevelClass.getAnnotations().add(asAnnotation(fqcn));
    }

    private String asAnnotation(String fqcn) {
        return "@" + fqcn.substring(fqcn.lastIndexOf(".") + 1);
    }

}
