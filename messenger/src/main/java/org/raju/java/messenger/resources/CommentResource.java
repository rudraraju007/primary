package org.raju.java.messenger.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
// this is a comment resource
@Path("/")
public class CommentResource {

	@GET
	public String getcomments(){
		return "tested";
	}
}
