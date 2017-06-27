<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/6/13
  Time: 14:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Title</title>
</head>
<body>
这是新增订单
<%--新增订单信息表单--%>
<form id="addOrderForm">
    <table>
        <tr>
            <td>
                订单实际支付金额:
            </td>
            <td>
                <input type="text" name="payment">
            </td>
        </tr>

    </table>

</form>

</body>
</html>
