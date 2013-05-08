Ext.ns('Boeing');
Boeing.Footer = Ext.extend(Ext.Container, {
    initComponent:function() {
        Ext.apply(this, {  
		width: panelWidth,
		defaults: {
        	style: {
            	padding: '0px'
        	}
		},     		
		items:[{xtype: "blackbar"},
		       {xtype:"container", cls: 'smalltype', html:"<center>BOEING is a trademark of Boeing Management Company.<br><a target=_blank href=\"javascript:popUpNewWin('http://www.boeing.com/copyright.html', 500, 500, '', 'popup2');\">Copyright</a> &copy;2010 Boeing. All rights reserved.<br><a href=\"http://www.boeing.com/companyoffices/aboutus/site_terms.html\">Security Agreement</a>"
		       }]  			
		});
        Boeing.Footer.superclass.initComponent.apply(this, arguments);
	}
});
Ext.reg('bfooter', Boeing.Footer);