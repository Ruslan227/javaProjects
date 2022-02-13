<#-- @ftlvariable name="commentForm" type="ru.itmo.wp.form.CommentCredentials" -->
<#import "commons.ftlh" as c>

<@c.page>
    <@c.post post/>
    <#if user??>
        <form method="post" action="">
            <input type="hidden" name="action" value="addComment"/>
            <div class="field">
                <div class="name">
                    <label for="text">Comment</label>
                </div>
                <div class="value">
                    <textarea autofocus id="text" name="text">${commentForm.text!}</textarea>
                </div>
                <@c.error "commentForm.text"/>
            </div>
            <div class="button-field">
                <input type="submit" value="Comment!">
            </div>
        </form>
    </#if>
    <#if post.comments??>
        <#list post.comments as comm>
            ${comm}
        </#list>
    <#else>
        ~~~~NO COMMENTS~~~~
    </#if>
</@c.page>