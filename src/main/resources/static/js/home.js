import { sendRequest } from "./request.js";

document.addEventListener('DOMContentLoaded', () => {
  const accessToken = sessionStorage.getItem('accessToken');

  const beforeLogin1 = document.getElementById('beforeLogin1');
  const beforeLogin2 = document.getElementById('beforeLogin2');
  const afterLogin = document.getElementById('afterLogin');
  const logoutLink = document.getElementById('logoutLink');

  if (accessToken) {
    console.log('accessToken: ', accessToken);
    if (beforeLogin1) beforeLogin1.style.display = 'none';
    if (beforeLogin2) beforeLogin2.style.display = 'none';
    if (afterLogin) afterLogin.style.display = 'block';
    if (logoutLink) {
      logoutLink.addEventListener('click', (event) => {
        event.preventDefault();

        sendRequest('/members/logout', 'POST')
          .then(response => {
            sessionStorage.removeItem('accessToken');
            console.log('logout success');
            window.location.href = '/';
          })
          .catch(err => {
            console.error('logout failed', err.message);
            alert('로그아웃에 실패했습니다');
          });
      })
    }

  } else {
    console.log('accessToken 없음');
    if (beforeLogin1) beforeLogin1.style.display = 'block';
    if (beforeLogin2) beforeLogin2.style.display = 'block';
    if (afterLogin) afterLogin.style.display = 'none';
  }

});

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