$(document).ready(function() {

	$('.launch-modal').on('click', function(e) {
		$('.embed-responsive-item')[0].contentWindow.postMessage('{"event":"command","func":"' + 'playVideo' + '","args":""}', '*');
		e.preventDefault();
		$('#' + $(this).data('modal-id')).modal();
	});

	var icon = $('#play');
	icon.click(function(e) {
		icon.toggleClass('active');
		return false;
	});
	
	$("#modal-video").on("hidden.bs.modal", function () {
		$('.embed-responsive-item')[0].contentWindow.postMessage('{"event":"command","func":"' + 'pauseVideo' + '","args":""}', '*');
	});
	
	var modal = document.getElementById('modal-video');
	window.onclick = function(event) {
	    if (event.target == modal) {
	        modal.style.display = "none";
	    }
	}
	
    $("#input-comment").click(function(){
        $("#publish-comment").show();
    });
	
});

