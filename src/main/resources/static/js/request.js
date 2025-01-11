export function sendRequest(url, method = 'GET', body = null) {
    const accessToken = sessionStorage.getItem('accessToken');

    const headers = {
    // 'Content-Type': 'application/json'
    };

    if (accessToken) {
        headers['Authorization'] = accessToken;
    }

    const options = {
        method,
        headers
    };

    if (body) {
        options.body = JSON.stringify(body);
    }

    return fetch(url, options)
        .then(response => {
            if (!response.ok) {
                if (response.status === 401) {
                    console.log('401 response:', response);
                    alert("토큰이 만료되었습니다. 다시 로그인해주세요.");
                    sessionStorage.removeItem('accessToken');
                    // window.location.href = '/login'; // 로그인 페이지로 리디렉션
                } else if (response.status === 403) {
                    alert("권한이 없습니다.");
                } else {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
            }
            return response.json();
        })
        .catch(err => {
            console.error("request error: ", err.message);
        });
}