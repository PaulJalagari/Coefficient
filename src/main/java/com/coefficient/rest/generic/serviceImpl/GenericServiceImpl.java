package com.coefficient.rest.generic.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coefficient.rest.generic.dao.GenericDao;
import com.coefficient.rest.generic.service.GenericService;

@Service("genericService")
public class GenericServiceImpl<E, I> implements GenericService<E, I> {

	protected GenericDao<E, I> genericDao;

	public GenericServiceImpl() {

	}

	public GenericServiceImpl(GenericDao<E, I> genericDao) {
		super();
		this.genericDao = genericDao;
	}

	@Override
	@Transactional(readOnly = true)
	public E find(I id) {
		return genericDao.find(id);
	}

	@Override
	@Transactional
	public E save(E e) {
		return genericDao.create(e);
	}

	@Override
	@Transactional
	public void saveOrUpdate(E e) {
		genericDao.saveOrUpdate(e);
	}

	@Override
	@Transactional
	public void delete(E e) {
		genericDao.delete(e);
	}

	@Override
	@Transactional(readOnly = true)
	public List<E> findAll() {
		return genericDao.findAll();
	}

	@Override
	@Transactional
	public void update(E e) {
		genericDao.update(e);
	}

	@Override
	@Transactional
	public void flush() {
		genericDao.flush();
	}

}
