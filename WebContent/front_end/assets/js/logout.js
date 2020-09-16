function logout() {
    
	let path = {};
    path.pathname = window.location.pathname;
    path.url = '/' + path.pathname.split('/')[1] + '/mem/mem.do';
    path.replaceUrl = '/' + path.pathname.split('/')[1] + '/front_end/home/home.jsp';
	
    let data = {
		action: 'logout'
	};

	fetch(path.url, {
	    body: JSON.stringify(data),
	    method: 'POST'
    }).then(function (response) {
        if (!response.ok) throw new Error(response.statusText);
        return response.json();
    }).then(function (json) {
    	location.replace(path.replaceUrl);
        return json;
    });

}
