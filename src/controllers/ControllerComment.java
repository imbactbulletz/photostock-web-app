package controllers;

import entities.Comment;
import services.IServiceComment;
import services.ServiceComment;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/comment")
public class ControllerComment {
    private IServiceComment serviceComment;

    public ControllerComment(){
        this.serviceComment = new ServiceComment();
    }

    @POST
    @Path(("/insert"))
    @Consumes("application/json")
    @Produces("application/json")
    public boolean insertComment(Comment comment){
        return this.serviceComment.insertComment(comment.getPosted_by(), comment.getPosted_to(), comment.getContent());
    }
}
