<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/5/23
  Time: 9:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/common/include.jsp" %>

<html>
<head>
    <title>新增商品</title>
</head>
<body>
<form id="addForm" method="post">
    <table>
        <tr>
            <td>商品标题:</td>
            <td><input type="text" name="title"class="easyui-validatebox" data-options="required:true"></td>
        </tr>
        <tr>
            <td>商品卖点：</td>
            <td><input type="text" name="sellPoint" class="easyui-validatebox" data-options="required:true"></td>
        </tr>
        <tr>
            <td>商品价格：</td>
            <td><input type="text" name="price" class="easyui-validatebox" data-options="required:true"></td>
        </tr>
        <tr>
            <td>商品库存：</td>
            <td><input type="text" name="num" class="easyui-validatebox" data-options="required:true"></td>
        </tr>
        <tr>
            <td>商品条形码：</td>
            <td><input type="text" name="barcode" class="easyui-validatebox" data-options="required:true"></td>
        </tr>
        <tr>
            <td>商品图片：</td>
            <td>
                <input type="file" name="upfile" id="upImg"/>
                <div id="showImg"/>
            </td>
        </tr>
    </table>
</form>

</td>
        </tr>
        <tr>
            <th>商品状态：</th>
            <th>
                <label><input type="radio" value="1" name="status" >正在售卖</label>
                <label><input type="radio" value="2" name="status" >已售罄</label>
            </th>
        </tr>
        <tr>
            <td>商品类目：</td>
            <td>
                <select id="catId" name="cid" style="width:200px;"> </select>
            </td>
        </tr>
    </table>
</form>

<script type="text/javascript">

    /*用户级别下拉列表框*/
    $(function(){
        $('#catId').combotree({
            url:'<%=request.getContextPath()%>/itemcat/selectItemCatTree.ty',
            required: true
        });
    })


    $(function() {

        $("#upImg").uploadify({
            //前台请求后台的url 不可忽略的参数
            'uploader': "<%=request.getContextPath()%>/item/upImg.ty",
            //插件自带 不可忽略的参数
            'swf': '<%=request.getContextPath()%>/js/uploadify/uploadify.swf',
            //撤销按钮的图片路径
            'cancelImg': '<%=request.getContextPath() %>/js/uploadify/uploadify-cancel.png',
            //如果为true 为自动上传 在文件后 为false 那么它就要我们自己手动点上传按钮
            'auto': true,
            //可以同时选择多个文件 默认为true 不可忽略
            'multi': false,
            //给div的进度条加背景 不可忽略
            // 'queueID' : 'jinDu',
            //给上传按钮设置文字
            'buttonText': '选取文件',
            //上传后队列是否消失
            'removeCompleted': true,
            'removeTimeout': 1,
            // action中接收文件的全局变量名  与 input标签的name属性一致
            'fileObjName': 'upfile',
            //上传文件的个数
            // 'uploadLimit' : 9,
            //上传文件的类型
            'fileTypeExts': '*.jpg',
            //struts2上传文件默认2MB
            //'fileSizeLimit' : '2MB',
            //   文件上传成功后调用的函数
            'onUploadSuccess': function (file, data, response) {


                var src = '<%=request.getContextPath() %>/' + data;
                $("#showImg").append("<img src='" + src + "' style='width:100px'/>");
                //alert(data);
                <%--if(data.success){--%>
                <%--$("#addZipDiv").dialog('close');--%>
                <%--location.href="<%=request.getContextPath()%>/show/show.hzx";--%>
                <%--}--%>

            }
        });
    });
</script>
</body>
</html>
