Ext.ns('Boeing');
Ext.onReady(function(){
	new Boeing.Header({renderTo: 'headerDiv'});	
	new Boeing.SearchPanel({renderTo: 'mainDiv', title: 'SSOW Search Screen'});//, items:[{xtype: "navbar"}]});
	new Boeing.Footer({renderTo: 'footerDiv'});
});