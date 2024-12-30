package com.dms.springcloud.service;

import java.util.List;



import com.dms.springcloud.entity.Entry;

public interface EntryServiceInterface {
	
	public Entry saveEntry(Entry entry);
	public Entry updateEntry(Entry entry);
	public void deleteEntry(Entry entry);
	public Entry findById(long id);
	public List<Entry> findAll();
	public List<Entry> findByUserid(long id);

}
