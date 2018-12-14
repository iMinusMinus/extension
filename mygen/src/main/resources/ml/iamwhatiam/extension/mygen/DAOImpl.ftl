<#--
<#assign javaClientImplementationPackage>
<#if context.javaClientGeneratorConfiguration.implementationPackage??>
<#t>${context.javaClientGeneratorConfiguration.implementationPackage}
<#else>
<#t>${context.javaClientGeneratorConfiguration.targetPackage}
</#if>
</#assign>
-->
package ${javaClientImplementationPackage};
<#assign domainObjectName>
<#if tableConfiguration.domainObjectName??>
<#t>${tableConfiguration.domainObjectName}
<#else>
<#t>${baseRecordType?substring(baseRecordType?last_index_of(".") + 1)}
 </#if>
</#assign>
<#if !baseRecordType?contains(javaClientImplementationPackage)>
import ${javaModelPackage}.${domainObjectName};
</#if>
<#if javaClientInterfacePackage != javaClientImplementationPackage>
import ${javaClientInterfacePackage}.${domainObjectName}DAO;
</#if>

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * @author <#if context.properties.author??>${context.properties.author}<#else>mbg.generated</#if>
 * @date ${.now?date}
 */
@Repository
public class ${domainObjectName}DAOImpl extends SqlMapClientDaoSupport implements ${domainObjectName}DAO {

<#--
    TODO
-->

}