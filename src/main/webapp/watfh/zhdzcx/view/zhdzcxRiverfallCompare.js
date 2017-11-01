Ext.define('zhdzcx.view.zhdzcxRiverfallCompare',{
	extend : 'Ext.panel.Panel',//1
	alias : 'widget.zhdzcxRiverfallCompare',//2
	id : 'zhdzcxRiverfallCompare',//3
	type:2,
	chartX:'DT',
	chartY:'AVZ',
	Xunit:'m',
	title: '平均水位',
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
				width:'8%',
				dataIndex: 'STCD',
				align :'left',
				style : {
					'text-align' : 'center'
				},
				text: '站码'
			},{
				xtype: 'gridcolumn',
				dataIndex: 'STNM',
				width:'8%',
				align :'left',
				style : {
					'text-align' : 'center'
				},
				text: '站名'
			}, {
				xtype: 'gridcolumn',
				align: 'center',
				dataIndex: 'RVNM',
				width:'8%',
				style : {
					'text-align' : 'center'
				},
				text: '河流名称'
			}, {
				xtype: 'gridcolumn',
				width:'9%',
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
				width:'8%',
				dataIndex: 'AVZ',
				text: '平均水位'
			}, {
				xtype: 'gridcolumn',
				align: 'right',
				style : {
					'text-align' : 'center'
				},
				width:'8.5%',
				dataIndex: 'AVZRCD',
				text: '平均水位注解码'
			}, {
				xtype: 'gridcolumn',
				align: 'center',
				dataIndex: 's1',
				style : {
					'text-align' : 'center'
				},
				width:'8%',
				text: '最高'
			}, {
				xtype: 'gridcolumn',
				align: 'right',
				dataIndex: 's15',
				style : {
					'text-align' : 'center'
				},
				width:'7%',
				text: '第15天'
			}, {
				xtype: 'gridcolumn',
				align: 'right',
				dataIndex: 's30',
				style : {
					'text-align' : 'center'
				},
				width:'7%',
				text: '第30天'
			}, {
				xtype: 'gridcolumn',
				align: 'center',
				dataIndex: 's90',
				style : {
					'text-align' : 'center'
				},
				width:'7%',
				text: '第90天'
			}, {
				xtype: 'gridcolumn',
				align: 'right',
				dataIndex: 's180',
				style : {
					'text-align' : 'center'
				},
				width:'7%',
				text: '第180天'
			}, {
				xtype: 'gridcolumn',
				align: 'center',
				dataIndex: 's270',
				style : {
					'text-align' : 'center'
				},
				width:'7%',
				text: '第270天'
			}, {
				xtype: 'gridcolumn',
				align: 'center',
				dataIndex: 's365',
				style : {
					'text-align' : 'center'
				},
				width:'7%',
				text: '第365天'
			}]
		},
		{
			xtype : 'panel',
			region : 'south',
			height:'45%',
			layout : {
				type : 'fit'
			},
			html:'<div id="chartPanel2" style="width:100%;height:100%;" />'
		}
	]
});