function showPageByTree(url,menuId,firstMenuId,type) {
	if (url == null || url == "") {
		return false;
	}
	var newUrl = url;
	if(url.indexOf("?") == -1){
		newUrl = newUrl + "?menuId=" + menuId + "&firstMenuId=" + firstMenuId;
	}else{
		newUrl = newUrl + "&menuId=" + menuId + "&firstMenuId=" + firstMenuId;
	}
	window.location.href = newUrl;
}