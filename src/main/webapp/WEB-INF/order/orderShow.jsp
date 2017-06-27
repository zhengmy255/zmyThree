<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/6/5
  Time: 19:38
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
<%--工具条--%>
<div id="tb">
    <a href="javascript:addOrder()" class="easyui-linkbutton" data-options="iconCls:'icon-add'">新增订单信息</a>
    <a href="javascript:exportExcelZip()" class="easyui-linkbutton" data-options="iconCls:'icon-add'">导出Excel压缩包</a>
    <a href="javascript:upExcelZip()" class="easyui-linkbutton" data-options="iconCls:'icon-add'">上传Excel压缩包</a>
</div>
<%--订单展示列表--%>
<table id="orderDataGrid"></table>
<%----%>
<div id="addZipDiv"></div>

<script type="application/javascript" >


    //选择要上传的文件
    function upExcelZip(){
        $("#addZipDiv").dialog({
            title: '选择文件',
            top:150,
            width: 400,
            height: 150,
            closed: false,
            cache: false,
            href: '<%=request.getContextPath()%>/order/chooseFile.ty',
            modal: true,
            buttons:[{
                text:'关闭',
                handler:function(){
                    $("#addZipDiv").dialog('close')
                }
            }]

        });
    };






    //新增用户
    function saveItem(){
        //alert($("#addOrderForm").serialize());
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

    <!--新增用户表单 -->
    function addOrder(){
        addDiv=$("<div/>").dialog({
            title: '新增',
            width: 600,
            height: 400,
            closed: false,
            cache: false,
            href: '<%=request.getContextPath()%>/order/toaddOrder.ty',
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









    //压缩下载压缩包
    function exportExcelZip() {
        location.href="<%=request.getContextPath()%>/order/exportExcelZip.ty"
    }

    /*页面加载查询订单详情,并渲染表格*/
    $(function(){
        $("#orderDataGrid").datagrid({
            url:'<%=request.getContextPath()%>/order/selectOrder.ty',
            method:'post',
            title:'订单列表展示',
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
                    {field:'orderId',title:'订单ID',width:80,sortable:true},
                    {field:'payment',title:'实际支付金额',width:80
                    },
                    {field:'ppt',title:'支付类型',width:120,
                        formatter: function(value,row,index){
                            if (row.pay){
                                return row.pay.describe;
                            } else {
                                return '';
                            }
                        }
                    },
                    {field:'postFee',title:'邮费',width:120,
                    },
                    {field:'zzz',title:'状态',width:120,
                        formatter: function(value,row,index){
                            if (row.orderStatus){
                                return row.orderStatus.describe;
                            } else {
                                return '';
                            }
                        }
                    },
                    {field:'createTime',title:'订单创建时间',width:120,
                        formatter: function (value, row, index) {
                            var unixTimestamp = new Date(value);
                            return unixTimestamp.toLocaleString();
                        },
                    },
                    {field:'updateTime',title:'订单更新时间',width:120,
                        formatter: function (value, row, index) {
                            var unixTimestamp = new Date(value);
                            return unixTimestamp.toLocaleString();
                        },
                    },
                    {field:'paymentTime',title:'付款时间',width:120,
                        formatter: function (value, row, index) {
                            var unixTimestamp = new Date(value);
                            return unixTimestamp.toLocaleString();
                        },
                    },
                    {field:'consignTime',title:'发货时间',width:120,
                        formatter: function (value, row, index) {
                            var unixTimestamp = new Date(value);
                            return unixTimestamp.toLocaleString();
                        },
                    },
                    {field:'endTime',title:'交易完成时间',width:120,
                        formatter: function (value, row, index) {
                            var unixTimestamp = new Date(value);
                            return unixTimestamp.toLocaleString();
                        },
                    },
                    {field:'closeTime',title:'交易关闭时间',width:120,
                        formatter: function (value, row, index) {
                            var unixTimestamp = new Date(value);
                            return unixTimestamp.toLocaleString();
                        },

                    },
                    {field:'shippingName',title:'物流名称',width:120},
                    {field:'shippingCode',title:'物流单号',width:180},
                    {field:'userName',title:'用户id',width:180,
                        formatter: function(value,row,index){
                        if (row.userInfo){
                            return row.userInfo.username;
                        } else {
                            return '';
                        }
                    }
                    },
                    {field:'buyerMessage',title:'买家留言',width:180},
                    {field:'buyerNick',title:'买家昵称',width:180},
                    {field:'zzzz',title:'买家是否已评价',width:180,
                        formatter: function(value,row,index){
                            if (row.userBuyerRate){
                                return row.userBuyerRate.describe;
                            } else {
                                return '';
                            }
                        }
                    }

                ]
            ]
        });
    });
</script>
</body>
</html>
