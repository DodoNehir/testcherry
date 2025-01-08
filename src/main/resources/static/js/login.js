document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.querySelector('#loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', function (event) {
            event.preventDefault(); // 기본 제출 막기

            const usernameInput = document.getElementById('username').value.trim();
            const passwordInput = document.getElementById('password').value.trim();

            // 유효성 검사
            if (usernameInput === '') {
                alert('아이디를 입력해주세요.');
                usernameInput.focus(); // 아이디 입력란에 포커스
                return;
            }

            if (passwordInput === '') {
                alert('비밀번호를 입력해주세요.');
                passwordInput.focus(); // 비밀번호 입력란에 포커스
                return;
            }

            // 특수 문자 이스케이핑
            const escapedUsername = usernameInput.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;').replace(/'/g, '&#039;');
            const escapedPassword = passwordInput.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;').replace(/"/g, '&quot;').replace(/'/g, '&#039;');


            fetch('/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded', // FormData 대신 urlencoded 방식으로 전송
                },
                body: `username=${encodeURIComponent(escapedUsername)}&password=${encodeURIComponent(escapedPassword)}` // URL 인코딩
            })
                .then(response => {
                    if (!response.ok) {
                        return response.json().then(data => {
                            throw new Error(data?.message || "아이디와 비밀번호를 확인해주세요");
                        })
                    }
                    return response.json();
                })
                .then(data => {
                    const accessToken = data.accessToken;
                    if (accessToken) {
                        sessionStorage.setItem('accessToken', accessToken);
                        console.log("Access Token:", accessToken);
                        window.location.href = "/"; // 리디렉션
                    } else {
                        console.error('accessToken이 없습니다.', data);
                        throw new Error("아이디와 비밀번호를 확인해주세요");
                    }
                })
                .catch(error => {
                    const loginResult = document.querySelector('#loginResult');
                    loginResult.textContent = `로그인 오류: ${error.message}`;
                    loginResult.className = "text-danger mt-2 mb-2";
                });
        });
    }

    // // 페이지 로드 시 Session Storage에서 Access Token 확인
    // const storedAccessToken = sessionStorage.getItem('accessToken');
    // if (storedAccessToken) {
    //     console.log("Stored Access Token:", storedAccessToken);

    //     // 저장된 Access Token을 사용하여 API 요청 등 수행
    //     fetch('/api/protected', {
    //         headers: {
    //             Authorization: storedAccessToken,
    //         },
    //     }).then(res => console.log(res));
    // }
});

// function logout() {
//     sessionStorage.removeItem('accessToken');
//     localStorage.removeItem('refreshToken');
//     window.location.href = "/login";
// }