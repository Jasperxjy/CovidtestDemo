    // 获取注册和登录表单元素
	const registerForm = document.querySelector('#register-form');
	const loginForm = document.querySelector('#login-form');

	// 获取用于显示结果的元素
	const registerResult = document.querySelector('#register-result');
	const loginResult = document.querySelector('#login-result');
	
	// 用于存储已注册用户的信息
	let users = [];

	// 注册表单提交事件处理函数
	registerForm.addEventListener('submit', function (event) {
  	event.preventDefault(); // 阻止表单默认提交行为
	
	  // 获取表单数据
	  const username = registerForm.elements['username'].value.trim();
	  const id = registerForm.elements['id'].value.trim();
	  const phonenum = registerForm.elements['phonenum'].value.trim();
	  const password = registerForm.elements['password'].value.trim();
	  const confirmPassword = registerForm.elements['confirm-password'].value.trim();

	  // 检查两次密码输入是否一致
	  if (password !== confirmPassword) {
	    registerResult.textContent = 'Error: Passwords do not match';
	    return;
	  }

	  // 检查该ID是否已经被注册
	  if (users.find(user => user.id === id)) {
	    registerResult.textContent = 'Error: ID already exists';
 	   return;
 	 }

	  // 保存用户信息
	  const user = { username, id, phonenum, password };
	  users.push(user);
	
	  // 清空表单数据
	  registerForm.reset();
	
	  // 显示注册成功消息
	  registerResult.textContent = 'Registration successful';
	});
	
	// 登录表单提交事件处理函数
	loginForm.addEventListener('submit', function (event) {
	  event.preventDefault(); // 阻止表单默认提交行为
	
	  // 获取表单数据
	  const id = loginForm.elements['login-id'].value.trim();
	  const password = loginForm.elements['login-password'].value.trim();
	
	  // 查找用户
	  const user = users.find(user => user.id === id && user.password === password);
	
	  // 根据查找结果显示消息	
	  if (user) {
	    loginResult.textContent = 'Login successful';
	  } else {
	    loginResult.textContent = 'Error: Invalid ID or password';
	  }
	
	  // 清空表单数据
	  loginForm.reset();
	});