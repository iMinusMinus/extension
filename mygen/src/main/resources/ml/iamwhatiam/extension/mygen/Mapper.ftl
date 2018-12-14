<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<#assign props = context.javaClientGeneratorConfiguration.properties>
<#assign domainObjectName>
<#if tableConfiguration.domainObjectName??>
<#t>${tableConfiguration.domainObjectName}
<#else>
<#t>${baseRecordType?substring(baseRecordType?last_index_of(".") + 1)}
</#if>
</#assign>
<mapper namespace="${myBatis3SqlMapNamespace}">
    <resultMap id="${context.sqlMapGeneratorConfiguration.properties.baseResultMapId ! baseResultMapId}" type="${javaModelPackage}.${domainObjectName}">
<#list primaryKeyColumns as column>
        <id column="${column.actualColumnName}" jdbcType="${column.jdbcTypeName}" property="${column.javaProperty}" <#if column.typeHandler??>typeHandler="${column.typeHandler}" </#if>/>
</#list>
<#list baseColumns as column>
        <result column="${column.actualColumnName}" jdbcType="${column.jdbcTypeName}" property="${column.javaProperty}" <#if column.typeHandler??>typeHandler="${column.typeHandler}" </#if> />
</#list>
    </resultMap>
<#if BLOBColumns?size gt 0>
    <resultMap extends="${context.sqlMapGeneratorConfiguration.properties.baseResultMapId ! baseResultMapId}" id="${context.sqlMapGeneratorConfiguration.properties.resultMapWithBLOBsId ! resultMapWithBLOBsId}" type="${javaModelPackage}.${domainObjectName}">
<#list BLOBColumns as column>
        <result column="${column.actualColumnName}" property="${column.javaProperty}" jdbcType="${column.jdbcTypeName}" <#if column.typeHandler??>typeHandler="${column.typeHandler}" </#if> />
</#list>
    </resultMap>
</#if>
    <sql id="${context.sqlMapGeneratorConfiguration.properties.baseColumnListId ! baseColumnListId}">
<#list primaryKeyColumns as column>
    <#lt>${column.actualColumnName}, <#rt>
</#list>
<#list baseColumns as column>
    <#lt>${column.actualColumnName}<#sep>, <#rt>
</#list>
    </sql>
<#if BLOBColumns?size gt 0>
    <sql id="${context.sqlMapGeneratorConfiguration.properties.blobColumnListId ! blobColumnListId}">
    <#list BLOBColumns as column>
        ${column.actualColumnName}<#if column_has_next>, </#if>
    </#list>
    </sql>
