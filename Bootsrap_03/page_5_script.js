document.addEventListener('DOMContentLoaded', function () {
    // Получаем токен из localStorage
    const token = localStorage.getItem('token');

    // Если токен отсутствует, перенаправляем пользователя на страницу входа
    if (!token) {
        window.location.href = 'index.html'; // Заменить на фактический путь
    }

    // Получаем форму и добавляем обработчик события на отправку формы
    const createVehicleForm = document.getElementById('createVehicleForm');
    createVehicleForm.addEventListener('submit', function (event) {
        event.preventDefault(); // Предотвращаем стандартное поведение формы

        // Получаем значения полей формы
        const numberVehicle = document.getElementById('numberVehicle').value;
        const price = document.getElementById('price').value;
        const yearOfManufacture = document.getElementById('yearOfManufacture').value;
        const mileage = document.getElementById('mileage').value;
        const equipmentType = document.getElementById('equipmentType').value;
        const carBrand = document.getElementById('carBrand').value;
        const enterpriseId = document.getElementById('enterprises').value; // Используйте значение, которое вам нужно

        // Создаем объект с данными для отправки на бэкэнд
        const vehicleData = {
            numberVehicle: numberVehicle,
            price: parseFloat(price),
            yearOfManufacture: parseInt(yearOfManufacture),
            mileage: parseInt(mileage),
            equipmentType: equipmentType,
            carBrand: carBrand,
            enterpriseId: parseInt(enterpriseId) // Используйте значение, которое вам нужно
            // Добавьте другие поля по необходимости
        };

        // Отправляем POST-запрос на бэкэнд для создания новой машины
        fetch('http://localhost:8080/manager1/vehicles/new', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            },
            body: JSON.stringify(vehicleData)
        })
        .then(response => response.json())
        .then(data => {
            // Обработка успешного ответа от сервера (если необходимо)
            console.log('Машина успешно создана:', data);

            // Перенаправляем пользователя на страницу, куда нужно после создания машины
            window.location.href = 'page_3_1.html'; // Заменить на фактический путь
        })
        .catch(error => {
            // Обработка ошибки
            console.error('Ошибка при создании машины:', error);
        });
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
        const enterpriseSelect = document.getElementById('enterprises'); // Используйте значение, которое вам нужно

        // Очищаем выпадающий список перед добавлением новых элементов
        enterpriseSelect.innerHTML = '';

        // Создаем элементы для каждого предприятия
        data.forEach(enterprise => {
            const option = document.createElement('option');
            option.value = enterprise.id;
            option.textContent = enterprise.enterpriseName;
            enterpriseSelect.appendChild(option);
        });
    })
    .catch(error => console.error('Ошибка при получении данных для предприятий:', error));

    // ... (остальной код)
});