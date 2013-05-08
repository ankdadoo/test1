Ext.application({
	name: 'Boeing',
	launch: function () {
    	Ext.create('Boeing.Header', {renderTo: 'headerDiv'});
    	
       // Ext.create('Boeing.MainPanel', {title: 'Home', renderTo: 'mainDiv'});//, items:[{xtype: "navbar"}]});
        Ext.create('Boeing.Footer', {renderTo: 'footerDiv'});
    }
});