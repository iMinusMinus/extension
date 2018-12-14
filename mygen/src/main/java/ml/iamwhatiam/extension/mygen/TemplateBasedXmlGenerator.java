package ml.iamwhatiam.extension.mygen;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.codegen.AbstractXmlGenerator;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * use freemarker template generate SqlMap or Mapper
 *
 * @author iMinusMinus
 */
public class TemplateBasedXmlGenerator extends AbstractXmlGenerator {

    private Configuration cfg;

    /**
     * legacy ? iBatis : MyBatis
     */
    private boolean legacy;

    public TemplateBasedXmlGenerator(Configuration cfg, IntrospectedTable introspectedTable, boolean legacy) {
        this.cfg = cfg;
        this.introspectedTable = introspectedTable;
        this.legacy = legacy;
    }

    @Override
    public Document getDocument() {
        String template = legacy ? "SqlMap.ftl" : "Mapper.ftl";
        final Writer out = new StringWriter();
        try {
            cfg.getTemplate(template).process(introspectedTable, out);
            return new Document() {

                @Override
                public String getFormattedContent() {
                    return out.toString();
                }
            };
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
