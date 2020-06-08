function openTab(evt, tabType) {
    // Declare all variables
    var i, tabcontent, tablinks;

    // Get all elements with class="tabcontent" and hide them
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    // Get all elements with class="tablinks" and remove the class "active"
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    let tabTypeElement = document.getElementById(tabType);
    console.log(tabType)
    console.log(tabTypeElement)
    if (tabType === 'history') {
        console.log("Creating request to history")
        let xhttp = new XMLHttpRequest();
        try {
            xhttp.open("GET", "/api/v1/alerts", false);
            xhttp.send();
        } catch (e) {
            alert("Request for getting history failed! " + e.message)
            console.log(e.message)
            return;
        }
        if (xhttp.status !== 200) {
            alert("Request for getting history failed! " + xhttp.responseText)
            console.log(xhttp.status + " " + xhttp.responseText)
            return;
        }
        tabTypeElement.innerHTML = ''
        let response = JSON.parse(xhttp.responseText);
        for (i = 0; i < response.length; i++) {
            var p = document.createElement('p');
            p.style.wordWrap = 'break-word';
            let localDate = new Date(response[i].date).toLocaleString();
            p.appendChild(document.createTextNode(localDate + " " + response[i].message));
            tabTypeElement.appendChild(p);
        }
    }

    // Show the current tab, and add an "active" class to the button that opened the tab
    tabTypeElement.style.display = "block";
    evt.currentTarget.className += " active";
}