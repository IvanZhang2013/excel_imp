var local = new Object();
local.type = '0';
function sub() {
	$('#pg').propertygrid("endEdit", local.edit);
	var data = $('#pg').propertygrid("getData");
	var tableId = $("#doType").combobox('getValue');
	var array = new Array(data.rows.length);
	for (var i = 0; i < data.rows.length; i++) {
		var obj = data.rows[i];
		var tableexcel = new Object();
		tableexcel.columnNm = obj.columnNm;
		tableexcel.columnCode = obj.columnCode;
		tableexcel.columnRegex = obj.columnRegex;
		tableexcel.columnRemark = obj.columnRemark;
		tableexcel.require = obj.require;
		tableexcel.excelIndex = obj.value;
		array[i] = tableexcel;
	}

	$.ajax({
		type : "POST",
		url : "/excelTempImp",
		contentType : "application/json",
		data : JSON.stringify({
			tableId : tableId,
			data : array
		})
	}).done(function(data) {
		if (data.status == "1") {
			location.replace('/excelImp');
		} else {
			$.messager.alert('警告', '系统异常请联系管理员！');
		}
	}).fail(function() {
		$.messager.alert('警告', '系统异常请联系管理员！');
	})
}

$(function() {

	$.post("/impOptions").done(function(data) {
		local.data = data.CellList;
		local.type = "1";
	}).fail(function() {
		$.messager.alert('警告', '数据异常请联系管理员！');
	});

	$("#doType").combobox({
		url : "/tableAll",
		valueField : 'tempTableId',
		textField : 'appName',
		onChange : function(newValue, oldValue) {
			if (newValue != oldValue) {
				local.type = '0';
				$.post("/excelPorpertity", {
					tableName : newValue
				}).done(function(data) {
					var objData = new Object();
					objData.total = data.length;
					var rowArray = new Array();
					for (var i = 0; i < data.length; i++) {
						var tq = data[i];
						var editor = new Object();
						editor.type = "combobox";
						var options = new Object();
						options.valueField = "index";
						options.textField = "cellName";
						options.method = "get";
						options.data = local.data;
						editor.options = options;
						tq.editor = editor;
						rowArray.push(tq)
					}
					objData.rows = rowArray;
					$('#pg').propertygrid("loadData", objData);
				});
			}
		}
	})

	$('#pg').propertygrid({
		columns : [ [ {
			field : "columnNm",
			sortable : true,
			title : "Excel-数据页字段",
			width : 100
		}, {
			field : "value",
			sortable : true,
			formatter : function(value, row, index) {
				var res = '';
				if (value != undefined) {
					var rowArray = local.data;
					for (var i = 0; i < rowArray.length; i++) {
						if (value == rowArray[i].index) {
							res = rowArray[i].cellName;
							break;
						}
					}
				}
				return res;
			},
			title : "数据库-数据表字段",
			width : 100
		} ] ],
		onBeforeEdit : function(index, row) {
			local.edit = index;
		},
		showGroup : true,
		scrollbarSize : 0
	});
})