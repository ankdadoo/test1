Ext.ns('Boeing');
Ext.onReady(function(){
	
	
//alert ("ssow Screen " + ssowScreen);
	
	breadCrumbTrail[breadCrumbTrail.length] = {id: 'metaData', name: 'SSOW MetaData', clickable: true};
	
	
			breadCrumbTrail[breadCrumbTrail.length] = {id: 'basicQ', name:  'Basic Questions(s)' , clickable: true};
		//}else {
			breadCrumbTrail[breadCrumbTrail.length] = {id:   'advancedQ', name:'Advanced Question(s)', clickable: true};
		//}
	//}
	
	breadCrumbTrail[breadCrumbTrail.length] = {id: 'confirmation', name: 'SSOW Confirmation', end: true, clickable: true};
	
	//}

	
	//alert ( "ssow id " + ssowId)
	//alert ("action " + action );
	
	
	
	
	new Boeing.Header({renderTo: 'headerDiv'});	
	
	Ext.Ajax.request({
		url :   "/TestSpring/view/ssow/loadSsow",
		method: 'POST',
		timeout: 240000,
		headers: {
		    'Accept': 'application/json'
		},	
		params:{
			ssowId: ssowId,
			action: action
			
		},		
		success: function ( result, request) {		
		//	alert("*** in the success method ");
			var data = Ext.decode(result.responseText);
			ssow = data.data;
		
			ssowId = ssow.id;
			//bclId = bcl.id;
		//	alert("loaded ssow successfully " + ssow.id );
			
			
			if(ssowScreen == "metaData"){
			//	alert ("2");		
		new Boeing.SsowPanel({renderTo: 'mainDiv', title: 'Phantom Works SSOW MetaData Screen'});//, items:[{xtype: "navbar"}]});
				
		setbuyerCheckListScreen(ssowScreen, breadCrumbNames[ssowScreen]); 
			}				
			else if(ssowScreen == "basicQ"){
				
				//alert ("3");
				new Boeing.SsowPanel({renderTo: 'mainDiv', title: 'Phantom Works SSOW BasicQ Screen'});//, items:[{xtype: "navbar"}]});
				setbuyerCheckListScreen(ssowScreen, breadCrumbNames[ssowScreen]);   	
			}
			else if(ssowScreen == "advancedQ") {	
			//	alert ("4");
			//	new Epic.BuyerCheckListScreen({renderTo: 'mainDiv', id: 'buyerCheckListScreenObj', parameterMap: parameterMap});
				new Boeing.SsowPanel({renderTo: 'mainDiv', title: 'Phantom Works SSOW AdvancedQ Screen'});//, items:[{xtype: "navbar"}]});
				setbuyerCheckListScreen(ssowScreen, breadCrumbNames[ssowScreen]);   				
			}
	
			else if(ssowScreen == "confirmation") {	
					//	alert ("4");
					//	new Epic.BuyerCheckListScreen({renderTo: 'mainDiv', id: 'buyerCheckListScreenObj', parameterMap: parameterMap});
				new Boeing.SsowPanel({renderTo: 'mainDiv', title: 'Phantom Works SSOW Confirmation Screen'});//, items:[{xtype: "navbar"}]});
						setbuyerCheckListScreen(ssowScreen, breadCrumbNames[ssowScreen]);   				
					}else {
					//	alert ("didnt hit anyone ");
					}
		
		},

		failure: function ( result, request) {
//	alert("eception happened");
	var data = Ext.decode(result.responseText);
	displayErrorMsgEpicService(data, data.data);			
		}
});
	
	
	
	
	//new Boeing.SsowPanel({renderTo: 'mainDiv', title: 'Phantom Works SSOW Screen'});//, items:[{xtype: "navbar"}]});
	new Boeing.Footer({renderTo: 'footerDiv'});
});


function setbuyerCheckListScreen(screen, name){	
	//alert("in bcl screen method;")	
		for(var i=0; i < breadCrumbTrail.length; i++){
		
			if(breadCrumbTrail[i]["id"] == screen){
		
				breadCrumbTrail[i]["current"] = true;
			}
			else{
			
				breadCrumbTrail[i]["current"] = false;	
			}
		}
		
		Ext.getCmp('breadCrumbsText').setText(createBreadCrumbTrail(breadCrumbTrail));
		
		
		

	}



function metaDataGoTo() {
	
//alert ( "metadata to go");
//alert ("ssowid " + ssowId);
	document.location.href = '/TestSpring/view/ssow.jsp?ssowId=' + ssow.id + '&action=' + action + '&ssowScreen=metaData';
	
}


function basicQGoTo() {
	
//alert("action" + action);
	document.location.href = '/TestSpring/view/ssow.jsp?ssowId=' + ssow.id + '&action=' + action + '&ssowScreen=basicQ';
}

function advancedQGoTo() {
	
//	alert ( "advanced Q to go");
	document.location.href = '/TestSpring/view/ssow.jsp?ssowId=' + ssow.id + '&action=' + action + '&ssowScreen=advancedQ';
}

function confirmationGoTo() {
	
	
	document.location.href = '/TestSpring/view/ssow.jsp?ssowId=' + ssow.id + '&action=' + action + '&ssowScreen=confirmation';
}