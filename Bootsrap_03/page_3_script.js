document.addEventListener('DOMContentLoaded', function () {
    // Начальные данные для первой загрузки страницы
    const itemsPerPage = 10;
    let currentPage = 0;
    let dataFromServer; // Добавим переменную для хранения данных

    // Найти контейнер для пагинации
    const paginationContainer = document.getElementById('paginationButtons');

    // Объявляем переменную для текущей активной кнопки "Редактировать"
    let activeEditButton = null;

    // Функция для выполнения GET-запроса к бэкенду
    function fetchData(page) {
        // Удаляем предыдущие слушатели событий
        removeEventListeners();

        fetch(`http://localhost:8080/pagination_manager1/vehicles?page=${page}&size=${itemsPerPage}`, {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token') // Прикрепляем токен из localStorage
            }
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                dataFromServer = data; // Сохраняем данные
                displayDataOnPage(data.content);
                updatePaginationButtons(data.totalPages, page);
                // Добавляем новые слушатели событий
                addEventListeners();
            })
            .catch(error => {
                console.error('There has been a problem with your fetch operation:', error);
            });
    }

    // Функция для отображения данных на странице
    function displayDataOnPage(data) {
        const tableBody = document.getElementById('vehicleTableBody');
        tableBody.innerHTML = ''; // Очищаем содержимое таблицы

        data.forEach(item => {
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
        document.getElementById('currentPage').textContent = currentPage + 1;
        document.getElementById('totalPages').textContent = dataFromServer.totalPages;
    }

    // Функция для обновления кнопок пагинации
    function updatePaginationButtons(totalPages, currentPage) {
        paginationContainer.innerHTML = ''; // Очищаем кнопки пагинации

        const liPrevious = document.createElement('li');
        liPrevious.className = 'page-item';
        liPrevious.innerHTML = `<a class="page-link" href="#" data-page="${currentPage - 1}">Предыдущая</a>`;
        liPrevious.addEventListener('click', function (event) {
            event.preventDefault();
            if (currentPage > 0) {
                fetchData(currentPage - 1);
            }
        });
        paginationContainer.appendChild(liPrevious);

        // Добавляем кнопку "Первая страница"
        const liFirst = document.createElement('li');
        liFirst.className = 'page-item';
        liFirst.innerHTML = `<a class="page-link" href="#" data-page="0">Первая страница</a>`;
        liFirst.addEventListener('click', function (event) {
            event.preventDefault();
            fetchData(0);
        });
        paginationContainer.appendChild(liFirst);

        // Добавляем кнопку "Последняя страница"
        const liLast = document.createElement('li');
        liLast.className = 'page-item';
        liLast.innerHTML = `<a class="page-link" href="#" data-page="${totalPages - 1}">Последняя страница</a>`;
        liLast.addEventListener('click', function (event) {
            event.preventDefault();
            fetchData(totalPages - 1);
        });
        paginationContainer.appendChild(liLast);

        const liNext = document.createElement('li');
        liNext.className = 'page-item';
        liNext.innerHTML = `<a class="page-link" href="#" data-page="${currentPage + 1}">Следующая</a>`;
        liNext.addEventListener('click', function (event) {
            event.preventDefault();
            if (currentPage < totalPages - 1) {
                fetchData(currentPage + 1);
            }
        });
        paginationContainer.appendChild(liNext);

        const liCounter = document.createElement('li');
        liCounter.className = 'page-item disabled';
        liCounter.innerHTML = `<span class="page-link">Страница ${currentPage + 1} из ${totalPages}</span>`;
        paginationContainer.appendChild(liCounter);
    }

    // Функция для отображения кнопки "Редактировать" при тапе (на мобильных устройствах)
    function handleTableRowClick(event) {
        const target = event.target;

        // Очищаем предыдущую активную кнопку
        if (activeEditButton) {
            activeEditButton.remove();
            activeEditButton = null;
        }

        if (target.tagName === 'TD') {
            const rowIndex = target.parentNode.rowIndex;
            const editButton = document.createElement('button');
            editButton.textContent = 'Редактировать';
            editButton.className = 'btn btn-sm btn-primary edit-button';
            // Сохраняем информацию о выбранной машине в кнопке
            editButton.dataset.vehicleIndex = rowIndex - 1; // -1 because of the header row
            // Добавляем кнопку в строку
            target.parentNode.appendChild(editButton);
            // Сохраняем текущую активную кнопку
            activeEditButton = editButton;
        }
    }

    // Функция для обработки клика по кнопке "Редактировать"
function handleEditButtonClick(event) {
    const target = event.target;

    if (target.classList.contains('edit-button')) {
        const rowIndex = target.dataset.vehicleIndex;
        const selectedVehicleId = dataFromServer.content[rowIndex].id;
        // Сохраняем id выбранной машины в localStorage
        localStorage.setItem('selectedVehicleId', selectedVehicleId);
        // Переходим на страницу редактирования
        window.location.href = 'page_6.html';
    }
}

    // Функция для добавления слушателей событий
    function addEventListeners() {
        // Добавляем слушатель события клика на таблицу (или тапа на мобильных устройствах)
        const tableBody = document.getElementById('vehicleTableBody');
        tableBody.addEventListener('click', handleTableRowClick);

        // Добавляем слушатель события клика на таблицу (или тапа на мобильных устройствах)
        tableBody.addEventListener('click', handleEditButtonClick);
    }

    // Функция для удаления слушателей событий
    function removeEventListeners() {
        const tableBody = document.getElementById('vehicleTableBody');
        tableBody.removeEventListener('click', handleTableRowClick);
        tableBody.removeEventListener('click', handleEditButtonClick);
    }

    // Загрузка данных для первой страницы при загрузке страницы
    fetchData(currentPage);
});
