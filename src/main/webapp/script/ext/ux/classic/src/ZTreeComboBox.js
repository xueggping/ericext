Ext.define('Ext.ux.ZTreeComboBox', {
    extend: 'Ext.form.field.Picker',
    xtype: 'ztreecombox',
    triggerCls: Ext.baseCSSPrefix + 'form-arrow-trigger',
    config: {
        selectedata:new jMap(),
        panelId:"ZTreePanel_",
        ztreeId:"ZTree_",
        zTreeObj:null,
        selectOnTab: true,
        maxPickerWidth: 450,
        minPickerWidth: 150,
        maxPickerHeight: 750,
        minPickerHeight: 300
    },
    editable: false,
    initComponent: function() {
        var me = this;
        me.callParent(arguments);
        this.addListener('select');
    },
    createPicker: function() {
        var me = this,
            picker = Ext.create('Ext.panel.Panel', {
            	id:me.panelId+me.id,
                floating: true,
                hidden: true,
                minWidth:me.minPickerWidth,
                maxWidth: me.maxPickerWidth,
                maxHeight: me.maxPickerHeight,
                minHeight: me.minPickerHeight,
                shadow: false,
                manageHeight: false,
                autoScroll:true,
                html:'<div id="'+me.ztreeId+me.id+'" class="ztree" style="margin:0;overflow-y:auto;overflow-x:auto;height:100%;width:100%;"></div>',
                listeners: {
            		afterrender : Ext.bind(me.afterrenderEvent, me)
                }
            }),
            view = picker;
	        view.on('render', me.setPickerViewStyles, me);
	        if (Ext.isIE9 && Ext.isStrict) {
	            view.on('highlightitem', me.repaintPickerView, me);
	            view.on('unhighlightitem', me.repaintPickerView, me);
	            view.on('afteritemexpand', me.repaintPickerView, me);
	            view.on('afteritemcollapse', me.repaintPickerView, me);
	        }
	        return picker;
    },
    setPickerViewStyles: function(view) {
        view.getEl().setStyle({
            'min-height': this.minPickerHeight + 'px',
            'max-height': this.maxPickerHeight + 'px'
        });
    },
    repaintPickerView: function() {
        var style = this.picker.getView().getEl().dom.style;
        style.display = style.display;
    },
    alignPicker: function() {
        var me = this,picker;
        if (me.isExpanded) {
            picker = me.getPicker();
            if (me.matchFieldWidth) {
                picker.setWidth(this.picker.getWidth());
            }
            if (picker.isFloating()) {
                me.doAlign();
            }
        }
    },
    afterrenderEvent:function(){
    	var me = this;
		Ext.Ajax.request( {
			url : me.ztreeConfig.url,
			method : 'POST',
			success : function(response, opts) {
				var result = Ext.JSON.decode(response.responseText);
				me.initZtree(result);
			},
			failure : function(response, opts) {
				Ext.Msg.alert('提示信息', "加载数据失败，请刷新后重试！");
			}
		});
    },
    initZtree : function(data) {
    	var me = this
		var zTreeSetting = {
			data : {
				keep : {
					parent : true
				},
				simpleData : {
					enable : true,
					idKey: me.ztreeConfig.root.idKey,
					pIdKey: me.ztreeConfig.root.pIdKey,
					rootPId:me.ztreeConfig.root.rootPId
				}
			},
			view : {
				dblClickExpand : false,
				showLine : true,
				selectedMulti : true,
			},
			check : {
				enable : true,
				chkStyle : me.ztreeConfig.chkStyle,
				chkboxType: me.ztreeConfig.chkboxType
			},
			callback : {
				onCheck:function(event, treeId, treeNode){
					me.selectItem(treeNode);
				}
			}
		};
		me.zTreeObj = $.fn.zTree.init($("#"+me.ztreeId+me.id), zTreeSetting, data);
		if(me.ztreeConfig.expandAll){
			me.zTreeObj.expandAll(true);
		}
		me.zTreeInitData();
    },
    zTreeInitData:function(){
    	var me = this;
    	me.selectedata.eachMap(function(value,rawValue){ 
			var node = me.zTreeObj.getNodeByParam("id",value , null);
			if(node == null){
				me.selectedata.remove(value);
			}else{
				me.zTreeObj.checkNode(node, true, false);
			}
		});
		me.setValue(me.selectedata);
    },
    selectItem: function(treeNode) {
        var me = this;
    	if(treeNode.checked){
    		me.selectedata.put(treeNode.id,treeNode.name);
		}else{
			me.selectedata.remove(treeNode.id);
		}
    	me.setValue(me.selectedata);
    	me.fireEvent('select', me, treeNode)
    },
    onExpand: function() {
        	var me = this;
        	me.afterrenderEvent();
    },
    setValue: function(selectedata) {
        var me = this;
        me.value = null;
        var rawValueT = null;
        if(selectedata){
	        selectedata.eachMap(function(value,rawValue){ 
	        	if(me.value){
	        		me.value = me.value + ',' + value;
	        		rawValueT = rawValueT + ',' + rawValue;
	        	}else{
	        		me.value = value;
	        		rawValueT = rawValue;
	        	}
		    });
        }else{
        	me.selectedata = new jMap();
        }
        me.setRawValue(rawValueT);
        return me;
    },
    getValue: function() {
        return this.value;
    },
    onTriggerClick: function() {
        var me = this;
        if (!me.readOnly && !me.disabled) {
            if (me.isExpanded) {
                me.collapse();
            } else {
                me.expand();
            }
            me.inputEl.focus();
        }
    }
});
function _show(key,name,value){
 return function()
    {
    	if(Ext.getCmp(key) != null && Ext.getCmp(key) != undefined){
        	Ext.getCmp(key).setValue(value);
    		Ext.getCmp(key).setRawValue(name);
    	}
    }
};
 
