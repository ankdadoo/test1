/*
	xBreadcrumbs (Extended Breadcrums) jQuery Plugin
	© 2009 ajaxBlender.com
	For any questions please visit www.ajaxblender.com 
	or email us at support@ajaxblender.com
*/

;(function($){
	/*  Variables  */
	$.fn.xBreadcrumbs = function(settings){
		var element = $(this);
		var 
		
		settings = $.extend({}, $.fn.xBreadcrumbs.defaults, settings);

		function _build(){
			if(settings.collapsible){
				var sz = element.children('LI').length;
				element.children('LI').children('A').css('white-space', 'nowrap').css('float', 'left');
				element.children('LI').children('A').each(function(i, el){
					if(i != sz - 1){
						$(this).css('overflow', 'hidden');
						$(this).attr('init-width', $(this).width());
						$(this).width(settings.collapsedWidth);
					}
				});
			}
			
            element.children('LI').mouseenter(function(){
                if($(this).hasClass('hover')){ return; }
                
            	_hideAllSubLevels();
            	if(!_subLevelExists($(this))){ return; }

            	// Show sub-level
            	var subLevel = $(this).children('UL');
            	_showHideSubLevel(subLevel, true);
            	
            	if(settings.collapsible && !$(this).hasClass('current')){
            		var initWidth = $(this).children('A').attr('init-width');
            		$(this).children('A').animate({width: initWidth}, 'normal');
            	}
            });
            
            element.children('LI').mouseleave(function(){
                var subLevel = $(this).children('UL');
                _showHideSubLevel(subLevel, false);
                
                if(settings.collapsible && !$(this).hasClass('current')){
                	$(this).children('A').animate({width: settings.collapsedWidth}, 'fast');
                }
            });
		};
		
		function _hideAllSubLevels(){
			element.children('LI').children('UL').each(function(){
                $(this).hide();
                $(this).parent().removeClass('hover');
			});
		};
		
		function _showHideSubLevel(subLevel, isShow){
			if(isShow){
                subLevel.parent().addClass('hover');
                if($.browser.msie){
                	var pos = subLevel.parent().position();
                	subLevel.css('left', parseInt(pos['left']));
                }
				if(settings.showSpeed != ''){ subLevel.fadeIn( settings.showSpeed ); } 
				else { subLevel.show(); }
			} else {
                subLevel.parent().removeClass('hover');
                if(settings.hideSpeed != ''){ subLevel.fadeOut( settings.hideSpeed ); } 
                else { subLevel.hide(); }
			}
		};
		
		function _subLevelExists(obj){
			return obj.children('UL').length > 0;
		};
		
		//    Entry point
		_build();
	};
	
	/*  Default Settings  */
	$.fn.xBreadcrumbs.defaults = {
		showSpeed:        'fast',
		hideSpeed:        '',
		collapsible:      false,
		collapsedWidth:   10
	};
})(jQuery);


function createBreadCrumbTrail(breadCrumbTrail){
//alert ("creating bread crumb trail" + breadCrumbTrail.length);
	var html = "";
	html += '<ul class="xbreadcrumbs" id="breadcrumbs-3">';
	for(var i=0; i < breadCrumbTrail.length; i++){
			var current = "";
			var link = "";
			if(breadCrumbTrail[i]["current"] == true){
				//alert (" current one ");
				current = "current";				
			}
			else if(breadCrumbTrail[i]["clickable"] == false){
				//alert ( "not clickable");
				current = "notclickable";				
			}
			else{
				link = breadCrumbTrail[i]["id"]+'GoTo()';
			}
			
			var end = "";
			if(breadCrumbTrail[i]["end"] == true){
				end = "end";				
			}
			
		    html += '<li class="'+current+' '+end+'" id="'+breadCrumbTrail[i]["id"]+'BC">';
		    html += '<a href="javascript:void(0);" onClick="'+link+'">'+breadCrumbTrail[i]["name"]+'</a>';
		    html += '</li>';
		    
		   
	}
    html += '</ul>';
  //  alert (html);
	return html;
	
}

var breadCrumbNames = [];
breadCrumbNames["metaData"] = "SSOW MetaData";
breadCrumbNames["basicQ"] = "Basic Question(s)";
breadCrumbNames["advancedQ"] = "Advanced Question(s)";
breadCrumbNames["confirmation"] = "SSOW Confirmation";

var breadCrumbTrail = [];
var crumbTrailSize = 4;
function addToBreadCrumbTrail(id, name){
	var tmpTrail = [];
	//remove duplicates
	for(var i=0; i < breadCrumbTrail.length; i++){
		if(breadCrumbTrail[i]["id"] != id){
			//if found remove from list only one unique id in the trail
			tmpTrail[tmpTrail.length] = breadCrumbTrail[i];
		}
	}
	
	//add to list
	tmpTrail[tmpTrail.length] = {id: id, name: name};
	//if size is too much take last set of size
	if(tmpTrail.length > crumbTrailSize){
		var tooBigTemp = [];
		for(var j=1; j < tmpTrail.length; j++){
			tooBigTemp[tooBidTemp.length] = tmpTrail[j];
		}
		
		tmpTrail = tooBigTemp;
	}
	
	breadCrumbTrail = tmpTrail;
	
	Ext.getCmp('breadCrumbsText').setText(createBreadCrumbTrail(breadCrumbTrail));
}