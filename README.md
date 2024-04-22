https://googlechromelabs.github.io/chrome-for-testing/
Для работы автотеста необходимо 
Chrome for testing (папку "chrome-win64"из архива переложить в корневую папку проекта)
https://storage.googleapis.com/chrome-for-testing-public/123.0.6312.122/win64/chrome-win64.zip
ChromeDriver ("chromedriver.exe"переложить в корневую папку проекта)
https://storage.googleapis.com/chrome-for-testing-public/123.0.6312.122/win64/chromedriver-win64.zip

email@ejao.ru
ejao
ejao

http://localhost:5000/ 
(1) При ширине экрана до 1090px пропадают кнопки Navigation Bar 

http://localhost:5000/signup
(1) Форма регистрации

(1.1) При заполнении поля Email
Нет проверки доменной зоны включая разделитель "." (Введенный email: email@example) Форма пропускает такой email и не реагирует на него
В тесте отсутствует проверка такого вида Email, по причине отсутствия validationMessage

(1.2) Ни одно поле не является обязательным
(1.3) Форма пропускает пустые поля 
(1.4) Кнопка Submit всегда активна

(2) Нет проверки заданного пароля (снижается вероятность успешной регистрации)
(2.1) Нет критеря по количеству симовлов от-до 
(2.2) Нет проверки на русские буквы, в общем принимает любую комбинацию пароля, которую потом пользователь может не ввести на странице авторизации
