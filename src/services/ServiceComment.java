package services;

import dao.DAOComment;

public class ServiceComment implements IServiceComment {

    protected DAOComment dao;

    public ServiceComment(){
        this.dao = new DAOComment();
    }

    @Override
    public boolean insertComment(String fromUser, String toUser, String content) {
        return this.dao.insertComment(fromUser, toUser, content);
    }
}
