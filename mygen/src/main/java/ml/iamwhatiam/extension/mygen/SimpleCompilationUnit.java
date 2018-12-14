package ml.iamwhatiam.extension.mygen;

import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

import java.lang.reflect.Modifier;
import java.util.*;

/**
 * simple java bean.
 *
 * @author iMinusMinus
 */
public class SimpleCompilationUnit implements CompilationUnit {

    /**
     * java type
     */
    private final FullyQualifiedJavaType type;

    /**
     * java file content
     */
    private final String formattedContent;

    /**
     * super class
     */
    private final FullyQualifiedJavaType superClass;

    /**
     * super interfaces
     */
    private final Set<FullyQualifiedJavaType> superInterfaceTypes;

    /**
     * java type needs to import
     */
    private Set<FullyQualifiedJavaType> importedTypes;

    private Set<String> staticImports;

    private List<String> fileCommentLines;

    /**
     * class modifier
     */
    private int modifier;

    public SimpleCompilationUnit(String formattedContent) {
        this(null, formattedContent, null, Collections.<FullyQualifiedJavaType>emptySet());
    }

    public SimpleCompilationUnit(FullyQualifiedJavaType type, String formattedContent,
                                 FullyQualifiedJavaType superClass, Set<FullyQualifiedJavaType> superInterfaceTypes) {
        this.type = type;
        this.formattedContent = formattedContent;
        this.superClass = superClass;
        this.superInterfaceTypes = superInterfaceTypes;
        importedTypes = new HashSet<FullyQualifiedJavaType>();
        importIfPossible(type, superClass);
        for(FullyQualifiedJavaType jtype : superInterfaceTypes) {
            importIfPossible(type, jtype);
        }
        staticImports = new HashSet<String>();
        fileCommentLines = new ArrayList<String>();
    }

    public String getFormattedContent() {
        return formattedContent;
    }

    public Set<FullyQualifiedJavaType> getImportedTypes() {
        return importedTypes;
    }

    public Set<String> getStaticImports() {
        return staticImports;
    }

    public FullyQualifiedJavaType getSuperClass() {
        return superClass;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }

    public boolean isJavaInterface() {
        return (modifier & Modifier.INTERFACE) != 0;
    }

    public boolean isJavaEnumeration() {
        return (modifier & 0x00004000) != 0;
    }

    public Set<FullyQualifiedJavaType> getSuperInterfaceTypes() {
        return superInterfaceTypes;
    }

    public FullyQualifiedJavaType getType() {
        return type;
    }

    public void addImportedType(FullyQualifiedJavaType importedType) {
        importedTypes.add(importedType);
    }

    public void addImportedTypes(Set<FullyQualifiedJavaType> importedTypes) {
        this.importedTypes.addAll(importedTypes);
    }

    public void addStaticImport(String staticImport) {
        staticImports.add(staticImport);
    }

    public void addStaticImports(Set<String> staticImports) {
        this.staticImports.addAll(staticImports);
    }

    public void addFileCommentLine(String commentLine) {
        fileCommentLines.add(commentLine);
    }

    public List<String> getFileCommentLines() {
        return fileCommentLines;
    }

    private void importIfPossible(FullyQualifiedJavaType thisType, FullyQualifiedJavaType dependencyType) {
        if(thisType != null && dependencyType != null && !thisType.getPackageName().equals(dependencyType.getPackageName())){
            addImportedType(dependencyType);
        }
    }
}
