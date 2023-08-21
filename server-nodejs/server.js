const app = require('express')();
const http = require('http').Server(app);
const io = require('socket.io')(http);

io.on('connection', function(socket){
	console.log('Connected Success');

  socket.on('user_login',(data) => {
    socket.emit('message_login_success', `${data} tham gia chat thành công`);
  })

	socket.on('message_send', (msg) => {
		io.sockets.emit('message_receiver',`${msg}`);
		console.log(`${msg}`)
	})

	socket.on('disconnect', (msg) => {
		console.log('Disconnect');
	})
});

http.listen(3000, () => {
	console.log('Listening to port 3000');
})
