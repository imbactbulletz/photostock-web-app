package dao;

public interface IDAOComment {
    boolean insertComment(String fromUser, String toUser, String content);
}
