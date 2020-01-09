function validate() {
	var name = document.getElementById("dbName").value;
	if (name == '') {
		alert('Please enter a valid DB Name.');
		return false;
	} else {
		return true;
	}
}