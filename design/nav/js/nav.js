// $(document).ready(function(){	
// });


$(".hamburger").hide();
$(".cross").hide();
$(".menu").hide();
$(".ProjectArrowLeft").hide();

function hideMenuOnResize() {
	if (window.innerWidth > 686) {
		$(".hamburger").hide();
	} else {
		$(".menu").hide();
		$(".hamburger").show();
		$(".cross").hide();
	}
}
$(".hamburger").click(function() {
	$(".ProjectArrowLeft").hide();
	$(".menu").slideToggle("slow", function() {
		$(".hamburger").hide();
		$(".cross").show();
	});
});
$(".cross").click(function() {
	$(".menu").slideToggle("slow", function() {
		$(".cross").hide();
		$(".hamburger").show();
	});
});
var c = 0;
$(".submenuList").click(function() {
	$(".submenu").slideToggle("slow", function() {
		if (c == 0) {
			$(".x").hide();
			$(".submenu").show();
			$(".ProjectArrow").hide();
			$(".ProjectArrowLeft").show();
			$(".ProjectArrowLeft").css('top', '7px');
			// $(".ProjectArrow").css('color','#09152c');
			c++;
		} else if (c == 1) {
			$(".ProjectArrow").show();
			$(".submenuList").show();
			$(".x").show();
			$(".ProjectArrow").css('color', '#999');
			$(".ProjectArrowLeft").hide();
			c = 0;
		}
	});
});