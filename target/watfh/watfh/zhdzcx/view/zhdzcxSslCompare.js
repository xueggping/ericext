Ext.define('zhdzcx.view.zhdzcxSslCompare',{
	extend : 'Ext.panel.Panel',//1
	alias : 'widget.zhdzcxSslCompare',//2
	id : 'zhdzcxSslCompare',//3
	type:7,
	chartX:'DT',
	chartY:'AVQS',
	Xunit:'㎏/s',
	title: '输沙率',
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
				width:'17%',
				dataIndex: 'STCD',
				align :'left',
				style : {
					'text-align' : 'center'
				},
				text: '站码'
			},{
				xtype: 'gridcolumn',
				dataIndex: 'STNM',
				width:'17%',
				align :'left',
				style : {
					'text-align' : 'center'
				},
				text: '站名'
			}, {
				xtype: 'gridcolumn',
				align: 'center',
				dataIndex: 'RVNM',
				width:'17%',
				style : {
					'text-align' : 'center'
				},
				text: '河流名称'
			}, {
				xtype: 'gridcolumn',
				width:'17%',
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
				width:'16.8%',
				dataIndex: 'AVQS',
				text: '输沙率'
			}, {
				xtype: 'gridcolumn',
				align: 'right',
				style : {
					'text-align' : 'center'
				},
				width:'15%',
				dataIndex: 'SDTP',
				text: '泥沙类型'
			}]
		},
		{
			xtype : 'panel',
			region : 'south',
			height:'45%',
			layout : {
				type : 'fit'
			},
			html:'<div id="chartPanel7" style="width:100%;height:100%;" />'
		}
	]
});