package vn.com.r2s.fms.model;

public class TypeFeedback {
    private int typeID;
    private String typeName;
    private boolean IsDeleted;

    public TypeFeedback(){

    }

    public TypeFeedback(int typeId, String typEName, boolean isDeleted) {
        typeID = typeId;
        typeName = typEName;
        IsDeleted = isDeleted;
    }

    public TypeFeedback(String typEName, boolean isDeleted) {
        typeName = typEName;
        IsDeleted = isDeleted;
    }

    public int getTypeId() {
        return typeID;
    }

    public void setTypeId(int typeId) {
        typeID = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typEName) {
        typeName = typEName;
    }

    public boolean getIsDeleted() {
        return IsDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        IsDeleted = isDeleted;
    }
}
