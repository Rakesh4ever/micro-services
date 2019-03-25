package com.kumar.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Writer {
	
	private int writerId;
	private String writerName;
	private String book;
	
	
	public Writer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Writer(int writerId, String writerName, String book) {
		super();
		this.writerId = writerId;
		this.writerName = writerName;
		this.book = book;
	}


	public int getWriterId() {
		return writerId;
	}
	public void setWriterId(int writerId) {
		this.writerId = writerId;
	}
	public String getWriterName() {
		return writerName;
	}
	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}
	public String getBook() {
		return book;
	}
	public void setBook(String book) {
		this.book = book;
	}
	

}
