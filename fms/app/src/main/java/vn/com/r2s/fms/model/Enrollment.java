package vn.com.r2s.fms.model;

import java.util.Comparator;

public class Enrollment {
    String className;
    String traineeName;
    EnrollmentID enrollmentID;


    public Enrollment() {
    }

    public Enrollment(String className, String traineeName, EnrollmentID enrollmentID) {
        this.className = className;
        this.traineeName = traineeName;
        this.enrollmentID = enrollmentID;
    }
    public Enrollment( EnrollmentID enrollmentID) {

        this.enrollmentID = enrollmentID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTraineeName() {
        return traineeName;
    }

    public void setTraineeName(String traineeName) {
        this.traineeName = traineeName;
    }

    public EnrollmentID getEnrollmentID() {
        return enrollmentID;
    }

    public void setEnrollmentID(EnrollmentID enrollmentID) {
        this.enrollmentID = enrollmentID;
    }
    
     //Sort by ClassId
    public static Comparator<Enrollment> idAscending = new Comparator<Enrollment>()
    {
        @Override
        public int compare(Enrollment en1, Enrollment en2)
        {
            int id1 = Integer.valueOf(en1.getEnrollmentID().getClassId());
            int id2 = Integer.valueOf(en2.getEnrollmentID().getClassId());

            return Integer.compare(id1, id2);
        }
    };


}

