Ext.define('impexpdata.view.impExpDataMain', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.impExpDataMain',
    id: 'functionPageMain',
    controller: 'main',
    layout: {
        type: 'border'
    },
    border: false,
    items: [{
        xtype: 'panel',
        region: 'north',
        border: false,
        layout: {
            type: 'column'
        },
        items: [{
            xtype: 'combobox',
            margin: '5 5 5 5',
            width: '40%',
            labelWidth: 30,
            fieldLabel: '表名',
            id: 'tableNm',
            editable: true,
            displayField: 'name',
            valueField: 'ckey',
            //表单提交ID
            hiddenName: 'tableNm',
            store: Ext.create('Ext.data.Store', {
                fields: ['name', 'ckey'],
                pageSize : 10,
                proxy: {
                    type: 'ajax',
                    // 异步获取数据
                    url: getRootPath() + '/portDataController/getTableNm.do',
                    actionMethods: 'post',
                    reader: {
                        type: 'json',
                        root: 'content',
                        totalProperty: 'totalElements'
                    },
                    extraParams: {}
                },
                autoLoad: true,
                listeners:{
                	beforeload:function(){
                		var typeIdValue = Ext.getCmp('tableNm').rawValue;
                		Ext.getCmp('tableNm').store.proxy.extraParams = {pId:typeIdValue};
                	}
                }
            }),
            allowBlank: false,
            matchFieldWidth: false,//下拉选择器(picker)的宽度是否要准确地和表单项的宽度一致。false会根据内容自动拉伸  
            forceSelection: false,//true时，所选择的值限制在一个列表中的值，false时，允许用户设置任意的文本字段
            minChars: 1,//当输入几个字符时开始查询  
            pageSize: 10,//每页显示行数
            queryMode: 'remote',////请求方式 remote为远端访问(重要) 
            selectOnFocus:true ,
            ypeAhead: true,//为<code>true</code>时，配置了延迟后，如果匹配到已知的值将填充和自动选择键入的文本其余部分。
            selectFlag: true,//很关键，由于文本数据改变事件执行的优先级别比较高，需要根据一个标记来判断是否需要输入数据
            listeners: {
        		'select': 'stnmFilter'
            }
        },
        {
            xtype: 'button',
            icon: getRootPath() + '/image/icon/run.gif',
            margin: '5 5 5 5',
            text: '导入/导出Excel',
            menu: {
                xtype: 'menu',
                items: [{
                    xtype: 'menuitem',
                    listeners: {
                        click: 'downLoadTemplet'
                    },
                    icon: getRootPath() + '/image/icon/watf_exload.png',
                    text: '模板下载'
                },
                {
                    xtype: 'menuitem',
                    listeners: {
                        click: 'exportDataBtn'
                    },
                    icon: getRootPath() + '/image/icon/watf_export.png',
                    text: '导出Excel'
                },
                {
                    xtype: 'menuitem',
                    listeners: {
                        click: 'importData'
                    },
                    icon: getRootPath() + '/image/icon/watf_import.png',
                    text: '导入Excel'
                }]
            }
        }]
    },
    {
        xtype: 'form',
        border: false,
        region: 'center',
        id: 'exportForm',
        layout: {
            type: 'column'
        },
        items: [{
            xtype: 'fieldset',
            id: 'importField',
            title: '导出条件',
            width: '70%',
            hidden: true,
            collapsible: false,
            layout: {
                type: 'column'
            },
            /*  defaults: {
	            labelWidth: 89,
	            anchor: '100%',
	            layout: {
	                type: 'hbox',
	                defaultMargins: {top: 0, right: 5, bottom: 0, left: 0}
	            }
	        },*/
            items: [{
                xtype: 'combobox',
                margin: '5 5 5 5',
                labelAlign: 'right',
                labelWidth: 80,
                width: '40%',
                fieldLabel: '字段名',
                actionName: 'cloumn',
                actionGroup: 'exportData',
                editable: false,
                allowBlank: false,
                displayField: 'name',
                valueField: 'ckey',
                displayField: 'name',
                valueField: 'ckey',
                store: Ext.create('Ext.data.Store', {
                    fields: ['name', 'ckey', 'type'],
                    proxy: {
                        type: 'ajax',
                        // 异步获取数据
                        url: getRootPath() + '/portDataController/getCloumn.do',
                        actionMethods: 'post',
                        extraParams: {
                            tablenm: tbnm
                        }
                    },
                    autoLoad: false
                }),
                listeners: {
                    'select': function(combo, rowIndex, obj) {
                        var dataTime = Ext.getCmp("dataTime");
                        if (rowIndex.data.type == 'datetime') {
                            dataTime.show();
                            Ext.ComponentQuery.query('fieldset [actionName=adesc]')[0].allowBlank = true;
                        } else {
                            Ext.ComponentQuery.query('fieldset [actionName=adesc]')[0].allowBlank = false;
                            dataTime.hide();
                        }
                    }
                }
            },
            {
                xtype: 'combobox',
                margin: '5 5 5 5',
                labelAlign: 'right',
                labelWidth: 80,
                actionName: 'adesc',
                actionGroup: 'exportData',
                width: '40%',
                fieldLabel: '排序方式',
                mode: 'local',
                //                value:          'mrs',
                allowBlank: false,
                triggerAction: 'all',
                forceSelection: true,
                value: 'ASC',
                editable: false,
                displayField: 'name',
                valueField: 'value',
                queryMode: 'local',
                store: Ext.create('Ext.data.Store', {
                    fields: ['name', 'value'],
                    data: [{
                        name: '正序',
                        value: 'ASC'
                    },
                    {
                        name: '倒序',
                        value: 'DESC'
                    }]
                })
            },
            {
                xtype: 'label',
                width: '20%',
            },
            {

                xtype: 'fieldcontainer',
                /* combineErrors: true,
                msgTarget: 'side',*/
                id: 'dataTime',
                hidden: true,
                /*defaults: {
                    hideLabel: true
                },*/
                layout: {
                    type: 'column'
                },
                items: [{
                    xtype: 'label',
                    forId: 'startDate',
                    text: '开始时间:',
                    margin: '5 5 5 25'
                },
                {
                    xtype: 'textfield',
                    id: 'startDate',
                    width: '28%',
                    margin: '5 5 5 5',
                    border: false,
                    //        			value: Ext.Date.format(new Date().addMonth( - 1), 'Y-m-d H:i:s'),
                    name: 'startDate',
                    actionName: 'startDate',
                    actionGroup: 'exportData',
                    readOnly: true,
                    listeners: {
                        render: function(p) {
                            p.getEl().on('click',
                            function() {
                                WdatePicker({
                                    dateFmt: 'yyyy-MM-dd HH:mm:ss',
                                    lang: 'zh-cn',
                                    maxDate: Ext.getCmp('endDate').getValue()
                                });
                            });
                        }
                    },
                    validator: function(val) {
                        var errMsg = "开始时间不能大于结束时间";
                        var endDate = Ext.getCmp('endDate');
                        var value = endDate.getValue();
                        if ((value != "" && val >= value)) {} else {
                            errMsg = true;
                        }
                        return errMsg;
                    }
                },
                {
                    xtype: 'label',
                    forId: 'endDate',
                    text: '结束时间:',
                    margin: '5 5 5 25',
                    labelWidth: 80,
                },
                {
                    xtype: 'textfield',
                    id: 'endDate',
                    margin: '5 5 5 5',
                    width: '28%',
                    border: false,
                    //        			value: Ext.Date.format(new Date(), 'Y-m-d H:i:s'),
                    readOnly: true,
                    name: 'endDate',
                    actionName: 'endDate',
                    actionGroup: 'exportData',
                    listeners: {
                        render: function(p) {
                            p.getEl().on('click',
                            function() {
                                WdatePicker({
                                    dateFmt: 'yyyy-MM-dd HH:mm:ss',
                                    lang: 'zh-cn',
                                    minDate: Ext.getCmp('startDate').getValue()
                                });
                            });
                        }
                    },
                    validator: function(val) {
                        var errMsg = "结束时间不能小于开始时间";
                        var startDate = Ext.getCmp('startDate');
                        var value = startDate.getValue();
                        if ((value != "" && val != "" && val <= value)) {} else {
                            errMsg = true;
                        }
                        return errMsg;
                    }
                }]

            },
            {
                name: 'minutes',
                xtype: 'numberfield',
                margin: '5 5 5 5',
                width: '40%',
                labelAlign: 'right',
                actionName: 'lines',
                actionGroup: 'exportData',
                labelWidth: 80,
                fieldLabel: '导出行数(行)',
                maxValue: 1048575
            },
            {
                xtype: 'button',
                margin: '55 5 5 5',
                listeners: {
                    click: 'exportData'
                },
                icon: getRootPath() + '/image/icon/watf_export.png',
                text: '导出Excel'
            }]
        }]

    }]
});