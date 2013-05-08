// *** NOTE - this panel is due for DELETION and is replaced by the "ManageSectionsPanel.js" (RJM - 12/24/12)
//// *********************************************************************************
//// NOTE: Main panel for the manage templates which the other items/elements are added to
////*********************************************************************************
//Ext.ns('Boeing');
//Boeing.ManageTemplatesPanel = Ext.extend(Ext.Panel, {
//	extend : 'Ext.panel.Panel',
//	alias : 'widget.templatepanel',
//	headerCfg : {
//		tag : 'div',
//		cls : 'x-panel-header epic-page-panel-header-text'
//	},
//	initComponent : function() {
//		Ext.apply(this, {
//			width : panelWidth,
//			height : panelHeight,
//			defaults : {
//				style : {
//					padding : '0px'
//				} 
//			},
//			items : [ {
//				xtype : "navbar"
//			}
//		    , {xtype :'container' , height:'30' , id : 'errorMessageDiv' }
////			, {xtype : 'managetemplatesFormPanel', id: 'manageusersForm'}
////			, {xtype : 'container', height : '30'} 
//			, { xtype: 'sectionsList'}
////			, { xtype: 'treepanel'}
////			, { xtype: 'managesectionsTreePanel'}
////			,{ xtype:'usersList', id:'usersList'}
//			],
//			listeners : {
//				afterrender : function(panel) {
////					panel.setTitle('<div style="font-size:medium;">'+ panel.title + '</div>');
//				}
//			}
//		});
//		Boeing.MainPanel.superclass.initComponent.apply(this, arguments);
//	}
//});
//Ext.reg('managetemplatesPanel', Boeing.ManageTemplatesPanel);
//
//
////*********************************************************************************
////NOTE: form panel for user input of user name, first name and last name
////*********************************************************************************
//// *** NOTE: CURRENTLY NOT USED IF SWITCH FROM EDITING BY TEMPLATE TO EDITING BY SECTIONS (RJM - 12/21/12)
////Ext.ns('Boeing');
////Boeing.ManageTemplatesNavBar = Ext.extend(Ext.Toolbar, { 
////    initComponent:function() {
////        Ext.apply(this, {
////	        height: 30,
////			items:[{xtype: 'tbfill'}
////					,{xtype: 'tbseparator'}
////			    	,{xtype: 'tbbutton', text: 'Edit' , listeners:{
////			    		click:function(item, event){
////			    			alert("Editing");
//////			    			getUserList();
////			    			}
////			    		}
////			    	}
////			    	,{xtype: 'tbseparator'}
////			    	,{xtype: 'tbbutton', text: 'Clone' , listeners:{
////			    		click:function(item, event){
////			    			alert("Cloning");
//////			    			addUser();
////			    			}
////			    		}
////			    	}
////			    	,{xtype: 'tbseparator'}
////			    	,{xtype: 'tbbutton', text: 'Delete' , listeners:{
////			    		click:function(item, event){
////			    			alert("Deleting");
//////			    			addUser();
////			    			}
////			    		}
////			    	}
////			]
////        });
////        Boeing.ManageTemplatesNavBar.superclass.initComponent.apply(this, arguments);
////    }
////});
////Ext.reg('managetemplatesNavbar', Boeing.ManageTemplatesNavBar);
////
////
////
////
////Boeing.managetemplatesFormPanel = Ext.extend(Ext.form.FormPanel, {
////	initComponent : function() {
////		Ext.apply(this, {
////			width : '400px',
////			autoHeight : true,
////			trackResetOnLoad : true,
////			method : 'POST',
////			id : 'managetemplatesForm',
////			tbar: {xtype: 'managetemplatesNavbar'   },
////			defaults : {
////				style : {
////					padding: '0px',
////					bodyStyle: 'padding-left:10px'
////				}
////			},
////			items : [
////			         {xtype: 'fieldset', vertical: true,
////			        	 bodyStyle: 'padding-right:5px; padding-left:10px;',
////	    	             border: false,
////	    	             // defaults are applied to all child items unless otherwise specified by child item
////	    	             defaults: {
////	    	                columnWidth: 1.0,
////	    	                border: false
////	    	             },
////	    	             items:[
////	    	                    {
////	    	                    	xtype : 'container',
////	    	                    	height : '30'
////	    	                    },
////	    	                	{ xtype: 'combo',
////	    	                        fieldLabel: 'Templates:',
////	    	                        labelSeparator: '',
//////	    	                        width: 220, 
////	    	                        allowBlank: false,
////	    	                        loadingText: "Loading...",
////	    	                        emptyText: 'Select One',
////	    	                        id: 'templateTypeDD',
////	    	                        name: 'templateTypeDD', 
//////	    	                        listWidth : 295,
////	    	                        editable : false,
////	    	                        triggerAction : 'all',
////	    	                        displayField:'name',
////	    	                        valueField: 'id', //'description',
////	    	                        hiddenName : "idTypeHidden", //'contractTypeHidden',
////	    	                        emptyText : 'Select One',
//////	    	                        submitValue : true,
////	    	                        mode: 'local',
////	    	                        value : '',
////	    	                        store: templatesStore 
////	    	                	},	    	                    
////	    	                    {
////	    	                    	xtype : 'textfield',
////	    	                    	maxLength: 40,
////	    	                    	fieldLabel : 'Name', // we can create a component
////	    	                    	name : 'name', // with a configuration
////	    	                    	value : '', // object
////	    	                    	id : "name",
////	    	                    }, {
////	    	                    	xtype : 'textfield',
////	    	                    	maxLength: 40,
////	    	                    	fieldLabel : 'Description', // we can create a component
////	    	                    	name : 'description', // with a configuration
////	    	                    	value : '', // object
////	    	                    	id : "description",
////	    	                    }, {
////	    	                    	xtype : 'container',
////	    	                    	height : '30'
////	    	                    } 
////	    	         ]}   // end-of fieldset 
////			],
////			listeners : {
////				afterrender : function(panel) {
//////					panel.setTitle('<div style="font-size:medium;">'
//////							+ 'Manage Users' + '</div>');
////				}
////			}
////		});
////		Boeing.managetemplatesFormPanel.superclass.initComponent.apply(this, arguments);
////	} // eo function initComponent
////}); // eo Epic.buyerCheckListFormPanel
////Ext.reg('managetemplatesFormPanel', Boeing.managetemplatesFormPanel);
//
//// RJM - START
//
////*********************************************************************************
////NOTE: Expandable Tree Structure for Section Management: (RJM - 12/21/22)
////*********************************************************************************
//Boeing.managesectionsTreePanel = Ext.extend(Ext.tree.TreePanel, {
//	initComponent : function() {
//		Ext.apply(this, {
//			
////var grid = new Ext.tree.TreePanel({
////    renderTo: 'ext-test',
//			width : 400,
//			height : 400,
//			frame:true,
//			title: 'Manage Sections Tree Panel',
//			iconCls: 'icon-basket',
//			collapsible:true,
//			titleCollapse: true,
//			style: 'padding-bottom: 5px',
//			loader: new Ext.tree.TreeLoader(),
//			rootVisible: false,
//			lines: false,
//			root: new Ext.tree.AsyncTreeNode({
//				id: 'isroot',
//				expanded: true,
//				children: [
//				   {
//					   id:'1',text:'Menu1',  leaf: false, children:
//						   [ {id:'1',text: 'test', leaf: true } ]
//				   },
//				   { id:'2',text:'Menu2',leaf: true }
//				]
//			})
//		});
//	} // eo function initComponent
//}); // eo Epic.buyerCheckListFormPanel
//Ext.reg('managesectionsTreePanel', Boeing.managesectionsTreePanel);
//		
//		
//// RJM - FINISH
//Ext.ns('Boeing');
//Boeing.ManageSectionsNavBar = Ext.extend(Ext.Toolbar, { 
//    initComponent:function() {
//        Ext.apply(this, {
//	        height: 30,
//			items:[{xtype: 'tbfill'}
//					,{xtype: 'tbseparator'}
//			    	,{xtype: 'tbbutton', text: 'Edit' , listeners:{
//			    		click:function(item, event){
//			    			alert("Editing");
////			    			getUserList();
//			    			}
//			    		}
//			    	}
//			    	,{xtype: 'tbseparator'}
//			    	,{xtype: 'tbbutton', text: 'Clone' , listeners:{
//			    		click:function(item, event){
//			    			alert("Cloning");
////			    			addUser();
//			    			}
//			    		}
//			    	}
//			    	,{xtype: 'tbseparator'}
//			    	,{xtype: 'tbbutton', text: 'Delete' , listeners:{
//			    		click:function(item, event){
//			    			alert("Deleting");
////			    			addUser();
//			    			}
//			    		}
//			    	}
//			]
//        });
//        Boeing.ManageSectionsNavBar.superclass.initComponent.apply(this, arguments);
//    }
//});
//Ext.reg('managesectionssNavbar', Boeing.ManageSectionsNavBar);
//
//Boeing.SectionsList = Ext.extend(Boeing.EditorGridPanel, {
//	listeners:{
//				
//	},
//    initComponent:function() {
//    Ext.apply(this, {    	
//    		
//    		width: 900, // 900,   
//    		height: 400, //600,
//    		collapsible: false,
//    		titleCollapse: false,
//    		headerAsText: false,
//    		clicksToEdit: 1,
//    		stripeRows : true,
//    		store :
//    			new Ext.data.SimpleStore( {
//    				fields:[ 'sid', 'description'], 
//    				data: [
//    				         ['1234', 'Testing'],
//    				         ['1231', 'Testing2']
//    				]
//
//    			}),
////    			sectionsStore, 
//			tbar: {xtype: 'managesectionssNavbar'   },
//    		
//    		selModel: new Ext.grid.RowSelectionModel({singleSelect:true}),
//    	
//    		listeners: {
//			    beforerender: function(){	
////			    	this.store.load();
//						}
//			},
//			
//			view: new Ext.grid.GridView({
//				//markDirty: false
//			}),    		
//			columns: [
//			 {
//				dataIndex : 'id',
//				align : 'center',
//				header : '',
//				width : 30,
//				editable : false,
//				hideable : false,
//				renderer : function(data, cell, record,
//						rowIndex, columnIndex, store) {
//					return rowIndex + 1;
//				}
//			},
//			{
//				dataIndex : 'sid', 
//				header : 'Section ID',
//				sortable : true,
//				width : 75,
//				editable : false,
//				hideable : true,
//				hidden: false
//			},{
//				dataIndex : 'description', 
//				align : 'left',
//				header : 'Description',
//				sortable : true,
//				width : 200,
//				editable : false,
//				hideable : false
//			}
//			]    		    			
//    	});
//    Boeing.SectionsList.superclass.initComponent.apply(this, arguments);
//	} // eo function initComponent
//});  // eo Epic.SourceSelectionPanel
//Ext.reg('sectionsList', Boeing.SectionsList);
//
//// RJM2 - START
//
//
//
////var grid = new Ext.tree.TreePanel({
////    renderTo: 'ext-test',
////    frame:true,
////    title: 'Tree Panel',
////    iconCls: 'icon-basket',
////    collapsible:true,
////    titleCollapse: true,
////    style: 'padding-bottom: 5px',
////    loader: new Ext.tree.TreeLoader(),
////    rootVisible: false,
////    lines: false,
////    root: new Ext.tree.AsyncTreeNode({
////      id: 'isroot',
////      expanded: true,
////      children: [
////      {
////        id:'1',text:'Menu1',  leaf: false, children:
////        [ {id:'1',text: 'test', leaf: true } ]
////      },
////      { id:'2',text:'Menu2',leaf: true }
////      ]
////    })
////  });
//// RJM2 - FINISH
//
//
////*********************************************************************************
////NOTE: Data Store for manage templates
////*********************************************************************************
//var templatesStore = new Ext.data.JsonStore( {
//	url : "/TestSpring/view/managetemplates/getAllTemplates",
//	 
//	root : 'data',
//	timeout: 240000,
//	autoLoad : false, // NOTE: WAS "true"
//	baseParams: {  },
//
//	fields : [ {
//		name : 'name'
//		
//	}, {
//		name : 'description' 
//		
//	}, {
//		name : 'id'
//		
//	} 
//	]		
//});
//
//
//// RJM - START 4
//Ext.tree.TreePanel = Ext.extend(Ext.Panel, {
//    rootVisible : true,
//    animate : Ext.enableFx,
//    lines : true,
//    enableDD : false,
//    hlDrop : Ext.enableFx,
//    pathSeparator : '/',
//
//    /**
//     * @cfg {Array} bubbleEvents
//     * <p>An array of events that, when fired, should be bubbled to any parent container.
//     * See {@link Ext.util.Observable#enableBubble}.
//     * Defaults to <tt>[]</tt>.
//     */
//    bubbleEvents : [],
//
//    initComponent : function(){
//        Ext.tree.TreePanel.superclass.initComponent.call(this);
//
//        if(!this.eventModel){
//            this.eventModel = new Ext.tree.TreeEventModel(this);
//        }
//
//        // initialize the loader
//        var l = this.loader;
//        if(!l){
//            l = new Ext.tree.TreeLoader({
//                dataUrl: this.dataUrl,
//                requestMethod: this.requestMethod
//            });
//        }else if(Ext.isObject(l) && !l.load){
//            l = new Ext.tree.TreeLoader(l);
//        }
//        this.loader = l;
//
//        this.nodeHash = {};
//
//        /**
//        * The root node of this tree.
//        * @type Ext.tree.TreeNode
//        * @property root
//        */
//        if(this.root){
//            var r = this.root;
//            delete this.root;
//            this.setRootNode(r);
//        }
//
//
//        this.addEvents(
//
//           'append',
//           'remove',
//           'movenode',
//           'insert',
//           'beforeappend',
//           'beforeremove',
//           'beforemovenode',
//            'beforeinsert',
//            'beforeload',
//            'load',
//            'textchange',
//            'beforeexpandnode',
//            'beforecollapsenode',
//            'expandnode',
//            'disabledchange',
//            'collapsenode',
//            'beforeclick',
//            'click',
//            'containerclick',
//            'checkchange',
//            'beforedblclick',
//            'dblclick',
//            'containerdblclick',
//            'contextmenu',
//            'containercontextmenu',
//            'beforechildrenrendered',
//            'startdrag',
//            'enddrag',
//            'dragdrop',
//            'beforenodedrop',
//            'nodedrop',
//            'nodedragover'
//        );
//        if(this.singleExpand){
//            this.on('beforeexpandnode', this.restrictExpand, this);
//        }
//    },
//
//    // private
//    proxyNodeEvent : function(ename, a1, a2, a3, a4, a5, a6){
//        if(ename == 'collapse' || ename == 'expand' || ename == 'beforecollapse' || ename == 'beforeexpand' || ename == 'move' || ename == 'beforemove'){
//            ename = ename+'node';
//        }
//        // args inline for performance while bubbling events
//        return this.fireEvent(ename, a1, a2, a3, a4, a5, a6);
//    },
//
//
//    /**
//     * Returns this root node for this tree
//     * @return {Node}
//     */
//    getRootNode : function(){
//        return this.root;
//    },
//
//    /**
//     * Sets the root node for this tree. If the TreePanel has already rendered a root node, the
//     * previous root node (and all of its descendants) are destroyed before the new root node is rendered.
//     * @param {Node} node
//     * @return {Node}
//     */
//    setRootNode : function(node){
//        this.destroyRoot();
//        if(!node.render){ // attributes passed
//            node = this.loader.createNode(node);
//        }
//        this.root = node;
//        node.ownerTree = this;
//        node.isRoot = true;
//        this.registerNode(node);
//        if(!this.rootVisible){
//            var uiP = node.attributes.uiProvider;
//            node.ui = uiP ? new uiP(node) : new Ext.tree.RootTreeNodeUI(node);
//        }
//        if(this.innerCt){
//            this.clearInnerCt();
//            this.renderRoot();
//        }
//        return node;
//    },
//    
//    clearInnerCt : function(){
//        this.innerCt.update('');    
//    },
//    
//    // private
//    renderRoot : function(){
//        this.root.render();
//        if(!this.rootVisible){
//            this.root.renderChildren();
//        }
//    },
//
//    /**
//     * Gets a node in this tree by its id
//     * @param {String} id
//     * @return {Node}
//     */
//    getNodeById : function(id){
//        return this.nodeHash[id];
//    },
//
//    // private
//    registerNode : function(node){
//        this.nodeHash[node.id] = node;
//    },
//
//    // private
//    unregisterNode : function(node){
//        delete this.nodeHash[node.id];
//    },
//
//    // private
//    toString : function(){
//        return '[Tree'+(this.id?' '+this.id:'')+']';
//    },
//
//    // private
//    restrictExpand : function(node){
//        var p = node.parentNode;
//        if(p){
//            if(p.expandedChild && p.expandedChild.parentNode == p){
//                p.expandedChild.collapse();
//            }
//            p.expandedChild = node;
//        }
//    },
//
//    /**
//     * Retrieve an array of checked nodes, or an array of a specific attribute of checked nodes (e.g. 'id')
//     * @param {String} attribute (optional) Defaults to null (return the actual nodes)
//     * @param {TreeNode} startNode (optional) The node to start from, defaults to the root
//     * @return {Array}
//     */
//    getChecked : function(a, startNode){
//        startNode = startNode || this.root;
//        var r = [];
//        var f = function(){
//            if(this.attributes.checked){
//                r.push(!a ? this : (a == 'id' ? this.id : this.attributes[a]));
//            }
//        };
//        startNode.cascade(f);
//        return r;
//    },
//
//    /**
//     * Returns the default {@link Ext.tree.TreeLoader} for this TreePanel.
//     * @return {Ext.tree.TreeLoader} The TreeLoader for this TreePanel.
//     */
//    getLoader : function(){
//        return this.loader;
//    },
//
//    /**
//     * Expand all nodes
//     */
//    expandAll : function(){
//        this.root.expand(true);
//    },
//
//    /**
//     * Collapse all nodes
//     */
//    collapseAll : function(){
//        this.root.collapse(true);
//    },
//
//    /**
//     * Returns the selection model used by this TreePanel.
//     * @return {TreeSelectionModel} The selection model used by this TreePanel
//     */
//    getSelectionModel : function(){
//        if(!this.selModel){
//            this.selModel = new Ext.tree.DefaultSelectionModel();
//        }
//        return this.selModel;
//    },
//
//    /**
//     * Expands a specified path in this TreePanel. A path can be retrieved from a node with {@link Ext.data.Node#getPath}
//     * @param {String} path
//     * @param {String} attr (optional) The attribute used in the path (see {@link Ext.data.Node#getPath} for more info)
//     * @param {Function} callback (optional) The callback to call when the expand is complete. The callback will be called with
//     * (bSuccess, oLastNode) where bSuccess is if the expand was successful and oLastNode is the last node that was expanded.
//     */
//    expandPath : function(path, attr, callback){
//        if(Ext.isEmpty(path)){
//            if(callback){
//                callback(false, undefined);
//            }
//            return;
//        }
//        attr = attr || 'id';
//        var keys = path.split(this.pathSeparator);
//        var curNode = this.root;
//        if(curNode.attributes[attr] != keys[1]){ // invalid root
//            if(callback){
//                callback(false, null);
//            }
//            return;
//        }
//        var index = 1;
//        var f = function(){
//            if(++index == keys.length){
//                if(callback){
//                    callback(true, curNode);
//                }
//                return;
//            }
//            var c = curNode.findChild(attr, keys[index]);
//            if(!c){
//                if(callback){
//                    callback(false, curNode);
//                }
//                return;
//            }
//            curNode = c;
//            c.expand(false, false, f);
//        };
//        curNode.expand(false, false, f);
//    },
//
//    /**
//     * Selects the node in this tree at the specified path. A path can be retrieved from a node with {@link Ext.data.Node#getPath}
//     * @param {String} path
//     * @param {String} attr (optional) The attribute used in the path (see {@link Ext.data.Node#getPath} for more info)
//     * @param {Function} callback (optional) The callback to call when the selection is complete. The callback will be called with
//     * (bSuccess, oSelNode) where bSuccess is if the selection was successful and oSelNode is the selected node.
//     */
//    selectPath : function(path, attr, callback){
//        if(Ext.isEmpty(path)){
//            if(callback){
//                callback(false, undefined);
//            }
//            return;
//        }
//        attr = attr || 'id';
//        var keys = path.split(this.pathSeparator),
//            v = keys.pop();
//        if(keys.length > 1){
//            var f = function(success, node){
//                if(success && node){
//                    var n = node.findChild(attr, v);
//                    if(n){
//                        n.select();
//                        if(callback){
//                            callback(true, n);
//                        }
//                    }else if(callback){
//                        callback(false, n);
//                    }
//                }else{
//                    if(callback){
//                        callback(false, n);
//                    }
//                }
//            };
//            this.expandPath(keys.join(this.pathSeparator), attr, f);
//        }else{
//            this.root.select();
//            if(callback){
//                callback(true, this.root);
//            }
//        }
//    },
//
//    /**
//     * Returns the underlying Element for this tree
//     * @return {Ext.Element} The Element
//     */
//    getTreeEl : function(){
//        return this.body;
//    },
//
//    // private
//    onRender : function(ct, position){
//        Ext.tree.TreePanel.superclass.onRender.call(this, ct, position);
//        this.el.addClass('x-tree');
//        this.innerCt = this.body.createChild({tag:'ul',
//               cls:'x-tree-root-ct ' +
//               (this.useArrows ? 'x-tree-arrows' : this.lines ? 'x-tree-lines' : 'x-tree-no-lines')});
//    },
//
//    // private
//    initEvents : function(){
//        Ext.tree.TreePanel.superclass.initEvents.call(this);
//
//        if(this.containerScroll){
//            Ext.dd.ScrollManager.register(this.body);
//        }
//        if((this.enableDD || this.enableDrop) && !this.dropZone){
//           /**
//            * The dropZone used by this tree if drop is enabled (see {@link #enableDD} or {@link #enableDrop})
//            * @property dropZone
//            * @type Ext.tree.TreeDropZone
//            */
//             this.dropZone = new Ext.tree.TreeDropZone(this, this.dropConfig || {
//               ddGroup: this.ddGroup || 'TreeDD', appendOnly: this.ddAppendOnly === true
//           });
//        }
//        if((this.enableDD || this.enableDrag) && !this.dragZone){
//           /**
//            * The dragZone used by this tree if drag is enabled (see {@link #enableDD} or {@link #enableDrag})
//            * @property dragZone
//            * @type Ext.tree.TreeDragZone
//            */
//            this.dragZone = new Ext.tree.TreeDragZone(this, this.dragConfig || {
//               ddGroup: this.ddGroup || 'TreeDD',
//               scroll: this.ddScroll
//           });
//        }
//        this.getSelectionModel().init(this);
//    },
//
//    // private
//    afterRender : function(){
//        Ext.tree.TreePanel.superclass.afterRender.call(this);
//        this.renderRoot();
//    },
//
//    beforeDestroy : function(){
//        if(this.rendered){
//            Ext.dd.ScrollManager.unregister(this.body);
//            Ext.destroy(this.dropZone, this.dragZone);
//        }
//        this.destroyRoot();
//        Ext.destroy(this.loader);
//        this.nodeHash = this.root = this.loader = null;
//        Ext.tree.TreePanel.superclass.beforeDestroy.call(this);
//    },
//    
//    /**
//     * Destroy the root node. Not included by itself because we need to pass the silent parameter.
//     * @private
//     */
//    destroyRoot : function(){
//        if(this.root && this.root.destroy){
//            this.root.destroy(true);
//        }
//    }
//
//
//});
//
//Ext.tree.TreePanel.nodeTypes = {};
//
//Ext.reg('treepanel', Ext.tree.TreePanel);
//// RJM - FINISH 4
//
//// end-of-Boeing.MangeTemplatesPanel
//
