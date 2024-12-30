package com.dms.springcloud.dto;

import java.util.List;

import com.dms.springcloud.entity.User;

public class UserLoginWithEntriesDto {
       User user;
       List<Entry> entrieslist;
	public User getUser() {
		return user;
	}
	public UserLoginWithEntriesDto() {
		// TODO Auto-generated constructor stub
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Entry> getEntrieslist() {
		return entrieslist;
	}
	public void setEntrieslist(List<Entry> entrieslist) {
		this.entrieslist = entrieslist;
	}
}
