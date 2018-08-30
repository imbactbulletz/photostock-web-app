package entities;

import utils.SafeConverter;

public class Comment extends BasicEntity {
    // column values
    String posted_by;
    String posted_to;
    String content;

    // column names
    String POSTED_BY = "POSTED_BY";
    String POSTED_TO = "POSTED_TO";
    String CONTENT = "CONTENT";

    public Comment(){
        super();

        this.columnNames.add(POSTED_BY);
        this.columnNames.add(POSTED_TO);
        this.columnNames.add(CONTENT);
    }

    @Override
    public Object getValueForColumnName(String columnName) {
        if(POSTED_BY.equals(columnName))
            return this.posted_by;

        if(POSTED_TO.equals(columnName))
            return this.posted_to;

        if(CONTENT.equals(columnName))
            return this.content;
        return super.getValueForColumnName(columnName);
    }

    @Override
    public void setValueForColumnName(String columnName, Object value) {
        if(POSTED_BY.equals(columnName))
            this.setPosted_by(SafeConverter.toSafeString(value));

        if(POSTED_TO.equals(columnName))
            this.setPosted_to(SafeConverter.toSafeString(value));

        if(CONTENT.equals(columnName))
            this.setContent(SafeConverter.toSafeString(value));

        super.setValueForColumnName(columnName, value);
    }

    public String getPosted_by() {
        return posted_by;
    }

    public void setPosted_by(String posted_by) {
        this.posted_by = posted_by;
    }

    public String getPosted_to() {
        return posted_to;
    }

    public void setPosted_to(String posted_to) {
        this.posted_to = posted_to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
