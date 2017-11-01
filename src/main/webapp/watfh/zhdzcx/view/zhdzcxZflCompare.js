Ext.define('zhdzcx.view.zhdzcxZflCompare',{
	extend : 'Ext.panel.Panel',//1
	alias : 'widget.zhdzcxZflCompare',//2
	id : 'zhdzcxZflCompare',//3
	type:4,
	chartX:'DT',
	chartY:'WSFE',
	Xunit:'mm',
	title: '平均蒸发量',
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
				width:'14%',
				dataIndex: 'STCD',
				align :'left',
				style : {
					'text-align' : 'center'
				},
				text: '站码'
			},{
				xtype: 'gridcolumn',
				dataIndex: 'STNM',
				width:'14%',
				align :'left',
				style : {
					'text-align' : 'center'
				},
				text: '站名'
			}, {
				xtype: 'gridcolumn',
				align: 'center',
				dataIndex: 'RVNM',
				width:'14%',
				style : {
					'text-align' : 'center'
				},
				text: '河流名称'
			}, {
				xtype: 'gridcolumn',
				width:'14%',
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
				width:'14%',
				dataIndex: 'WSFE',
				text: '水面蒸发量'
			}, {
				xtype: 'gridcolumn',
				align: 'right',
				style : {
					'text-align' : 'center'
				},
				width:'14.8%',
				dataIndex: 'WSFERCD',
				text: '水面蒸发量注解码'
			}, {
				xtype: 'gridcolumn',
				align: 'center',
				dataIndex: 'EETP',
				style : {
					'text-align' : 'center'
				},
				width:'14.8%',
				text: '蒸发器形式'
			}]
		},
		{
			xtype : 'panel',
			region : 'south',
			height:'45%',
			layout : {
				type : 'fit'
			},
			html:'<div id="chartPanel4" style="width:100%;height:100%;" />'
		}
	]
});
