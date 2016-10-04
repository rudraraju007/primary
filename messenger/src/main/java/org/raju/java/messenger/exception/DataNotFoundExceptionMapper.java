package org.raju.java.messenger.exception;
// this is mapper class
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.raju.java.messenger.model.ErrorMessage;


public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {

	@Override
	public Response toResponse(DataNotFoundException exception) {
		// TODO Auto-generated method stub
		ErrorMessage error=new ErrorMessage("DataNotFoundExpeption",404,"http://stackoverflow.com/questions/2378258/what-is-the-auto-alignment-shortcut-key-in-eclipse");
		return Response.status(Status.CREATED)
				               .entity(error)
				               .build();
	}
}
