<#-- @ftlvariable name="noticeForm" type="ru.itmo.wp.form.NoticeCredentials" -->
<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>

<@c.page>
    <div class="notice form-box">
        <div class="header">Add Notice</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="addNotice"/>


                <div class="field">
                    <div class="name">
                        <label for="content">Notice</label>
                    </div>
                    <div class="value">
                        <#if noticeForm.content??>
                            <textarea id="content" name="content" type="text">${noticeForm.content}</textarea>
                        <#else>
                            <textarea id="content" name="content" type="text"></textarea>
                        </#if>
                    </div>

                    <@c.error "noticeForm.content"/>
                </div>

                <div class="button-field">
                    <input type="submit" value="Add">
                </div>
            </form>
        </div>
    </div>
</@c.page>
