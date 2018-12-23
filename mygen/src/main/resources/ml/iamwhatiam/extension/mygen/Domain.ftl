<#--
<#assign javaModelPackage = context.javaModelGeneratorConfiguration.targetPackage>
-->
<#assign props = context.javaModelGeneratorConfiguration.properties>
<#function snake2camel snakeCase>
    <#local words = snakeCase?split("_")>
    <#local camelCase = "">
    <#list words as word>
        <#if word?index != 0>
            <#local camelCase += word?cap_first>
        <#else>
            <#local camelCase += word>
        </#if>
    </#list>
    <#return camelCase>
</#function>
package ${javaModelPackage};

<#assign superClass>
<#if tableConfiguration.properties.rootClass??>
<#t>${tableConfiguration.properties.rootClass}
<#elseif props.rootClass??>
<#t>${props.rootClass}
</#if>
</#assign>
<#if superClass?trim?length gt 0>
import ${superClass};
<#else>
import java.io.Serializable;
</#if>
<#if props.jpaAnnotation??>
import javax.persistence.Entity;
import javax.persistence.Table;
<#list allColumns as column>
<#if props.jpaAnnotation == 'ALWAYS' || (props.jpaAnnotation == 'IMPLIED' && snake2camel(column.actualColumnName) != column.javaProperty)>
import javax.persistence.Column;
<#break>
</#if>
</#list>
</#if>
<#if props.beanValidationAnnotation??>
<#assign notNullNotImported=true>
<#assign sizeNotImported=true>
<#list allColumns as column>
<#if !column.nullable && notNullNotImported>
import javax.validation.constraints.NotNull;
<#assign notNullNotImported = false>
</#if>
<#if !column.nullable && sizeNotImported && column.fullyQualifiedJavaType == "java.lang.String">
import javax.validation.constraints.Size;
<#assign sizeNotImported = false>
</#if>
<#if !notNullNotImported && !sizeNotImported>
<#break>
</#if>
</#list>
</#if>

<#assign imports>
<#list allColumns as column>
<#if column.fullyQualifiedJavaType.packageName != "java.lang" && column.fullyQualifiedJavaType.packageName != javaModelPackage>
<#t>${column.fullyQualifiedJavaType},<#t>
</#if>
</#list>
</#assign>
<#if imports?trim?length gt 0>
<#assign importTypes = imports?split(",")?sort>
<#list importTypes as importType>
<#if importType?trim?length gt 0 && importTypes?seq_index_of(importType) == importType_index>
import ${importType};
</#if>
</#list>
</#if>

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

<#assign domainObjectName>
<#if tableConfiguration.domainObjectName??>
<#t>${tableConfiguration.domainObjectName}
<#else>
<#t>${baseRecordType?keep_after_last(".")}
</#if>
</#assign>
/**
 * <#if remarks??>${remarks}</#if>
 *
 * @author <#if context.properties.author??>${context.properties.author}<#else>mbg.generated</#if>
 * @date ${.now?date}
 */
@Getter
@Setter
@ToString
<#if props.jpaAnnotation??>
@Entity
@Table
</#if>
public class ${domainObjectName} <#if superClass?trim?length gt 0>extends ${superClass?keep_after_last(".")}<#else>implements Serializable</#if> {

    private static final Long serialVersionUID = 1L;

<#list allColumns as column>
    /**
     * <#if column.remarks??>${column.remarks}</#if>
     */
    <#if props.jpaAnnotation??>
    <#if props.jpaAnnotation == 'ALWAYS'>
    @Column(name = "${column.actualColumnName}")
    <#elseif props.jpaAnnotation == 'IMPLIED' && snake2camel(column.actualColumnName?lower_case) != column.javaProperty>
    @Column(name = "${column.actualColumnName}")
    </#if>
    </#if>
    <#if props.beanValidationAnnotation??>
    <#if !column.nullable>
    @NotNull
    </#if>
    <#if column.length gt 0 && column.fullyQualifiedJavaType == "java.lang.String">
    @Size(${column.length})
    </#if>
    </#if>
    private ${column.fullyQualifiedJavaType.shortNameWithoutTypeArguments} ${column.javaProperty};

</#list>

}