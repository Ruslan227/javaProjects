<#import "commons.ftlh" as c>

<@c.page>
    <#if user??>
        id: ${user.id} <br>
        login: ${user.login} <br>
        creation: ${user.creationTime} <br>
    <#else>
        No such user.
    </#if>
</@c.page>