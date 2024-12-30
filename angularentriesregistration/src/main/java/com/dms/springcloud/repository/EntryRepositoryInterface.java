package com.dms.springcloud.repository;

import java.util.List;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dms.springcloud.entity.Entry;

public interface EntryRepositoryInterface extends JpaRepository<Entry, Long> {
	
	@Query(value="select * from entries where userid=:id",nativeQuery = true)
	public List<Entry> findByUserid(long id);

}
