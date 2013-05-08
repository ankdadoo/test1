Ext.ns('Boeing');
Boeing.BlackBar = Ext.extend(Ext.Toolbar, {
    initComponent:function() {
        Ext.apply(this, { 
			defaults: {
		    	style: {
		        	padding: '0px'
		    	}
			},		
			width: panelWidth,
			baseCls: 'x-toolbar-blackbar x-form-element-nopadleft',
			items:[{
				xtype: 'tbtext',
				width: ((role != "admin") ? (panelWidth/5) : (panelWidth/6)), 
			//	text: '',				
				//text: '<a class="whiteLink" href="'+contextPath+'/view/home"><span style="font-weight:bold;text-decoration:none;color:white;"><font size=2>Home</font></span></a>',
				text: '<span style="font-weight:bold;text-decoration:none;color:white;"><font size=2>&nbsp;</font></span>',
				cls: 'gNavbar'
			},{
				xtype: 'tbtext',
				width: ((role != "admin") ? (panelWidth/5) : (panelWidth/6)),
			//	text: '',
				text: '<span style="font-weight:bold;text-decoration:none;color:white;"><font size=2>&nbsp;</font></span>',
				cls: 'gNavbar'
			},{
				xtype: 'tbtext',
				width: ((role != "admin") ? (panelWidth/5) : (panelWidth/6)), 
			//	text: '',
				text: '<span style="font-weight:bold;text-decoration:none;color:white;"><font size=2>&nbsp;</font></span>',
				cls: 'gNavbar'
			},{
				xtype: 'tbtext',
				width: ((role != "admin") ? (panelWidth/5) : (panelWidth/6)), 
			//	text: '',
				text: '<span style="font-weight:bold;text-decoration:none;color:white;"><font size=2>&nbsp;</font></span>',
				cls: 'gNavbar'
			},{
				xtype: 'tbtext',
				width: ((role != "admin") ? (panelWidth/5) : (panelWidth/6)), 
				hidden: (role != "admin"),
			//	text: '',
				text: '<span style="font-weight:bold;text-decoration:none;color:white;"><font size=2>&nbsp;</font></span>',
				cls: 'gNavbar'
			},{
				xtype: 'tbtext',
				width: ((role != "admin") ? (panelWidth/5) : (panelWidth/6)), 
			//	text: '',
				text: '<span style="font-weight:bold;text-decoration:none;color:white;"><font size=2>&nbsp;</font></span>',
				cls: 'gNavbar'
			}]
        });
        Boeing.BlackBar.superclass.initComponent.apply(this, arguments);
    } 
});
Ext.reg('blackbar', Boeing.BlackBar);