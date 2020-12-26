package vn.com.r2s.fms.model;

public class Classic {
    private int classID;
    private String className;
    private int capadity;
    private String startTime;
    private String endTime;
    private boolean isDeleted;

    public Classic(int classID, String className, int capadity, String startTime, String endTime, boolean isDeleted) {
        this.classID = classID;
        this.className = className;
        this.capadity = capadity;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isDeleted = isDeleted;
    }

    public Classic(String className, int capadity, String startTime, String endTime) {
        this.className = className;
        this.capadity = capadity;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Classic() {

    }


    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getCapadity() {
        return capadity;
    }

    public void setCapadity(int capadity) {
        this.capadity = capadity;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
}
