package org.raju.java.messenger.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement

public class Message {
	// this is a message
	private long id;
	private String message;
	private Date created;
	private String author;
	List<link> list=new ArrayList<>();
	
//	public List<link> getList() {
//		return list;
//	}
//
//	public void setList(List<link> list) {
//		this.list = list;
//	}

	public Message(){
		
	}
	
	public Message(long id, String message, String author){
		this.id = id;
		this.message = message;
		this.author = author;
		this.created = new Date();
	}
	
	public String message(){
		return message;
	}
	public void setMessage(String message){
		this.message= message;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public void getLink(String uri, String link){
		link lister=new link();
		lister.setLink(uri);
		lister.setRel(link);
		list.add(lister);
	}
	

}
