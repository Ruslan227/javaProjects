<#-- @ftlvariable name="usersForm" type="ru.itmo.wp.form.UserCredentials" -->
<#-- @ftlvariable name="users" type="ru.itmo.wp.domain.User[]" -->
<#import "commons.ftlh" as c>
<#--<#import "/spring.ftl" as spring>-->

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
                <th>Switch</th>
            </tr>
            </thead>
            <tbody>
            <#if users?size==0>
                <tr class="noData">
                    <td colspan="3">
                        No data
                    </td>
                </tr>
            <#else>
                <#list users as user>
                    <tr>
                        <td class="id">${user.id}</td>

                        <td class="login">${user.login}</td>
                        <td class="creationTime">${user.creationTime}</td>

                        <td class="form">
                            <form method="post" action="">
                                <div class="field">
                                    <div class="value">
                                        <#if user.disabled == false>
                                            <input id="disabled" name="disabled" type="submit" value="Disable"/>
                                        <#else>
                                            <input id="disabled" name="disabled" type="submit" value="Enable"/>
                                        </#if>
                                        <input type="hidden" name="id" value="${user.id}"/>
                                    </div>
                                </div>
                            </form>
                        </td>


                    </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>
