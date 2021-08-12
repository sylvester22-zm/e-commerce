/**
 * 
 */
function checkpasswords(){
	var newPassword=$('#newPassword').val();
	var confirmPassword=$('#confirmPassword').val();
	if(newPassword!=confirmPassword){
		$('#checkPasswordMatch').html('passwords do not match');
		$('#updateUserInfo').prop('disabled',true);
	}
	else if(newPassword==""&&confirmPassword==""){
		('#checkPasswordMatch').html('blank');
		$('#updateUserInfo').prop('disabled',false);
	}
      if(newPassword===confirmPassword){
		$('#checkPasswordMatch').html('passwords match');
		$('#updateUserInfo').prop('disabled',false);
}

}
$(document).ready(function(){
	$(".updateQuantity").on('change', function(){
		var id=this.id;
		console.log("this is the id"+id);
		console.log("working")
		$('#update'+id).css('display', 'inline-block');
	});
	$("#email").on('change',function(){
		console.log("sedn email")
		$("#sendEmail").css('display','inline-block')
	})
	$('#newPassword').keyup(checkpasswords);
	$('#confirmPassword').keyup(checkpasswords);
});
var togglebutton=document.querySelector('.navbar-toggle')
var navlinks=document.querySelector('.links');

 togglebutton.addEventListener('click',()=>{
 navlinks.classList.toggle('active')
 })
 ///for sticky navbar
 
 
 window.onscroll = function() {myFunction()};

// Get the navbar
var navbar = document.querySelector(".navbar");

// Get the offset position of the navbar
var sticky = navbar.offsetTop;

// Add the sticky class to the navbar when you reach its scroll position. Remove "sticky" when you leave the scroll position
function myFunction() {
  if (window.pageYOffset >= sticky) {
    navbar.classList.add("sticky")
  } else {
    navbar.classList.remove("sticky");
  }
}