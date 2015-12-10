/**
 * 
 */
function font2( _size ) {

	var lines = document.getElementById( "wrap-box" ).getElementsByTagName( "p" );

	for ( var i = 0; i < lines.length; ++i ) {
		var line = lines[ i ];
		line.style.fontSize = ( _size + "px" );
	}
}

function color2( _color ) {

	var lines = document.getElementById( "wrap-box" ).getElementsByTagName( "p" );

	for ( var i = 0; i < lines.length; ++i ) {
		var line = lines[ i ];
		line.style.color = _color;
	}
}

window.setTimeout( function() {

	document.addEventListener( "keyup", function( e ) {
		var keyCode = e.keyCode;
		if ( keyCode == 39 ) {
			document.getElementById( "next0" ).click();
		} else if ( keyCode == 37 ) {
			document.getElementById( "previous0" ).click();
		}
	} );

}, 10 );
