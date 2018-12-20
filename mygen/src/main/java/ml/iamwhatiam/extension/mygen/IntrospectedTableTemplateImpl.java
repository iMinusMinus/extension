package ml.iamwhatiam.extension.mygen;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import lombok.Getter;
import lombok.Setter;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.codegen.AbstractGenerator;
import org.mybatis.generator.codegen.AbstractJavaClientGenerator;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.AbstractXmlGenerator;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.StringUtility;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * render template, not code.
 *
 * Example:
 * &#60;context targetRuntime="ml.iamwhatiam.extension.mygen.IntrospectedTableTemplateImpl"&#62;
 * ...
 * &#0;/context&#62;
 *
 * @author iMinusMinus
 */
public class IntrospectedTableTemplateImpl extends IntrospectedTable {

    private static final String CLASSPATH = "classpath:";

    private static final String FILE = "file:///";

    protected Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);

    protected List<AbstractJavaGenerator> javaModelGenerators;

    protected List<AbstractJavaGenerator> clientGenerators;

    protected AbstractXmlGenerator xmlGenerator;

    protected String xmlFileName;

    protected String xmlPackageName;

    @Getter
    private String javaModelPackage;

    @Getter
    private String javaClientInterfacePackage;

    @Getter
    private String javaClientImplementationPackage;

    public IntrospectedTableTemplateImpl() {
        this(TargetRuntime.MYBATIS3);
    }

    public IntrospectedTableTemplateImpl(TargetRuntime targetRuntime) {
        super(targetRuntime);
        javaModelGenerators = new ArrayList<AbstractJavaGenerator>();
        clientGenerators = new ArrayList<AbstractJavaGenerator>();
    }

    @Override
    public void initialize() {
        super.initialize();
        javaClientImplementationPackage = calculateJavaClientImplementationPackage();
        javaClientInterfacePackage = calculateJavaClientInterfacePackage();
        javaModelPackage = calculateJavaModelPackage();
        xmlPackageName = calculateSqlMapPackage();
        if(context.getJavaClientGeneratorConfiguration().getConfigurationType().contains("MAPPER")) {
            xmlFileName = calculateMyBatis3XmlMapperFileName();
        } else {
            xmlFileName = calculateIbatis2SqlMapFileName();
        }
        //custom template path
        String templatePath = context.getProperty("templatePath");
        boolean hasCustomTemplate = StringUtility.stringHasValue(templatePath);
        TemplateLoader defaultTemplateLoader = new ClassTemplateLoader(getClass(), "/" + getClass().getPackage().getName().replace(".", "/"));
        TemplateLoader[] delegates = new TemplateLoader[hasCustomTemplate ? 2 : 1];
        delegates[delegates.length - 1] = defaultTemplateLoader;
        if(hasCustomTemplate) {
            try {
                delegates[0] = newCustomTemplateLoader(templatePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        MultiTemplateLoader templateLoader = new MultiTemplateLoader(delegates);
        configuration.setTemplateLoader(templateLoader);
    }

    @Override
    public void calculateGenerators(List<String> warnings, ProgressCallback callback) {
        if (context.getJavaClientGeneratorConfiguration() == null) {
            return;
        }
        //iBatis or MyBatis, XML or Annotation
        //java model: example, primary key, with blob?
        AbstractJavaGenerator modelGenerator = new TemplateBasedModelGenerator(configuration, this);
        initializeAbstractGenerator(modelGenerator, warnings, callback);
        javaModelGenerators.add(modelGenerator);
        //java client: IBATIS, SPRING, GENERIC-CI, GENERIC-SI; XMLMAPPER(alias MAPPER), MIXEDMAPPER, ANNOTATEDMAPPER
        String type = context.getJavaClientGeneratorConfiguration().getConfigurationType();
        boolean legacy = !type.contains("MAPPER");
        if(!type.equals("ANNOTATEDMAPPER")) {
            xmlGenerator = new TemplateBasedXmlGenerator(configuration, this, legacy);
            initializeAbstractGenerator(xmlGenerator, warnings, callback);
        }
        AbstractJavaClientGenerator clientGenerator = new TemplateBasedJavaClientGenerator(configuration, this, xmlGenerator, legacy);
        initializeAbstractGenerator(clientGenerator, warnings, callback);
        clientGenerators.add(clientGenerator);
    }

    @Override
    public List<GeneratedJavaFile> getGeneratedJavaFiles() {
        List<GeneratedJavaFile> answer = new ArrayList<GeneratedJavaFile>();
        for(AbstractJavaGenerator javaModelGenerator : javaModelGenerators) {
            List<CompilationUnit> compilationUnits = javaModelGenerator.getCompilationUnits();
            for(CompilationUnit compilationUnit : compilationUnits) {
                answer.add(new GeneratedJavaFile(compilationUnit,
                        context.getJavaModelGeneratorConfiguration().getTargetProject(),
                        context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                        context.getJavaFormatter()));
            }
        }
        for(AbstractJavaGenerator javaClientGenerator : clientGenerators) {
            List<CompilationUnit> compilationUnits = javaClientGenerator.getCompilationUnits();
            for(CompilationUnit compilationUnit : compilationUnits) {
                answer.add(new GeneratedJavaFile(compilationUnit,
                        context.getJavaClientGeneratorConfiguration().getTargetProject(),
                        context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                        context.getJavaFormatter()));
            }
        }
        return answer;
    }

    @Override
    public List<GeneratedXmlFile> getGeneratedXmlFiles() {
        if(xmlGenerator == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(new GeneratedXmlFile(xmlGenerator.getDocument(), xmlFileName, xmlPackageName,
                              context.getSqlMapGeneratorConfiguration().getTargetProject(),
                              true, context.getXmlFormatter()));
    }

    protected void initializeAbstractGenerator(AbstractGenerator abstractGenerator, List<String> warnings,
            ProgressCallback progressCallback) {
        if (abstractGenerator == null) {
            return;
        }
        abstractGenerator.setContext(context);
        abstractGenerator.setIntrospectedTable(this);
        abstractGenerator.setProgressCallback(progressCallback);
        abstractGenerator.setWarnings(warnings);
    }

    private TemplateLoader newCustomTemplateLoader(String templatePath) throws Exception {
        TemplateLoader customTemplateLoader = null;
        if(templatePath.startsWith(CLASSPATH)) {
            customTemplateLoader = new ClassTemplateLoader(getClass().getClassLoader(), templatePath.substring(CLASSPATH.length()));
        } else if(templatePath.startsWith(FILE)) {
            customTemplateLoader = new FileTemplateLoader(new File(templatePath.substring(FILE.length())));
        } else {
            throw new RuntimeException("templatePath must start with 'classpath:' or 'file:///'");
        }
        return customTemplateLoader;
    }

    @Override
    public boolean isJava5Targeted() {
        return true;
    }

    @Override
    public int getGenerationSteps() {
        return javaModelGenerators.size() + clientGenerators.size() + (xmlGenerator == null ? 0 : 1);
    }

    @Override
    public boolean requiresXMLGenerator() {
        return xmlGenerator != null;
    }
}
