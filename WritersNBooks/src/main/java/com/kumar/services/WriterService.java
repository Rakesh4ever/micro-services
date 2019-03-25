package com.kumar.services;

import java.util.List;

import javax.annotation.Resources;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.kumar.model.Writer;
import com.kumar.resource.WriterResource;
@Path("/writer")
public class WriterService {
	WriterResource writerResource =new WriterResource();
	
	@GET
	/*@Resources(MediaType.APPLICATION_XML)*/
public List<Writer> getAllWriter(){

	return writerResource.getAllWriters();
}
}
