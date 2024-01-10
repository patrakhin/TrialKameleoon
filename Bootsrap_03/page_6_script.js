document.addEventListener('DOMContentLoaded', function () {
    // Получаем токен из localStorage
    const token = localStorage.getItem('token');

    // Если токен отсутствует, перенаправляем пользователя на страницу входа
    if (!token) {
        window.location.href = 'index.html'; 
    }

    // Получаем данные о выбранной машине из localStorage
    const selectedVehicle = JSON.parse(localStorage.getItem('selectedVehicle'));

    // Если данные отсутствуют, перенаправляем пользователя на страницу с машинами
    if (!selectedVehicle) {
        window.location.href = 'page_3.html';
    }

    // Заполняем форму данными о выбранной машине
    document.getElementById('numberVehicle').value = selectedVehicle.numberVehicle;
    document.getElementById('price').value = selectedVehicle.price;
    document.getElementById('yearOfManufacture').value = selectedVehicle.yearOfManufacture;
    document.getElementById('mileage').value = selectedVehicle.mileage;
    document.getElementById('equipmentType').value = selectedVehicle.equipmentType;
    
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

        // Установим значение выбранной марки машины
        carBrandSelect.value = selectedVehicle.carBrandId;
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

        // Добавляем обработчик события изменения предприятия здесь, внутри этого блока
        enterpriseSelect.addEventListener('change', function () {
            const selectedOption = this.options[this.selectedIndex];

            // Сохраняем данные о выбранном предприятии
            selectedEnterpriseId = selectedOption.value;
            selectedEnterpriseAddress = selectedOption.getAttribute('data-address');
            selectedEnterprisePhone = selectedOption.getAttribute('data-phone');
        });
    })
    .catch(error => console.error('Ошибка при получении данных для предприятий:', error));

    // Получаем форму и добавляем обработчик события на отправку формы
    const editVehicleForm = document.getElementById('editVehicleForm');
    editVehicleForm.addEventListener('submit', function (event) {
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
        const editedVehicleData = {
            id: selectedVehicle.id, // Важно включить идентификатор машины для редактирования
            numberVehicle: numberVehicle,
            price: parseFloat(price),
            yearOfManufacture: parseInt(yearOfManufacture),
            mileage: parseInt(mileage),
            equipmentType: equipmentType,
            carBrand: carBrand,
            enterpriseId: parseInt(enterpriseId), // Используйте значение, которое вам нужно
        };

        // Отправляем PATCH-запрос на бэкэнд для редактирования машины
        fetch(`http://localhost:8080/manager1/edit/${selectedVehicle.id}`, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            },
            body: JSON.stringify(editedVehicleData)
        })
        .then(response => response.json())
        .then(data => {
            // Обработка успешного ответа от сервера (если необходимо)
            console.log('Машина успешно отредактирована:', data);

            // Перенаправляем пользователя на страницу, куда нужно после редактирования машины
            window.location.href = 'page_3.html'; // Заменить на фактический путь
        })
        .catch(error => {
            // Обработка ошибки
            console.error('Ошибка при редактировании машины:', error);
        });
    });
});
