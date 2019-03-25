package com.kumar.resource;

import java.util.ArrayList;
import java.util.List;

import com.kumar.model.Writer;

public class WriterResource {
	public List<Writer> getAllWriters(){
		Writer w1=new Writer(1001,"The Merchant of Venice","William Shakespeare");
		Writer w2=new Writer(1002,"The Merchant of Venice","William Shakespeare");
		Writer w3=new Writer(1003,"The Merchant of Venice","William Shakespeare");
		List<Writer> listWriter= new ArrayList();
		listWriter.add(w1);
		listWriter.add(w2);
		listWriter.add(w3);
		return listWriter;
	}

}
