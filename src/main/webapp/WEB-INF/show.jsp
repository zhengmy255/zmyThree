<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/6/2
  Time: 19:30
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

<!-- 条件查询表单 -->
<div class="easyui-panel" data-options="title:'查询条件'">
    <form id="f" method="post">
        <table>
            <tr>
                <td>商品标题:</td>
                <td><input type="text" id="title" name="title" class="easyui-textbox"/></td>
            <tr>
            <tr>
                <td>商品所属类目:</td>
                <td>
                    <select id="cid" name="cid"  style="width: 100px">
                    </select>
                </td>
            </tr>
            <td>
                <a  href="javascript:search()" class="easyui-linkbutton" data-options="iconCls:'icon-search'">搜索</a>
            </td>
            <td>
                <a  href="javascript:reset()" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">重置</a>
            </td>
            </tr>
        </table>
    </form>
</div>
<table id="itemDataGrid"></table>
<!-- datagrid 工具条 -->
<div id="tb">
    <a href="javascript:addItem()" class="easyui-linkbutton" data-options="iconCls:'icon-add'">新增</a>
    <a href="javascript:deleteItem()" class="easyui-linkbutton" data-options="iconCls:'icon-add'">删除</a>
    <a href="javascript:editorTable()" class="easyui-linkbutton" data-options="iconCls:'icon-add'">编辑数据</a>
    <a href="javascript:exitEditor()" class="easyui-linkbutton" data-options="iconCls:'icon-add'">退出编辑</a>
    <a href="javascript:editorUpdate()" class="easyui-linkbutton" data-options="iconCls:'icon-add'">修改</a>
    <a href="javascript:exportExcel()" class="easyui-linkbutton" data-options="iconCls:'icon-add'">导出Excel</a>
    <a href="javascript:downImportTemplate()" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" >下载导入Excel模板</a>
</div>
<script type="application/javascript">
    /*excel导出*/
    //根据条件导出Excel
    function exportExcel() {
    location.href="<%=request.getContextPath()%>/item/exportExcel.ty";
    }
    var editor=null;

    //表格进入编辑模式
    function editorTable() {
        if(editor!=null){
            $("#itemDataGrid").datagrid("endEdit", editor);
            editor=null;
        }
        var row=$("#itemDataGrid").datagrid('getSelected');
        if(row){
            if(editor==null){
                var index=$("#itemDataGrid").datagrid('getRowIndex',row);
                $("#itemDataGrid").datagrid("beginEdit", index);
                editor=index;
            }
        }
    }

    //退出编辑模式
    function exitEditor(){
        if(editor!=null){
            $("#itemDataGrid").datagrid("endEdit", editor);
            editor=null;
            $("#itemDataGrid").datagrid("rejectChanges");
        }
    }

    //表格直接修改
    function editorUpdate() {
        $("#itemDataGrid").datagrid("endEdit", editor);
    }



    //删除用户
    function deleteItem(){
        var strId='';
        var arr=$("#itemDataGrid").datagrid('getChecked')
        for(i=0;i<arr.length;i++){
            strId+=arr[i].id+",";
        }
        $.messager.confirm('确认','您确认想要删除这个用户吗？',function(r){
            if (r){
                $.post(
                    '<%=request.getContextPath()%>/item/deleteItem.ty',
                    {'strId':strId},
                    function(data){
                        alert(data.msg);
                        $("#itemDataGrid").datagrid('reload');
                    },
                    'json'
                )
            }
        });
    }


    <!--新增用户 -->
    function addItem(){
        addDiv=$("<div/>").dialog({
            title: '新增',
            width: 600,
            height: 400,
            closed: false,
            cache: false,
            href: '<%=request.getContextPath()%>/item/toadd.ty',
            modal: true,
            buttons:[{
                text:'保存',
                handler:function(){
                    saveItem();
                }
            },{
                text:'关闭',
                handler:function(){
                    addDiv.dialog('destroy')
                }
            }],
            onClose:function () {
                addDiv.dialog('destroy')
            },

        })
    }

    //新增用户
    function saveItem(){
        alert($("#addForm").serialize());
        $.post(
            '<%=request.getContextPath()%>/item/saveItem.ty',
            $("#addForm").serialize(),
            function(data){
                alert(data.msg);
                addDiv.dialog('destroy');
                $("#itemDataGrid").datagrid('reload');
            },
            'json'

        )
    }


    /*商品的类目下拉列表框*/
    $(function(){
        $('#cid').combobox({
            url:'<%=request.getContextPath()%>/itemcat/selectItemCat.ty',
            valueField:'id',
            textField:'name'
        });
    })

    //查询的条件
    function search(){
        var title=$("#title").val().trim();
        var cid= $('#cid').combobox('getValue');
        $("#itemDataGrid").datagrid('load',{
            "title":title,
            "cid":cid
        })
    }

    //重置查询
    function reset(){
        $("#f").form('reset');
        search();
    }


    <!--页面加载时 查询userlist集合 -->
    $(function(){
        $("#itemDataGrid").datagrid({
            url:'<%=request.getContextPath()%>/item/selectItem.ty',
            method:'post',
            title:'列表展示',
            pagination:true,
            rownumbers:true,
            selectOnCheck:false,
            checkOnSelect:false,
            pageNumber:1,
            pageSize:2,
            pageList:[2,4,6,8],
            striped : true,
            rownumbers : true,
            pagination : true,
            singleSelect : true,
            sortName:'id',
            sortOrder:'desc',
            idField : 'id',
            loadMsg:'候着。。。',
            toolbar: '#tb',
            columns:[
                [{field:'',checkbox:true,width:80},
                    {field:'id',title:'ID',width:80,sortable:true},
                    {field:'title',title:'商品标题',width:80,
                        editor:{
                            type:'validatebox',
                            options:{
                                required:true,
                            }
                        }
                    },
                    {field:'sellPoint',title:'商品卖点',width:120,
                        editor:{
                            type:'validatebox',
                            options:{
                                required:true,
                            }
                        }
                    },
                    {field:'price',title:'商品价格',width:120,
                        editor:{
                            type:'validatebox',
                            options:{
                                required:true,
                            }
                        }
                    },
                    {field:'num',title:'库存数量',width:120,
                        editor:{
                            type:'validatebox',
                            options:{
                                required:true,
                            }
                        }
                    },
                    {field:'barcode',title:'商品条形码',width:120},
                    {field:'image',title:'商品图片',width:120},
                    {
                        field: 'cid', title: '所属类目', width: 120,
                        formatter: function (value, row, index) {
                            if (row.cat) {
                                return row.cat.name;
                            }
                        },
                        editor:{
                            type:'combotree',
                            options:{
                                url:'<%=request.getContextPath()%>/itemcat/selectItemCatTree.ty',
                                required: true,
                            }
                        }
                    },
                    {field:'status',title:'商品状态',width:120,
                        formatter: function (value, row, index) {
                            if (value==1) {
                                return "正在售卖中";
                            }else{
                                return "已售罄";
                            }
                        }
                    },
                    {field:'showCreated',title:'创建时间',width:120},
                    {field:'showUpdated',title:'修改时间',width:180}

                ]
            ],onEndEdit:function(index, row, changes){
                //alert(row.uName);
                var rowStr = JSON.stringify(row);
                $.post(
                    '<%=request.getContextPath()%>/item/editorUpdate.ty',
                    {'rowStr':rowStr},
                    function(data){
                        alert(data.msg)
                        $("#itemDataGrid").datagrid('reload');
                    },
                    'json'
                )
            }

        });
    });
</script>
</body>
</html>
