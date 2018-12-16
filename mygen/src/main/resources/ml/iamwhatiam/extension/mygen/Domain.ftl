<#--
<#assign javaModelPackage = context.javaModelGeneratorConfiguration.targetPackage>
-->
package ${javaModelPackage};

<#assign superClass>
<#if tableConfiguration.properties.rootClass??>
<#t>${tableConfiguration.properties.rootClass}
<#elseif context.javaModelGeneratorConfiguration.properties.rootClass??>
<#t>${context.javaModelGeneratorConfiguration.properties.rootClass}
</#if>
</#assign>
<#if superClass?trim?length gt 0>
import ${superClass};
<#else>
import java.io.Serializable;
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
<#t>${baseRecordType?substring(baseRecordType?last_index_of(".") + 1)}
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
public class ${domainObjectName} <#if superClass?trim?length gt 0>extends ${superClass?substring(superClass?last_index_of(".") + 1)}<#else>implements Serializable</#if> {

    private static final Long serialVersionUID = 1L;

<#list allColumns as column>
    /**
     * <#if column.remarks??>${column.remarks}</#if>
     */
    private ${column.fullyQualifiedJavaType.shortNameWithoutTypeArguments} ${column.javaProperty};

</#list>

}