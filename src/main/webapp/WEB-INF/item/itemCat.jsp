<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/5
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/include.jsp" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table id="itemCatId"></table>

<script type="application/javascript">
    $("#itemCatId").treegrid({
        url:'<%=request.getContextPath()%>/itemcat/selectItemCatList.ty',
        idField:'id',
        treeField:'name',
        columns:[[
            {title:'Task Name',field:'name',width:180},
            {field:'status',title:'status',width:60,align:'right'},
            {field:'created',title:'created',width:80},
            {field:'updated',title:'updated',width:80}
        ]]
    })

</script>
</body>
</html>
