$(function() {
	$('#excelupload').form({
		onSubmit : function() {
			var startrow = $("#startrow").numberbox('getValue');
			var doType = $("#doType").combobox('getValue');
			var file = $("#file").filebox('getValue');
			var improw = $("#improw").numberbox('getValue');
			var st = file.split(".");
			if (startrow.trim().length == 0
					|| doType.trim().length == 0
					|| file.trim().length == 0
					|| improw.trim().length == 0) {
				$.messager.alert('警告', '参数填写不完全！');
				return false;
			}
			if (Number(improw) <= Number(startrow)) {
				$.messager.alert('警告', '数据导入开始行必须大于数据标题所在行！');
				return false;
			}
			if (st[st.length - 1] != doType) {
				$.messager.alert('警告', '文件类型与文件不匹配！');
				return false;
			}
		},
		 success : function(r){
			 	location.replace('/documentImp');//成功之后用js进行跳转
			 	var obj = jQuery.parseJSON(r);
			}
		
	});
});

function sub() {
	$('#excelupload').submit();
}
function clear() {
	alert(2);
}