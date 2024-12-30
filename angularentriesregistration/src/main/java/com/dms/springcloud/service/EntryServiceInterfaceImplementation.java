package com.dms.springcloud.service;

import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.dms.springcloud.entity.Entry;

import com.dms.springcloud.repository.EntryRepositoryInterface;

@Service
public class EntryServiceInterfaceImplementation implements EntryServiceInterface {

	@Autowired
	private EntryRepositoryInterface entryRepository;

	@Override
	public Entry saveEntry(Entry entry) {
		return entryRepository.save(entry);
	}

	@Override
	public Entry updateEntry(Entry entry) {
		return entryRepository.save(entry);
	}

	@Override
	public void deleteEntry(Entry entry) {
		entryRepository.delete(entry);
	}

	@Override
	public Entry findById(long id) {
		return entryRepository.findById(id).get();
	}

	@Override
	public List<Entry> findAll() {
		return entryRepository.findAll();
	}

	@Override
	public List<Entry> findByUserid(long id) {
		
		return entryRepository.findByUserid(id);
	}

}
