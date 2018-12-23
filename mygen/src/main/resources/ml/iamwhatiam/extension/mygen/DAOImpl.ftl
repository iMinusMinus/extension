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
<#t>${baseRecordType?keep_after_last(".")}
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
 * @author ${context.properties.author!'mbg.generated'}
 * @date ${.now?date}
 */
@Repository
public class ${domainObjectName}DAOImpl extends SqlMapClientDaoSupport implements ${domainObjectName}DAO {

<#--
    TODO
-->

}