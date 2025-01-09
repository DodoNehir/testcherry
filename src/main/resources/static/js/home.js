let prevScrollPos = window.pageYOffset;
const navbar = document.getElementById("mainNavBar");

window.onscroll = function () {
  let currentScrollPos = window.pageYOffset;
  if (prevScrollPos > currentScrollPos) {
    navbar.classList.remove("hidden"); // 위로 스크롤 시 나타남
  } else {
    navbar.classList.add("hidden"); // 아래로 스크롤 시 숨김
  }
  prevScrollPos = currentScrollPos;
}