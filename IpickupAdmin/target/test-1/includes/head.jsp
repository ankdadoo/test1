<%@ include file="/includes/includes.jsp" %>
<meta http-equiv="Content-Script-Type" content="javaScript" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Phantom Works - SSOW</title>

<script language="javascript">
//paramaters go here
panelWidth = 1000;
panelHeight = 700;
contextPath = "<%= request.getContextPath() %>";
user = "<%= request.getSession().getAttribute("user") %>";
role = "<%= request.getSession().getAttribute("role") %>";
ssowScreen = "<%if(request.getParameter("ssowScreen") != null && !"".equals(request.getParameter("ssowScreen"))){out.print(request.getParameter("ssowScreen"));}else{out.print("metaData");}%>";
ssowId = "<%if(request.getParameter("ssowId") != null && !"".equals(request.getParameter("ssowId"))){out.print(request.getParameter("ssowId"));}else{out.print("0");}%>";
action = "<%if(request.getParameter("action") != null && !"".equals(request.getParameter("action"))){out.print(request.getParameter("action"));}else{out.print("new");}%>";
queueType = "<%if(request.getParameter("queueType") != null && !"".equals(request.getParameter("queueType"))){out.print(request.getParameter("queueType"));}else{out.print("0");}%>";

// RJM - ADDED 12/26/12
sectionScreen = "<%if(request.getParameter("sectionScreen") != null && !"".equals(request.getParameter("sectionScreen"))){out.print(request.getParameter("sectionScreen"));}else{out.print("metaData");}%>";
sectionId = "<%if(request.getParameter("sectionId") != null && !"".equals(request.getParameter("sectionId"))){out.print(request.getParameter("sectionId"));}else{out.print("0");}%>";

var rule = null;
var ssow = null;
var criteriaTrueCheckListId = null;
var criteriaAnyTrueCheckListId = null;
var criteriaAllTrueCheckListId =null;
var criteriaNotTrueCheckListId =null;
var sdrl = null;
var acronyms = null;
var dictionary = null;
var checkList = null;
var section = null;
var currentSectionId = null;
var sectionStatus = "";


function displayErrorMsg(errorMsg, errorDiv){
	var divName = "panelMessageDiv";
	var noScreen = true;

		color = "red";
	

	var div = null;
	//a supplied error div could exist for special reasons
	if(errorDiv != null){
		div = document.getElementById(errorDiv);
	}
	else{
		div = document.getElementById(divName);
	}
	
	if(eval(div)){
		noScreen = false;
        div.style.color = color;
        div.innerHTML = errorMsg;
	}
				
	if(noScreen){
		//if no screen is visible render to error div
		var div = document.getElementById("errorMessageDiv");
		div.style.color = color;
		div.innerHTML = errorMsg;
	}
} 



</script>

<jwr:style src="/bundledcss/all.css"/>
<jwr:script src="/bundled/all-lib.js" />

               
<script language="javascript">
	//used to avoid a security warning with extJs
	if(Ext.isIE6||Ext.isIE7||Ext.isAir){				
		Ext.BLANK_IMAGE_URL = "ext/resources/themes/images/default/tree/s.gif";
	}
</script>