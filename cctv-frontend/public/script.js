let arrow = document.querySelectorAll(".arrow");
for (var i = 0; i < arrow.length; i++) {
  arrow[i].addEventListener("click", (e)=>{
 let arrowParent = e.target.parentElement.parentElement;//selecting main parent of arrow
 arrowParent.classList.toggle("showMenu");
  });
}

document.addEventListener("DOMContentLoaded", function () {
  // 네비게이션 바 열기
  const sidebar = document.querySelector(".sidebar");
  sidebar.classList.remove("close");
});
