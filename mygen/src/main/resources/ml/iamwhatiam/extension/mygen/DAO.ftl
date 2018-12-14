package ${javaClientInterfacePackage};

import java.util.List;

<#assign props = context.javaClientGeneratorConfiguration.properties>
<#assign rootInterface>
<#if tableConfiguration.properties.rootInterface??>
<#t>${tableConfiguration.properties.rootInterface}
<#elseif props.rootInterface??>
<#t>${props.rootInterface}
</#if>
</#assign>
<#if rootInterface?trim?length != 0>
import ${rootInterface};

</#if>
<#assign domainObjectName>
<#if tableConfiguration.domainObjectName??>
<#t>${tableConfiguration.domainObjectName}
<#else>
<#t>${baseRecordType?substring(baseRecordType?last_index_of(".") + 1)}
</#if>
</#assign>
<#assign javaClientInterfaceName>
<#if tableConfiguration.mapperName??>
<#t>${tableConfiguration.mapperName}
<#elseif context.javaClientGeneratorConfiguration.configurationType?contains("MAPPER")>
<#t>${myBatis3JavaMapperType?substring(myBatis3JavaMapperType?last_index_of(".") + 1)}
<#else>
<#t>${DAOInterfaceType?substring(DAOInterfaceType?last_index_of(".") + 1)}
</#if>
</#assign>
<#if !baseRecordType?contains(javaClientInterfacePackage)>
import ${javaModelPackage}.${domainObjectName};

</#if>
<#if props.annotationClass??>
import ${props.annotationClass};

</#if>
import org.apache.ibatis.annotations.Param;

/**
 * @author <#if context.properties.author??>${context.properties.author}<#else>mbg.generated</#if>
 * @date ${.now?date}
 */
<#-- mybtis-generator:
                    {domainObject}WithBLOBs extends {domainObject}, {domainObject} extends {domainObject]Key
                    Selective: insert or update when property not null
-->
<#if props.annotationClass??>
@${props.annotationClass?substring(props.annotationClass?last_index_of(".") + 1)}
</#if>
public interface ${javaClientInterfaceName} <#if rootInterface?trim?length != 0>extends ${rootInterface?substring(rootInterface?last_index_of(".") + 1)} </#if>{

    int ${props.insertStatementId ! insertStatementId}(${domainObjectName} record);

    int ${props.insertSelectiveStatementId ! insertSelectiveStatementId}(${domainObjectName} record);

    int ${props.deleteByPrimaryKeyStatementId ! deleteByPrimaryKeyStatementId}(${domainObjectName} key);

    int ${props.deleteByExampleStatementId ! deleteByExampleStatementId}(${domainObjectName} example);

    int ${props.updateByPrimaryKeyStatementId ! updateByPrimaryKeyStatementId}(@Param("record") ${domainObjectName} record, @Param("example") ${domainObjectName} example);

    int ${props.updateByExampleSelectiveStatementId ! updateByExampleSelectiveStatementId}(@Param("record") ${domainObjectName} record, @Param("example") ${domainObjectName} example);
<#--
    int ${props.updateByPrimaryKeyWithBLOBsStatementId ! updateByPrimaryKeyWithBLOBsStatementId}(@Param("record") ${domainObjectName} record, @Param("example") ${domainObjectName} example);
-->

    int ${props.updateByExampleStatementId ! updateByExampleStatementId}(@Param("record") ${domainObjectName} record, @Param("example") ${domainObjectName} example);

    int ${props.updateByPrimaryKeySelectiveStatementId ! updateByPrimaryKeySelectiveStatementId}(@Param("record") ${domainObjectName} record, @Param("example") ${domainObjectName} example);
<#-- Domain = BlobRecord
    int ${props.updateByExampleWithBLOBsStatementId ! updateByExampleWithBLOBsStatementId}(@Param("record") ${domainObjectName} record, @Param("example") ${domainObjectName} example);
-->

    ${domainObjectName} ${props.selectByPrimaryKeyStatementId ! selectByPrimaryKeyStatementId}(${domainObjectName} key);

<#if context.javaClientGeneratorConfiguration.configurationType == 'ANNOTATEDMAPPER'>
    @@SelectProvider(type=${domainObjectName}SqlProvider.class, method="countByExample")
</#if>
    long ${props.countByExampleStatementId ! countByExampleStatementId}(${domainObjectName} example);

    List<${domainObjectName}> ${props.selectByExampleStatementId ! selectByExampleStatementId}(${domainObjectName} example);

    List<${domainObjectName}> ${props.selectByExampleWithBLOBsStatementId ! selectByExampleWithBLOBsStatementId}(${domainObjectName} example);
<#--
    List<${domainObjectName}> <#if props.selectAllStatementId??>${props.selectAllStatementId}<#else>${selectAllStatementId}</#if>();
-->
}