</#if>
    <select id="${props.selectByExampleWithBLOBsStatementId ! selectByExampleWithBLOBsStatementId}" parameterType="${javaModelPackage}.${domainObjectName}" resultMap="${context.sqlMapGeneratorConfiguration.properties.resultMapWithBLOBsId ! resultMapWithBLOBsId}">
        SELECT <include ref="${context.sqlMapGeneratorConfiguration.properties.baseColumnListId ! baseColumnListId}" /><#if BLOBColumns?size gt 0>, <include ref="${context.sqlMapGeneratorConfiguration.properties.blobColumnListId ! blobColumnListId}" /></#if>
          FROM ${tableConfiguration.tableName}
        <where>
            <#list allColumns as column>
            <if test="${column.javaProperty} != null">
           AND ${column.actualColumnName} = <#noparse>#{</#noparse>${column.javaProperty},${column.jdbcTypeName}}
            </if>
            </#list>
        </where>
    </select>
    <select id="${props.selectByExampleStatementId ! selectByExampleStatementId}" resultMap="${context.sqlMapGeneratorConfiguration.properties.baseResultMapId ! baseResultMapId}" parameterType="${javaModelPackage}.${domainObjectName}">
        SELECT <include ref="${context.sqlMapGeneratorConfiguration.properties.baseColumnListId ! baseColumnListId}" />
          FROM ${tableConfiguration.tableName}
        <where>
            <#list allColumns as column>
            <if test="${column.javaProperty} != null">
            AND ${column.actualColumnName} = <#noparse>#{</#noparse>${column.javaProperty},${column.jdbcTypeName}}
            </if>
            </#list>
        </where>
    </select>

    <select id="${props.selectByPrimaryKeyStatementId ! selectByPrimaryKeyStatementId}" parameterType="${javaModelPackage}.${domainObjectName}" resultMap="${context.sqlMapGeneratorConfiguration.properties.resultMapWithBLOBsId ! resultMapWithBLOBsId}">
        SELECT <include ref="${context.sqlMapGeneratorConfiguration.properties.baseColumnListId ! baseColumnListId}" /><#if BLOBColumns?size gt 0>, <include ref="${context.sqlMapGeneratorConfiguration.properties.baseColumnListId ! baseColumnListId}" /></#if>
          FROM ${tableConfiguration.tableName}
         WHERE <#list primaryKeyColumns as key>${key.actualColumnName} = <#noparse>#{</#noparse>key.javaProperty},${key.jdbcTypeName}}<#if key_has_next> AND</#if></#list>
    </select>

    <delete id="${props.deleteByPrimaryKeyStatementId ! deleteByPrimaryKeyStatementId}" parameterType="${javaModelPackage}.${domainObjectName}">
        DELETE FROM ${tableConfiguration.tableName}
         WHERE <#list primaryKeyColumns as key>${key.actualColumnName} = <#noparse>#{</#noparse>key.javaProperty},${key.jdbcTypeName}}<#if key_has_next> AND</#if></#list>
    </delete>

    <delete id="${props.deleteByExampleStatementId ! deleteByExampleStatementId}" parameterType="${javaModelPackage}.${domainObjectName}">
        DELETE FROM ${tableConfiguration.tableName}
        <where>
            <#list allColumns as column>
            <if test="${column.javaProperty} != null">
           AND ${column.actualColumnName} = <#noparse>#{</#noparse>${column.javaProperty},${column.jdbcTypeName}} AND
            </if>
            </#list>
        </where>
    </delete>

    <insert id="${props.insertStatementId ! insertStatementId}" parameterType="${javaModelPackage}.${domainObjectName}">
        INSERT INTO ${tableConfiguration.tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <include ref="${context.sqlMapGeneratorConfiguration.properties.baseColumnListId ! baseColumnListId}" /><#if BLOBColumns?size gt 0>, <include ref="${context.sqlMapGeneratorConfiguration.properties.baseColumnListId ! baseColumnListId}" /></#if>
        </trim>
        <trim prefix=" VALUES (" suffix=")" suffixOverrides=",">
        <#list allColumns as column><#noparse>#{</#noparse>${column.javaProperty},${column.jdbcTypeName}}</#list>
        </trim>
    </insert>

    <insert id="${props.insertSelectiveStatementId ! insertSelectiveStatementId}" parameterType="${javaModelPackage}.${domainObjectName}">
        INSERT INTO ${tableConfiguration.tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
        <#list allColumns as column>
            <if test="${column.javaProperty} != null">
                ${column.actualColumnName},
            </if>
        </#list>
        </trim>
        <trim prefix=" VALUES (" suffix=")" suffixOverrides=",">
        <#list allColumns as column>
            <if test="${column.javaProperty} != null">
                <#noparse>#{</#noparse>${column.javaProperty},${column.jdbcTypeName}},
            </if>
        </#list>
        </trim>
    </insert>

    <select id="${props.countByExampleStatementId ! countByExampleStatementId}" parameterType="${javaModelPackage}.${domainObjectName}" resultType="java.lang.Long">
        SELECT COUNT(*)
          FROM ${tableConfiguration.tableName}
        <where>
            <#list allColumns as column>
            <if test="${column.javaProperty} != null">
            AND ${column.actualColumnName} = <#noparse>#{</#noparse>${column.javaProperty},${column.jdbcTypeName}}
            </if>
            </#list>
        </where>
    </select>

    <update id="${props.updateByExampleSelectiveStatementId ! updateByExampleSelectiveStatementId}" parameterType="map">
        UPDATE ${tableConfiguration.tableName}
           <set>
               <#list allColumns as column>
               <if test="record.${column.javaProperty} != null">${column.actualColumnName} = <#noparse>#{</#noparse>record.${column.javaProperty},${column.jdbcTypeName}},</if>
               </#list>
           </set>
        <where>
            <#list allColumns as column>
            <if test="example.${column.javaProperty} != null">
           AND ${column.actualColumnName} = <#noparse>#{</#noparse>example.${column.javaProperty},${column.jdbcTypeName}}
            </if>
            </#list>
        </where>
    </update>

    <update id="${props.updateByExampleStatementId ! updateByExampleStatementId}" parameterType="map">
        UPDATE ${tableConfiguration.tableName}
        <set>
            <#list allColumns as column>
               ${column.actualColumnName} = <#noparse>#{</#noparse>record.${column.javaProperty},${column.jdbcTypeName}},
            </#list>
        </set>
        <where>
            <#list allColumns as column>
            <if test="example.${column.javaProperty} != null">
           AND ${column.actualColumnName} = <#noparse>#{</#noparse>example.${column.javaProperty}, ${column.jdbcTypeName}}
            </if>
            </#list>
        </where>
    </update>

    <update id="${props.updateByPrimaryKeySelectiveStatementId ! updateByPrimaryKeySelectiveStatementId}" parameterType="${javaModelPackage}.${domainObjectName}">
        UPDATE ${tableConfiguration.tableName}
        <set>
            <#list allColumns as column>
                <#if !primaryKeyColumns?seq_contains(column)>
            <if test="${column.javaProperty} != null">
               ${column.actualColumnName} = <#noparse>#{</#noparse>${column.javaProperty},${column.jdbcTypeName}},
            </if>
                </#if>
            </#list>
        </set>
         WHERE
            <#list primaryKeyColumns as column>
               ${column.actualColumnName} = <#noparse>#{</#noparse>${column.javaProperty},${column.jdbcTypeName}}<#if column_has_next> AND</#if>
            </#list>
    </update>

    <update id="${props.updateByPrimaryKeyStatementId ! updateByPrimaryKeyStatementId}" parameterType="${javaModelPackage}.${domainObjectName}">
        UPDATE ${tableConfiguration.tableName}
        <set>
            <#list allColumns as column>
                <#if !primaryKeyColumns?seq_contains(column)>
               ${column.actualColumnName} = <#noparse>#{</#noparse>record.${column.javaProperty},${column.jdbcTypeName}},
                </#if>
            </#list>
        </set>
        WHERE
        <#list primaryKeyColumns as column>
               ${column.actualColumnName} = <#noparse>#{</#noparse>${column.javaProperty},${column.jdbcTypeName}}<#if column_has_next> AND</#if>
        </#list>
    </update>
</mapper>