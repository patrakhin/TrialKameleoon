// Функция для проверки уникальности номера
function checkNumberUniqueness(number) {
    return fetch('http://localhost:8080/checkNumberUniqueness?number=' + number, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token')
        }
    })
    .then(response => response.json())
    .catch(error => {
        console.error('Error:', error);
        // Обработка ошибки при проверке уникальности
        throw error; // Прокидываем ошибку выше для обработки в вызывающем коде
    });
}

// Обработчик события для поля ввода номера машины
document.addEventListener('DOMContentLoaded', function () {
    var numberInput = document.getElementById('number');
    var numberError = document.getElementById('numberError');
    var numberSuccess = document.getElementById('numberSuccess');

    if (numberInput) {
        numberInput.addEventListener('input', function () {
            var number = this.value.trim();

            // Проверяем формат номера
            if (!/^[A-Z]\d{3}[A-Z]$/.test(number)) {
                numberError.innerText = 'Invalid number format';
                numberSuccess.innerText = ''; // Сбрасываем галочку
            } else {
                // Проверяем, что все буквы в верхнем регистре
                if (number.toUpperCase() !== number) {
                    numberError.innerText = 'Letters should be uppercase';
                    numberSuccess.innerText = ''; // Сбрасываем галочку
                } else {
                    numberError.innerText = ''; // Сбрасываем ошибку формата
                    numberSuccess.innerText = '✔ Number is correct'; // Устанавливаем галочку

                    // Сброс сообщений об ошибке/успехе при вводе
                    numberInput.addEventListener('input', function () {
                        numberError.innerText = '';
                        numberSuccess.innerText = '';
                    });
                }
            }
        });
    }
});