package ${javaClientInterfacePackage};

import java.util.List;

<#assign props = context.javaClientGeneratorConfiguration.properties>
<#assign rootInterfaceName>
<#if tableConfiguration.properties.rootInterface??>
<#t>${tableConfiguration.properties.rootInterface}
<#elseIf props.rootInterface??>
<#t>${props.rootInterface}
</#if>
</#assign>
<#assign rootInterface = rootInterfaceName?trim>
<#if rootInterface?length gt 0>
import ${rootInterface};

</#if>
<#assign domainObjectName>
<#if tableConfiguration.domainObjectName??>
<#t>${tableConfiguration.domainObjectName}
<#else>
<#t>${baseRecordType?substring(baseRecordType?lastIndexOf(".") + 1)}
</#if>
</#assign>
<#assign javaClientInterfaceName>
<#if tableConfiguration.mapperName??>
<#t>${tableConfiguration.mapperName}
<#elseIf context.javaClientGeneratorConfiguration.configurationType?contains("MAPPER")>
<#t>${myBatis3JavaMapperType?substring(myBatis3JavaMapperType?lastIndexOf(".") + 1)}
<#else>
<#t>${DAOInterfaceType?substring(DAOInterfaceType?lastIndexOf(".") + 1)}
</#if>
</#assign>
<#if !baseRecordType?contains(javaClientInterfacePackage)>
import ${javaModelPackage}.${domainObjectName};

</#if>
<#if props.markerInterface?? && props.markerInterface != rootInterface>
import ${props.markerInterface};

</#if>
import org.apache.ibatis.annotations.Param;
<#if props.annotationClass??>
import ${props.annotationClass};
</#if>

<#if rootInterface?length gt 0 && props.markerInterface?? && props.markerInterface != rootInterface>
<#assign superInterfaces = [rootInterface,props.markerInterface]>
<#elseIf  rootInterface?length gt 0>
<#assign superInterfaces=[rootInterface]>
<#elseIf props.markerInterface??>
<#assign superInterfaces=[props.markerInterface]>
</#if>

/**
 * @author <#if context.properties.author??>${context.properties.author}<#else>mbg.generated</#if>
 * @date ${.now?date}
 */
<#-- mybtis-generator:
                    {domainObject}WithBLOBs extends {domainObject}, {domainObject} extends {domainObject]Key
                    Selective: insert or update when property not null
-->
<#if props.annotationClass??>
@${props.annotationClass?substring(props.annotationClass?lastIndexOf(".") + 1)}
</#if>
public interface ${javaClientInterfaceName} <#if superInterfaces??>extends <#list superInterfaces as superInterface>${superInterface?substring(superInterface?lastIndexOf(".") + 1)}<#sep>, </#list></#if>{

<#if tableConfiguration.insertStatementEnabled>
    int ${props.insertStatementId ! insertStatementId}(${domainObjectName} record);

    int ${props.insertSelectiveStatementId ! insertSelectiveStatementId}(${domainObjectName} record);

</#if>
<#if tableConfiguration.deleteByPrimaryKeyStatementEnabled>
    int ${props.deleteByPrimaryKeyStatementId ! deleteByPrimaryKeyStatementId}(${domainObjectName} key);

</#if>
<#if tableConfiguration.deleteByExampleStatementEnabled>
    int ${props.deleteByExampleStatementId ! deleteByExampleStatementId}(${domainObjectName} example);

</#if>
<#if tableConfiguration.updateByPrimaryKeyStatementEnabled>
    int ${props.updateByPrimaryKeyStatementId ! updateByPrimaryKeyStatementId}(@Param("record") ${domainObjectName} record, @Param("example") ${domainObjectName} example);

    int ${props.updateByPrimaryKeySelectiveStatementId ! updateByPrimaryKeySelectiveStatementId}(@Param("record") ${domainObjectName} record, @Param("example") ${domainObjectName} example);

</#if>
<#if tableConfiguration.updateByExampleStatementEnabled>
    int ${props.updateByExampleSelectiveStatementId ! updateByExampleSelectiveStatementId}(@Param("record") ${domainObjectName} record, @Param("example") ${domainObjectName} example);
<#--
    int ${props.updateByPrimaryKeyWithBLOBsStatementId ! updateByPrimaryKeyWithBLOBsStatementId}(@Param("record") ${domainObjectName} record, @Param("example") ${domainObjectName} example);
-->

    int ${props.updateByExampleStatementId ! updateByExampleStatementId}(@Param("record") ${domainObjectName} record, @Param("example") ${domainObjectName} example);

</#if>
<#-- Domain = BlobRecord
    int ${props.updateByExampleWithBLOBsStatementId ! updateByExampleWithBLOBsStatementId}(@Param("record") ${domainObjectName} record, @Param("example") ${domainObjectName} example);
-->
<#if tableConfiguration.selectByPrimaryKeyStatementEnabled>
    ${domainObjectName} ${props.selectByPrimaryKeyStatementId ! selectByPrimaryKeyStatementId}(${domainObjectName} key);

</#if>
<#if tableConfiguration.countByExampleStatementEnabled>
<#if context.javaClientGeneratorConfiguration.configurationType == 'ANNOTATEDMAPPER'>
    @@SelectProvider(type=${domainObjectName}SqlProvider.class, method="countByExample")
</#if>
    long ${props.countByExampleStatementId ! countByExampleStatementId}(${domainObjectName} example);

</#if>
<#if tableConfiguration.selectByExampleStatementEnabled>
    List<${domainObjectName}> ${props.selectByExampleStatementId ! selectByExampleStatementId}(${domainObjectName} example);

    List<${domainObjectName}> ${props.selectByExampleWithBLOBsStatementId ! selectByExampleWithBLOBsStatementId}(${domainObjectName} example);
<#--
    List<${domainObjectName}> <#if props.selectAllStatementId??>${props.selectAllStatementId}<#else>${selectAllStatementId}</#if>();
-->
</#if>
}