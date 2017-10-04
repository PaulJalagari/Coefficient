package com.coefficient.rest.generic.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coefficient.rest.user.dao.UserDao;

@Service("userDetailService")
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	UserDao userDao;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) {
		/*
		 * UserDetails user = userDao.loadUserByUsername(email); if(user ==
		 * null){ throw new UsernameNotFoundException("The user " + email +
		 * " was not found"); } return user;
		 */
		return null;
	}

}
