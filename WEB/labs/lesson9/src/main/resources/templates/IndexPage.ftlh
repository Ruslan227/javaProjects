<#-- @ftlvariable name="posts" type="ru.itmo.wp.domain.Post[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#if posts??>
        <#list posts as post>
            <@c.post post=post/>
        </#list>
    </#if>
</@c.page>
