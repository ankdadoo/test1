Ext.ns('Boeing');
Ext.onReady(function(){
	new Boeing.Header({renderTo: 'headerDiv'});	
	new Boeing.ManageUsersPanel({renderTo: 'mainDiv', title: 'Home'});//, items:[{xtype: "navbar"}]});
	new Boeing.Footer({renderTo: 'footerDiv'});
});