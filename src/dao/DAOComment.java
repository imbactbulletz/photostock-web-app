package dao;

import entities.Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class DAOComment extends DAOAbstractDatabase<Comment> implements IDAOComment {

    //queries
    private static final String INSERT_COMMENT = "INSERT INTO COMMENT (POSTED_BY, POSTED_TO, CONTENT) VALUES ('%s','%s','%s')";

    public DAOComment(){
        super(Comment.class);
    }

    @Override
    public boolean insertComment(String fromUser, String toUser, String content) {
        Connection connection = createConnection();

        if(connection == null || fromUser == null || content == null)
            return false;

        String query = String.format(INSERT_COMMENT, fromUser , toUser, content);

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            int result = preparedStatement.executeUpdate();

            if(result == 0)
                return false;

            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
