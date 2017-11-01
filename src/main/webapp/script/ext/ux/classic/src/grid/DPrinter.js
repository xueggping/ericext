Ext.define("Ext.ux.grid.DPrinter",{  
  
    requires: 'Ext.XTemplate',  
  
    statics: {  
        print: function(grid,bt) {  
            var me=this;  
            me.bt = bt;
            me.initColumns(grid.columns);  
            var columns = this.columns;  
            me.headings=me.initHeader(grid.columns);  
            var body     = Ext.create('Ext.XTemplate', this.bodyTpl).apply(columns);  
            var htmlMarkup = [  
                '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">',  
                '<html>',  
                  '<head>',  
                    '<meta content="text/html; charset=UTF-8" http-equiv="Content-Type" />',  
                    '<link href="' + this.stylesheetPath + '" rel="stylesheet" type="text/css" media="print" />',  
                    '<title></title>',
                    '<style> ' +
                    '    body { ' +
				    '        height: 100%; '+
				    '        width: 100%;'+
				    '        margin: 0; padding: 0; '+
				    '        algin:center;' +
				    '    } '+
				    '    table {'+
				    '        height: 95%;'+
				    '        width: 98%;   '  +    /*  必须设置一个宽度， margin: 0 auto才能使之居于父组件中央*/
				    '        margin: 10px auto; '+ /*  通过设置外边距（margin）中的左右外边距属性为auto使之居于父组件（body）中间*/
				    '		 border-collapse:collapse;'+
				    '    }'+
			    	'    tr{'+
			   		'     	height: 40px;'+
			    	'     	width: 100%;'+
			    	'    }'+
			    	'    td{'+
			    	'     	height: 100%; ' +
			    	'    }'+
                    '</style>',
                  '</head>',  
                  '<body>',  
                    '<table>'+this.headings+  
                      '<tpl for=".">',  
                        body,  
                      '</tpl>',  
                    '</table>',  
                  '</body>',  
                '</html>'             
            ];  
            var html = Ext.create('Ext.XTemplate', htmlMarkup).apply(bt.printConfig.data);   
            var win = window.open('', 'printgrid');  
            win.document.clear();
            win.document.write(html);  
            me.printData(win); 
        },  
        printData:function(win){
        	if (this.printAutomatically){  
                win.print();  
                win.close();  
            } 
        },
        
        initColumns:function(columns){  
            var me=this;
            me.columns = [];
            Ext.each(columns,function(column){
                if(column.dataIndex!=null){ 
                	if(!column.hidden){
	                    me.columns.push(column);  
                	}
                }  
                else{  
                    me.initColumns(column.items.items);  
                }  
            });  
        },  
          
        initHeaderCtl:function(columns,index,maxrow,ischild){  
            var me=this;  
            var html= '<tr align="center">';  
            if(ischild){
            	html = '';
            }
            var colspan=0;  
            Ext.each(columns,function(column){  
                if(column.dataIndex!=''){  
                    if(maxrow-index>1)html+='<th style="border:solid #000 1px;" rowspan='+eval(maxrow-index)+'>'+column.text+'</th>';  
                    else html+='<th style="border:solid #000 1px;">'+column.text+'</th>';  
                    colspan++;  
                }  
                else{  
                    var temp=me.initHeaderCtl(column.items.items,index+1,maxrow,true);  
                    html+='<th style="border:solid #000 1px;" colspan='+temp+'>'+column.text+'</th>';  
                    colspan+=temp;  
                }  
            });  
            if(!ischild){
	            html+='</tr>';  
            }
            var temp=this.headerCtl[index];  
            if(temp!=null && temp!=''){  
                html=temp+html;  
            }  
            this.headerCtl[index]=html;  
            return colspan;  
        },  
          
        initHeader:function(columns){ 
            var me=this; 
            var display = 'none';
            if(me.bt.searchTime){
            	display = 'inline';
            }
        	var printTile = '<tr>'+
				            	'<td colspan="'+columns.length+'" align="center" height="40">'+
					            '    <div id="printTitle" style="font-weight: bold; text-align: center; font-size: 16pt;">'+
					            	 me.bt.printConfig.Title +
					            '    </div>'+
					           	' </td>'+
				        	'</tr>'+
				        	'<tr>'+
				            	'<td colspan="'+columns.length+'" height="40">'+
				            	'    <div id="printSearch" style="width:50%;float:left;text-align: left; font-size: 10pt;display:' + display + ';">'+
					            '	 	时间：'+  me.bt.searchTime +
					            '    </div>'+
					            '    <div id="printTime" style="width:50%; float:right; text-align: right; font-size: 10pt;">'+
					            '	 	打印日期：'+ Ext.Date.format( new Date(), "Y年m月d日" ) +
					            '    </div>'+
					           	' </td>'+
				        	'</tr>';
            me.headerCtl = [];
            columns = me.initRealColumns(columns);
            me.getMaxRows(columns,1);  
            me.initHeaderCtl(columns,0,me.maxrow);  
            var headerCtls=me.headerCtl;
            var headings='';  
            Ext.each(headerCtls,function(headerCtl){ 
            	if(headerCtl.indexOf('tr') < 0){
            		headerCtl = '<tr align="center">' + headerCtl + '</tr>';
            	}
                headings+=headerCtl;  
            });  
            return printTile + headings;  
        },  
        initRealColumns:function(columns){
        	var cs = {};
        	var cls = [];
        	Ext.each(columns,function(column){  
                if(column.ownerCt.xtype && column.ownerCt.xtype == 'gridcolumn'){  
                   cs[column.ownerCt.ordered] = column.ownerCt;
                }else{
                   cs[column.ordered] = column;
                }  
            });
        	for(var or in cs){
        		if(!cs[or].hidden){
	        		cls.push(cs[or]);
        		}
        	}
        	return cls;
        },
        getMaxRows:function(columns,rowIndex){ 
            var me=this;  
            Ext.each(columns,function(column){  
                if(column.dataIndex==''){  
                    var temp=me.getMaxRows(column.items.items,rowIndex+1);    
                }  
            });  
            if(rowIndex>me.maxrow){  
                me.maxrow=rowIndex;  
            }  
              
            return me.maxrow;  
        },  
        columns:[],  
        headerCtl:[],  
        maxrow:0,  
        headings:'',  
        stylesheetPath: getRootPath()+'/script/ext/ux/classic/src/grid/print.css',  
        printAutomatically: true,  
        bt:{},  
        bodyTpl: [  
            '<tr align=center >',  
                '<tpl for=".">',  
                    '<td style="border:solid #000 1px;">\{{dataIndex}\}</td>',  
                '</tpl>',  
            '</tr>'  
        ]  
    }  
});  