<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ include file="/common/include.jsp"%>

<!actionCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>主页面</title>
	<script type="text/javascript">
	</script>
</head>
<body id="showLayout" class="easyui-layout">
<%--<div data-options="region:'north',href:'<%=request.getContextPath()%>/main/north.ty'" style="height: 70px; overflow: hidden;" class="logo"></div>
<div data-options="region:'west',href:'',split:true" title="导航" style="width: 200px; padding: 10px;">
	<ul id="showMenu"></ul>
</div>`--%>
<div data-options="region:'center'" style="overflow: hidden;">
	<div id="showTabs">
		<div title="首页" data-options="iconCls:'icon-filter'">
			<iframe src="<%=request.getContextPath()%>/admin/toshow.ty" allowTransparency="true" style="border: 0; width: 100%; height: 99%;" frameBorder="0"></iframe>
		</div>
	</div>
</div>
<%--<div data-options="region:'south',href:'<%=request.getContextPath()%>/main/south.ty',border:false" style="height: 30px; overflow: hidden;"></div>--%>





<script type="text/javascript" charset="utf-8">



    var mainMenu;
    var mainTabs;
    $(function(){
        $("#showMenu").tree({
            <%--url:'<%=request.getContextPath()%>/itemcat/selectItemCatTree.ty',--%>
            <%--//显示虚线--%>
            <%--lines:true,--%>
            <%--onClick:function(node){--%>
            <%--//node点击的当前节点--%>
            <%--//alert(node.text+node.attributes.url);--%>
            <%--addTabs(node);--%>
            <%--}--%>
            data:[{
                "id": 1,
                "text": "商品",
                "state": "closed",
                "children": [{
                    "id": 11,
                    "text": "商品详细"
                },{
						"id": 12,
						"text": "商品类目"
					},
                    {
                        "id": 13,
                        "text": "订单详情"
                    }]
				},{
					"id": 2,
					"text": "随便啦",
					"state": "closed"
				}],
            onClick:function(node){
                //node点击的当前节点
                //alert(node.text+node.attributes.url);
                addTabs(node);
            }
        });

        mainTabs = $('#showTabs').tabs({
            fit : true,
            border : false,
            tools : [ {
                text : '刷新',
                iconCls : 'ext-icon-arrow_refresh',
                handler : function() {
                    var panel = mainTabs.tabs('getSelected').panel('panel');
                    var frame = panel.find('iframe');
                    if (frame.length > 0) {
                        for ( var i = 0; i < frame.length; i++) {
                            frame[i].contentWindow.document.write('');
                            frame[i].contentWindow.close();
                            frame[i].src = frame[i].src;
                        }
                    }
                }
            }, {
                text : '关闭',
                iconCls : 'ext-icon-cross',
                handler : function() {
                    var index = mainTabs.tabs('getTabIndex', mainTabs.tabs('getSelected'));
                    var tab = mainTabs.tabs('getTab', index);
                    //	mainTabs.tabs('getSelected').panel('options');
                    if (tab.panel('options').closable) {
                        mainTabs.tabs('close', index);//title
                    } else {
                        $.messager.alert('提示', '[' + tab.panel('options').title + ']不可以被关闭！', 'error');
                    }
                }
            } ]
        });
    })

    function addTabs(node){
        var tabs = $("#showTabs")
        var text=node.text;
        var src ="";

        if(text=="商品类目"){
            src = "<%=request.getContextPath()%>/itemcat/tocat.ty";
        }
        if(text=="订单详情"){
            src = "<%=request.getContextPath()%>/order/toorder.ty";
        }
        if (mainTabs.tabs('exists', node.text)) {
            mainTabs.tabs('select', node.text);
        } else {
            mainTabs.tabs('add',{
                title:node.text,
                content:formatString('<iframe src="{0}" allowTransparency="true" style="border:0;width:100%;height:99%;" frameBorder="0"></iframe>', src),
                closable:true,
                border : false,
                fit : true
            });
        }
    }


    function formatString(str) {
        for ( var i = 0; i < arguments.length - 1; i++) {
            str = str.replace("{" + i + "}", arguments[i + 1]);
        }
        return str;
    };

</script>
</body>
</html>