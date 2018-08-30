package services;

public interface IServiceComment {
    boolean insertComment(String fromUser, String toUser, String content);
}
