var local  = new Object();
var obj = new Array();
local.edit= obj;

$(function() {
	$.post("/searchtableColumn").done(function(data){
		local.tablecolumn = data.data;
	}).fail(function(data){
		$.messager.alert('警告', '连接超时！请关闭页面重新进行导入！');
	})
})

function checkData() {
	

}

function getData(){
	  var type = $("input[name='type']:checked").val();
	  if(typeof(type)=="undefined"||type=="1"){
		  type = "1";
	  }else{
		  type="0";
	  }
	  return type ;
}
function searchData() {
	var type = getData();
	$('#pg').datagrid({
		url:"/searchData",
		rownumbers:true,
		singleSelect:true,
		queryParams : {type:type},
		loadMsg : '请稍等...正在努力加载中！',
		columns : initColumn(local.tablecolumn),
		pagination : true,
		onDblClickRow:function(index,row){
			if(typeof(local.selectedIndex)!='undefined'){
				$('#pg').datagrid('endEdit',local.selectedIndex);
				var obj =getIndexData(local.selectedIndex);
				local.edit.push(obj);
				local.selectedIndex=index;
			}else{
				local.selectedIndex=index;
			}
			$('#pg').datagrid('beginEdit',local.selectedIndex);
		},
		loadFilter:function(data){
			return data;
		}
	})
}

var initColumn= function(data){
	var row_num={
			field : 'row_num',
			title : '数据行',
			width : 40
	};
	var colum  = new Array(row_num);
	
	for (var int = 0; int < data.length; int++) {
		var obj = {
				field : data[int].columnCode,
				title : data[int].columnNm,
				width : 100,
				editor : {
					type : 'text'
				}
			};
		colum.push(obj);
	}
	var excel_status={
			field : "excel_status",
			title : "数据校验结果",
			width : 100,
			styler : function(value, row, index) {
				if(value=="0"){
					return 'background-color:#dff0d8;'
				}else{
					return null;
				}
				
			},
			formatter:function(value, row, index){
				if(value=="0"){
					return "错误";
				}else if(value=="1"){
					return "正确";
				}
				
			}
	};
	colum.push(excel_status);
	var excel_remark={
			field : "excel_remark",
			title : "数据校验说明",
			width : 100
	};
	colum.push(excel_remark);
	var produce_status={
			field : "produce_status",
			title : "业务逻辑校验结果",
			width : 100,
			styler : function(value, row, index) {
				if(value=="0"){
					return 'background-color:#dff0d8;'
				}else {
					return null;
				}
			},
			formatter:function(value, row, index){
				if(value=="0"){
					return "错误";
				}else if(value=="1") {
					return "正确";
				}
			}
	};
	colum.push(produce_status);
	var produce_remark={
			field : "produce_remark",
			title : "业务逻辑说明",
			width : 100
	};
	colum.push(produce_remark);
	
	return new Array(colum);
	
}
function impTable() {
	
	$.ajax({
		type : "POST",
		url : "/serviceProcedure"
	}).done(function(data) {
		if (data.status == "1") {
			$.messager.alert('警告', '业务逻辑运行完成请查看导入的数据！');
		}
	}).fail(function() {
		$.messager.alert('警告', '系统异常请联系管理员！');
	})

}



function save(){
	if(typeof(local.selectedIndex)!='undefined'){
		$('#pg').datagrid('endEdit',local.selectedIndex);
		var obj =getIndexData(local.selectedIndex);
		local.edit.push(obj);
	}
	
	$.ajax({
		type : "POST",
		url : "/saveEdit",
		contentType : "application/json",
		data : JSON.stringify(local.edit)
	}).done(function(data) {
		var type = getData();
		$('#pg').datagrid('load',{type:type});
	}).fail(function() {
		$.messager.alert('警告', '系统异常请联系管理员！');
	})
}


function  getIndexData(index){
	var objs  =$('#pg').datagrid('getRows');
	obj=objs[index];
	return obj;
}