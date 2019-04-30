package com.tfjy.springaop.service;

import com.tfjy.springaop.annotation.Log;
import com.tfjy.springaop.bean.User;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements IUserService {

	@Override
	@Log(name="您访问了保存用户信息")
	public void save(User user) {
		System.out.println(user.getName());
	}
	
}
