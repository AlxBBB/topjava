<li class="dropdown" style="float: right">
    <a class="dropdown-toggle" data-toggle="dropdown" href="#"><spring:message
            code="app.locale"/>
        <span class="caret"></span></a>
    <ul class="dropdown-menu">
        <li><a class="myloc" href="?lang=en">en</a></li>
        <li><a class="myloc" href="?lang=ru">ru</a></li>
    </ul>
</li>
<script type="text/javascript">
    $('a.myloc').each(function(){
        $(this).attr('href',window.location.pathname+$(this).attr('href'));
    });
</script>