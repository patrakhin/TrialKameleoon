document.addEventListener('DOMContentLoaded', function () {
    // Получаем токен из localStorage
    const token = localStorage.getItem('token');

    // Если токен отсутствует, перенаправляем пользователя на страницу входа
    if (!token) {
        window.location.href = 'index.html'; 
    }

    // Получаем id выбранной машины из localStorage
    const selectedVehicleId = parseInt(localStorage.getItem('selectedVehicleId'), 10);

    // Если id отсутствует, перенаправляем пользователя на страницу с машинами
    if (!selectedVehicleId) {
        window.location.href = 'page_3.html';
    }

    // Получаем форму и добавляем обработчик события на отправку формы
    const editVehicleForm = document.getElementById('editVehicleForm');
    editVehicleForm.addEventListener('submit', function (event) {
        event.preventDefault(); // Предотвращаем стандартное поведение формы

        // Собираем данные из формы
        const formData = {
            numberVehicle: document.getElementById('numberVehicle').value,
            price: parseFloat(document.getElementById('price').value),
            yearOfManufacture: parseInt(document.getElementById('yearOfManufacture').value, 10),
            mileage: parseInt(document.getElementById('mileage').value, 10),
            equipmentType: document.getElementById('equipmentType').value,
            carBrandId: parseInt(document.getElementById('carBrand').value, 10),
            enterpriseId: parseInt(document.getElementById('enterprises').value, 10)
        };

        // Отправляем PATCH-запрос на бэкэнд для обновления данных о машине
        fetch(`http://localhost:8080/manager1/vehicle/${selectedVehicleId}`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            },
            body: JSON.stringify(formData)
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(updatedVehicle => {
            // Перенаправляем пользователя на страницу с машинами (страницу 3)
            window.location.href = 'page_3.html';
        })
        .catch(error => console.error('Ошибка при обновлении данных о машине:', error));
    });

    // Получаем данные о марках машин для заполнения списка
    fetch('http://localhost:8080/manager1/vehicles/car_brand', {
        headers: {
            'Authorization': 'Bearer ' + token
        }
    })
    .then(response => response.json())
    .then(data => {
        const carBrandSelect = document.getElementById('carBrand');
        // Создаем элементы для каждой марки машины и добавляем их в выпадающий список
        data.forEach(brand => {
            const option = document.createElement('option');
            option.value = brand.id; // Используйте значение, которое вам нужно
            option.textContent = brand.brandName;
            carBrandSelect.appendChild(option);
        });
    })
    .catch(error => console.error('Ошибка при получении данных для carBrand:', error));

    // Получаем данные о предприятиях для заполнения выпадающего списка
    fetch('http://localhost:8080/manager1/enterprises', {
        headers: {
            'Authorization': 'Bearer ' + token
        }
    })
    .then(response => response.json())
    .then(data => {
        const enterpriseSelect = document.getElementById('enterprises');

        // Создаем элементы для каждого предприятия
        data.forEach(enterprise => {
            const option = document.createElement('option');
            option.value = enterprise.id;
            option.textContent = enterprise.enterpriseName;

            // Добавляем скрытые атрибуты с дополнительными данными
            option.setAttribute('data-address', enterprise.enterprisesAddress);
            option.setAttribute('data-phone', enterprise.enterprisesPhone);

            enterpriseSelect.appendChild(option);
        });
    })
    .catch(error => console.error('Ошибка при получении данных для предприятий:', error));

    // Получаем данные о машине по её id для заполнения формы
    fetch(`http://localhost:8080/manager1/vehicle/${selectedVehicleId}`, {
        headers: {
            'Authorization': 'Bearer ' + token
        }
    })
    .then(response => response.json())
    .then(selectedVehicle => {
        // Заполняем форму данными о выбранной машине
        document.getElementById('numberVehicle').value = selectedVehicle.numberVehicle;
        document.getElementById('price').value = selectedVehicle.price;
        document.getElementById('yearOfManufacture').value = selectedVehicle.yearOfManufacture;
        document.getElementById('mileage').value = selectedVehicle.mileage;
        document.getElementById('equipmentType').value = selectedVehicle.equipmentType;

        // Устанавливаем значения выбранной марки машины и предприятия
        document.getElementById('carBrand').value = selectedVehicle.carBrandId;
        document.getElementById('enterprises').value = selectedVehicle.enterpriseId;
    })
    .catch(error => console.error('Ошибка при получении данных о машине:', error));
});
