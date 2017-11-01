Ext.define('zhdzcx.view.zhdzcxRainfallCompare',{
	extend : 'Ext.panel.Panel',//1
	alias : 'widget.zhdzcxRainfallCompare',//2
	id : 'zhdzcxRainfallCompare',//3
	type:1,
	chartType:'column',
	chartX:'DT',
	chartY:'P',
	Xunit:'mm',
	title: '降水量',
	border : false,//6
	layout: 'border',
	items : [
		{
			xtype: 'gridpanel',
			border: false,
			region: 'center',
			store: {
				type: 'rainfallStore'
			},
			enableColumnHide: false, ///隐藏列
			sortableColumns: false, ///隐藏排序
			columnLines: true, //列间线
			viewConfig: {
				stripeRows: true, //条纹行
				enableTextSelection: true //文本可选
			},
			dockedItems : [{
				xtype : 'pagingtoolbar',
				displayInfo : true,
				dock : 'bottom'
			}],
			columns: [{
				xtype: 'gridcolumn',
				width:'11%',
				dataIndex: 'STCD',
				align :'left',
				style : {
					'text-align' : 'center'
				},
				text: '站码'
			},{
				xtype: 'gridcolumn',
				dataIndex: 'STNM',
				width:'11%',
				
				align :'left',
				style : {
					'text-align' : 'center'
				},
				text: '站名'
			}, {
				xtype: 'gridcolumn',
				align: 'center',
				dataIndex: 'RVNM',
				width:'11%',
				style : {
					'text-align' : 'center'
				},
				text: '河流名称'
			}, {
				xtype: 'gridcolumn',
				width:'11%',
				dataIndex: 'DT',
				style : {
					'text-align' : 'center'
				},
				text: '日期'
			}, {
				xtype: 'gridcolumn',
				align: 'center',
				style : {
					'text-align' : 'center'
				},
				width:'11%',
				dataIndex: 'P',
				text: '降水量'
			}, {
				xtype: 'gridcolumn',
				align: 'right',
				style : {
					'text-align' : 'center'
				},
				width:'11%',
				dataIndex: 'PRCD',
				text: '降水量注解码'
			}, {
				xtype: 'gridcolumn',
				align: 'center',
				dataIndex: 'MXP',
				style : {
					'text-align' : 'center'
				},
				width:'11%',
				text: '最大降水量'
			}, {
				xtype: 'gridcolumn',
				align: 'right',
				dataIndex: 'MXPDR',
				style : {
					'text-align' : 'center'
				},
				width:'11%',
				text: '最大降水量时段'
			}, {
				xtype: 'gridcolumn',
				align: 'right',
				dataIndex: 'MXPRC',
				style : {
					'text-align' : 'center'
				},
				width:'11.65%',
				text: '最大降水量注解码'
			}]
		},
		{
			xtype : 'panel',
			region : 'south',
			height:'45%',
			layout : {
				type : 'fit'
			},
			html:'<div id="chartPanel1" style="width:100%;height:100%;" />'
		}
	]
});