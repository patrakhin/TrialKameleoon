// Выполняем GET-запрос на сервер для получения данных
fetch('http://localhost:8080/pagination_manager1/vehicles?page=0&size=10', {
    method: 'GET',
    headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('token') // Прикрепляем токен из localStorage
    }
})
.then(response => {
    // Проверяем успешность ответа
    if (!response.ok) {
        throw new Error('Network response was not ok');
    }
    // Парсим JSON-ответ
    return response.json();
})
.then(dataFromServer => {
    // Обрабатываем полученные данные здесь
    const tableBody = document.getElementById('vehicleTableBody');
    const paginationContainer = document.getElementById('paginationButtons');
    const currentPageSpan = document.getElementById('currentPage');
    const totalPagesSpan = document.getElementById('totalPages');
    
    const itemsPerPage = 10;
    let currentPage = 0;

    function displayDataOnPage(data, page) {
        tableBody.innerHTML = ''; // Очищаем содержимое таблицы
        const startIndex = page * itemsPerPage;
        const endIndex = startIndex + itemsPerPage;
        const pageItems = data.slice(startIndex, endIndex);

        pageItems.forEach(item => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${item.id}</td>
                <td>${item.numberVehicle}</td>
                <td>${item.price}</td>
                <td>${item.yearOfManufacture}</td>
                <td>${item.mileage}</td>
                <td>${item.equipmentType}</td>
                <td>${item.carBrandName}</td>
                <td>${item.enterprisesName}</td>
            `;
            tableBody.appendChild(row);
        });

        // Устанавливаем текущую страницу и общее количество страниц
        currentPageSpan.textContent = currentPage + 1;
        totalPagesSpan.textContent = Math.ceil(data.length / itemsPerPage);
    }

    function updatePaginationButtons(totalPages, currentPage) {
        paginationContainer.innerHTML = ''; // Очищаем кнопки пагинации
        for (let i = 0; i < totalPages; i++) {
            const li = document.createElement('li');
            li.className = `page-item ${i === currentPage ? 'active' : ''}`;
            li.innerHTML = `<a class="page-link" href="#" data-page="${i}">${i + 1}</a>`;
            li.addEventListener('click', function (event) {
                event.preventDefault();
                currentPage = parseInt(event.target.getAttribute('data-page'));
                displayDataOnPage(dataFromServer.content, currentPage);
                updatePaginationButtons(totalPages, currentPage);
            });
            paginationContainer.appendChild(li);
        }
    }

    // Показываем данные на первой странице при загрузке
    displayDataOnPage(dataFromServer.content, currentPage);
    updatePaginationButtons(Math.ceil(dataFromServer.totalElements / itemsPerPage), currentPage);
})
.catch(error => {
    console.error('There has been a problem with your fetch operation:', error);
});