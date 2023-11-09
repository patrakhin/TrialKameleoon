document.getElementById('showVehiclesBtn').addEventListener('click', function () {
    // Отправляем GET-запрос на бэкэнд
    fetch('http://localhost:8080/pagination_manager1/vehicles?page=0&size=10', {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + localStorage.getItem('token') // Прикрепляем токен из localStorage
        }
    })
    .then(response => response.json()) // Преобразуем ответ в JSON
    .then(data => {
        // Преобразуем данные в строку JSON и кодируем ее для передачи в URL
        var jsonData = encodeURIComponent(JSON.stringify(data));

        // Перенаправляем пользователя на третью страницу с данными в URL
        window.location.href = 'page_3.html?data=' + jsonData;
    })
    .catch(error => {
        // Обрабатываем ошибку
        console.error('Ошибка:', error);
    });
});