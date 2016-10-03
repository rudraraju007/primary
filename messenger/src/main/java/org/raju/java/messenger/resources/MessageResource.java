package org.raju.java.messenger.resources;

import java.awt.PageAttributes.MediaType;
import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.server.Uri;
import org.raju.java.messenger.exception.DataNotFoundException;
import org.raju.java.messenger.model.ErrorMessage;
import org.raju.java.messenger.model.Message;
import org.raju.java.messenger.service.MessageService;
// this is a message resource

@Path("/messages")
public class MessageResource {

	MessageService messageService = new MessageService();

	@GET
	@Produces("application/json")
	public List<Message> getMessages(@QueryParam("year") int year,
			@QueryParam("start")int start,
			@QueryParam("size")int size){
		if(year>0){
			return messageService.getAllMessagesForYear(year);
		}
		if(start>0 && size>0){
			return messageService.getAllMessagesPaginated(start, size);
		}
		return messageService.getAllMessages();
	}
	
	@GET
	@Path("/context")
	@Produces("application/json")
	public String details(@Context UriInfo uriInfo, @Context HttpHeaders headers){
		String path=uriInfo.getAbsolutePath().toString();
		String val= headers.getCookies().toString();
		return path + headers;
		
	}

	@PUT
	@Path("/{messageId}")
	@Consumes("application/json")
	@Produces("application/json")
	public Message updateMessage(@PathParam("messageId") long id, Message message) {
		message.setId(id);
		return messageService.updateMessage(message);
	}

	@DELETE
	@Path("/{messageId}")
	@Produces("application/json")
	public void deleteMessage(@PathParam("messageId") long id) {
		messageService.removeMessage(id);
	}
	 
	@Path("/{messageId}/comments")
	public CommentResource getcommments(){
		return new CommentResource();
	}
	
	@POST
	@Produces("application/json")
	@Consumes("application/json")
	public Response addMessage(Message message, @Context UriInfo uriInfo){
		Message val= messageService.addMessage(message);
		String newId= String.valueOf(val.getId());
		URI uri= uriInfo.getAbsolutePathBuilder().path(newId).build();
		return Response.created(uri)
				            .entity(val)
				            .build();
	}

	@GET
	@Path("/{messageId}")
	@Produces("application/json")
	public Message getMessage(@PathParam("messageId") long id, @Context UriInfo uriInfo) {
		
		Message message= messageService.getMessage(id);
//		ErrorMessage error=new ErrorMessage("not found",304,"http://indianroommates.sulekha.com/offered_male_roommates_in-and-near_philadelphia-pa");
//		Response response= Response.status(Status.CREATED)
//				                    .entity(error)
//				                    .build();
//		if(message==null){
//			throw new WebApplicationException(response);
//		}
		String uri= uriInfo.getBaseUriBuilder().path(MessageResource.class)
		                                .path(Long.toString(message.getId()))
		                                .build().toString();
		message.getLink(uri, "self");
		message.getLink(getUriInfoForProfile(uriInfo, message),"profile");
		return message;
	}

	private String getUriInfoForProfile( UriInfo uriInfo, Message message) {
		String uri= uriInfo.getBaseUriBuilder()
				        .path(ProfileResource.class)
				        .path(message.getAuthor())
				        .build().toString();
		return uri;
	}
}
