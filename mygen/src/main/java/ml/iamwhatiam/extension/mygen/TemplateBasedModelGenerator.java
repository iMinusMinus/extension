package ml.iamwhatiam.extension.mygen;

import freemarker.template.Configuration;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.codegen.AbstractJavaGenerator;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * use freemarker template generate {@code CompilationUnit}
 *
 * @author iMinusMinus
 */
public class TemplateBasedModelGenerator extends AbstractJavaGenerator {

    private Configuration cfg;

    public TemplateBasedModelGenerator(Configuration cfg, IntrospectedTable introspectedTable) {
        this.cfg = cfg;
        this.introspectedTable = introspectedTable;
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        FullyQualifiedJavaType superClass = null;
        String rootClass = getRootClass();
        if(rootClass != null) {
            superClass = new FullyQualifiedJavaType(rootClass);
        }
        Set<FullyQualifiedJavaType> superInterfaces = new HashSet<FullyQualifiedJavaType>();
        if(superClass == null) {
            superInterfaces.add(new FullyQualifiedJavaType("java.io.Serializable"));
        }
        Writer out = new StringWriter();
        try {
            cfg.getTemplate("Domain.ftl").process(introspectedTable, out);
            return Arrays.<CompilationUnit>asList(new SimpleCompilationUnit(type, out.toString(), superClass, superInterfaces));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
