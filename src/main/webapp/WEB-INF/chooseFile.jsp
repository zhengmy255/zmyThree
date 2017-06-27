<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/include.jsp"%>
<html>
<head>
    <title></title>
</head>
<body>
<center>
 <form>
<input type="file" name="upexcel" id="upexcel"/>
</form>
</center>

<script type="text/javascript">

$(function(){
	
	$("#upexcel").uploadify({
		//前台请求后台的url 不可忽略的参数
	     'uploader' :"<%=request.getContextPath()%>/order/readZip.ty",
	     //插件自带 不可忽略的参数
	     'swf' : '<%=request.getContextPath()%>/js/uploadify/uploadify.swf',
	     //撤销按钮的图片路径
	     'cancelImg' : '<%=request.getContextPath() %>/js/uploadify/uploadify-cancel.png',
	     //如果为true 为自动上传 在文件后 为false 那么它就要我们自己手动点上传按钮
	     'auto' : true,
	     //可以同时选择多个文件 默认为true 不可忽略
	     'multi' : false,
	     //给div的进度条加背景 不可忽略
	    // 'queueID' : 'jinDu',
	     //给上传按钮设置文字
	     'buttonText' : '选取文件',
	     //上传后队列是否消失
	     'removeCompleted' : true,
	     'removeTimeout' : 1,
	     // action中接收文件的全局变量名  与 input标签的name属性一致
	     'fileObjName' : 'upexcel',
	    //上传文件的个数
	    // 'uploadLimit' : 9,
	    //上传文件的类型
	    'fileTypeExts' : '*.zip',
	    //struts2上传文件默认2MB 
        'fileSizeLimit' : '31MB',
	     //   文件上传成功后调用的函数
	      'onUploadSuccess' : function(file, data, response) {
	              //alert(data);
	        $("#addZipDiv").dialog('close');
	     }
	});
});
</script>
</body>
</html>