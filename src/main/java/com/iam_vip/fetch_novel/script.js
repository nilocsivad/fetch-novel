/**
 * 
 */
function font2(_size) {

	var lines = document.getElementById("wrap-box").getElementsByTagName("p");

	for (var i = 0; i < lines.length; ++i) {
		var line = lines[i];
		line.style.fontSize = (_size + "px");
	}
}

function color2(_color) {

	var lines = document.getElementById("wrap-box").getElementsByTagName("p");

	for (var i = 0; i < lines.length; ++i) {
		var line = lines[i];
		line.style.color = _color;
	}
}