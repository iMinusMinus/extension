package ml.iamwhatiam.extension.mygen;

import freemarker.template.Configuration;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;

import java.io.StringWriter;
import java.io.Writer;
import java.util.*;

/**
 * use freemarker template generate DAO (and DAOImpl).
 *
 * @author iMinusMinus
 */
public class TemplateBasedJavaClientGenerator extends AbstractJavaClientGenerator {

    private final Configuration cfg;

    private final AbstractXmlGenerator xmlGenerator;

    private final boolean legacy;

    public TemplateBasedJavaClientGenerator(Configuration cfg, IntrospectedTable introspectedTable,
                                            AbstractXmlGenerator xmlGenerator, boolean legacy) {
        super(xmlGenerator != null);
        this.cfg = cfg;
        this.introspectedTable = introspectedTable;
        this.xmlGenerator = xmlGenerator;
        this.legacy = legacy;
    }

    @Override
    public AbstractXmlGenerator getMatchedXMLGenerator() {
        return xmlGenerator;
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        List<CompilationUnit> compilationUnits = new ArrayList<CompilationUnit>();
        Writer out = new StringWriter();
        FullyQualifiedJavaType interfaceType = null;
        try {
            if(legacy) {
                interfaceType = new FullyQualifiedJavaType(introspectedTable.getDAOInterfaceType());
            } else {
                interfaceType = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());
            }
            String rootInterface = context.getJavaClientGeneratorConfiguration().getProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
            Set<FullyQualifiedJavaType> superInterfaces =new HashSet<FullyQualifiedJavaType>();
            if(StringUtility.stringHasValue(rootInterface)) {
                FullyQualifiedJavaType superInterface = new FullyQualifiedJavaType(rootInterface);
                superInterfaces.add(superInterface);
            }
            cfg.getTemplate("DAO.ftl").process(introspectedTable, out);
            compilationUnits.add(new SimpleCompilationUnit(interfaceType, out.toString(), null, superInterfaces));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(!legacy) {
            return compilationUnits;
        }
        Writer writer = new StringWriter();
        try {
            FullyQualifiedJavaType implementationType = new FullyQualifiedJavaType(introspectedTable.getDAOImplementationType());
            FullyQualifiedJavaType superClass = new FullyQualifiedJavaType("org.springframework.orm.ibatis.support.SqlMapClientDaoSupport");
            cfg.getTemplate("DAOImpl.ftl").process(introspectedTable, writer);
            Set<FullyQualifiedJavaType> superInterfaces =new HashSet<FullyQualifiedJavaType>();
            superInterfaces.add(interfaceType);
            compilationUnits.add(new SimpleCompilationUnit(implementationType, writer.toString(), superClass, superInterfaces));
        } catch (Exception ignore) {
            //NO-OP
        }
        return compilationUnits;
    }
}
