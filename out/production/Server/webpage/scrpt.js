
var nameText = document.getElementById("uname");
var messageText = document.getElementById("message");
var holder = document.getElementById("msgholder");

function sendMessage(){
	
	var dname = nameText.value;
	var dmessage = messageText.value;
	
	var data = {
		name: dname,
		message: dmessage
	};
	
	sendToServer(data)
	
}

function sendToServer(data){
	socket.send(JSON.stringify(data));
	console.log("отправлено")
}


// Создаем новое подключение WebSocket
let socket = new WebSocket("ws://localhost:1234");

// Обработчик открытия соединения
socket.onopen = function(e) {
  console.log("[open] Соединение установлено");
  console.log("Отправляем данные на сервер");
};

// Обработчик входящих сообщений
socket.onmessage = function(event) {
  //console.log(`[message] Данные получены с сервера: ${event.data}`);
  var data = JSON.parse(event.data);
  var res = data["name"]+": "+data["message"];
  
  var msg = document.createElement("div");
  msg.innerHTML = res;
  msgholder.appendChild(msg);
  
  
};

// Обработчик ошибок
socket.onerror = function(error) {
  console.log(`[error] ${error.message}`);
};
