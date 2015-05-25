<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>tbc列表</title>
<link href="/static/uimaker/css/style.css" rel="stylesheet" type="text/css" />
<link href="/static/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/static/uimaker/js/jquery.js"></script>
<script type="text/javascript" src="/static/js/common.js"></script>

<script type="text/javascript">
jQuery(function(){
	<#if (VIEW_MSG_TYPE)?? >
	var op={msg:"${VIEW_MSG_MSG}"};
	peon.popx["${VIEW_MSG_TYPE}"](op);
	</#if>
});


function confirm_del(){
	var ids=[];
	var checkIds=jQuery(".onecheck:checked");
	if(checkIds.length==0){
		peon.popx.warn({msg:"请选择要删除的项！"});
		return;
	}
	checkIds.each(function(){
		ids.push(this.value);
	});
	var str='        <p>是否确认要删除当前选中的记录 ？</p>'+
			'        <cite>如果是请点击确定按钮 ，否则请点取消。</cite>';
	var op={
		title:"操作提示",
		msg:str,
		confirm:function(v){v&&delByIds(ids);}
	};
	peon.popx.confirm(op);
}
function delByIds(ids){
	location="/tbc/deleteByIds?id="+(ids.join("&id="));
}
function checkAll(val){
	jQuery(".onecheck").attr("checked",val);
}
</script>


</head>

<#include "/page/common/_paginate_new.ftl" />
<body>

	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="javascript:top.location='/';">首页</a></li>
    <li><a href="#">tbc列表</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
    
    <div class="tools">
    
    	<ul class="toolbar">
        <li onclick="location='/tbc/add'"><span><img src="/static/uimaker/images/t01.png" /></span>添加</li>
        <li onclick="confirm_del()"><span><img src="/static/uimaker/images/t03.png" /></span>删除</li>
        </ul>
        
        
        <ul class="toolbar1">
        <li><span><img src="/static/uimaker/images/t05.png" /></span>设置</li>
        </ul>
    
    </div>
    
    
    <table class="tablelist">
    	<thead>
    	<tr>
    	<th ><input name="" type="checkbox" onclick="checkAll(this.checked,'')"/></th>
			<th title="">id</th>
			<th title="模型id">tbid</th>
			<th title="属性名称">cn</th>
			<th title="数据类型">ty</th>
			<th title="默认值">de</th>
			<th title="标记(是否是主|空|增)">fl</th>
			<th title="注释">cm</th>
			<th title="示例数据">egg</th>
    	
        <th style="color:red">adminOP</th>
        </tr>
        </thead>
        <tbody>
        
        
        
       	<#list pg.getList() as tbc>
        <tr>
       		<td><input class="onecheck" type="checkbox" value="${tbc.id}" /></td>
       
			<td style="text-align:center;"><@withSub text="${tbc.id!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${tbc.tbid!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${tbc.cn!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${tbc.ty!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${tbc.de!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${tbc.fl!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${tbc.cm!}" len=8 /></td>
			<td style="text-align:center;"><@withSub text="${tbc.egg!}" len=8 /></td>
       
       
       		<td><a href="/tbc/edit/${tbc.id}" class="tablelink">查看</a>&nbsp;<a href="/tbc/delete/${tbc.id}" class="tablelink"> 删除</a></td>
        </tr> 
		</#list>
          
          
            
        </tbody>
    </table>
    
   
	<@paginate currentPage=pg.pageNumber totalPage=pg.totalPage totalRow=pg.totalRow actionUrl="/tbc/" />
   
    </div>
    
    <script type="text/javascript">
	$('.tablelist tbody tr:odd').addClass('odd');
	</script>

</body>

</html>
