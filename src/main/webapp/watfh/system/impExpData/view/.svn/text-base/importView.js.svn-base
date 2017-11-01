Ext.define('impexpdata.view.importView', {
    extend: 'Ext.window.Window',
    alias: 'widget.importView',
    id: 'importView',
	modal: true,
    constrain:true,
    resizable: false,
    height: 150,
    width: 500,
    layout: {
        type: 'column'
    },
    title: '批量导入',
    items: [{
		xtype: 'form',
		id:'importForm',
		layout: {
	        type: 'column'
	    },
	    border:false,
	    margin:'10',
		items:[{
            xtype: 'filefield',
            id: 'filePath',
            name: 'filePath',//不能改
            width: 450,
            margin:'10',
            allowBlank: false,
            blankText: '请选择上传文件',
			emptyText : '请选择上传文件',
            fieldLabel: '导入文件路径',
            labelPad: 1,
            labelWidth: 90,
            buttonText: '选择文件'
        }]
    }],
    initComponent : function() {
		this.buttons = [{ 
			xtype : 'button',
			listeners : {
				click : 'importExcel'
			},
			icon: getRootPath() + '/image/icon/watf_import.png',
			text : '导入'
		}, {
			xtype : 'button',
			text : '关闭',
			icon: getRootPath() + '/image/icon/cancel.png',
			scope : this,
			handler : this.close
		} ];
		this.callParent(arguments);
	}
});