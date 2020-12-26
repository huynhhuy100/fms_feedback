package vn.com.r2s.fms.Language;

public interface Language {
    String Add = "Add success!";
    String delete = "Delete success!";
    String update = "Update success!";
    String className = "Please enter class name and less than 255 character";
    String capacity = "Please enter capicity is interger and larger than 0";
    String date = "Please choose date or fill full mm/dd/yyyy ";
    String dateLarge = "Please choose date after now date";

    //Login
    String ValUserLogin = "Username must have at least 1 character!";
    String ValUserLogin2 = "Username can't be empty!";
    String ValPassLogin = "Password can't be empty!";
    String ValPassLogin2 = "Password must have at least 1 character!";
    String Vallogin2 = "Password must have no blank space !!";
    String admin = "Admin";
    String trainee = "Trainee";
    String trainer ="Trainer";
    String logout = "Logout Success";

    String AddFailed = "Add fail!";
    String deleteFailed = "Delete fail!";
    String updateFailed = "Update fail!";
    String addClass = "Add Class";
    
     //Enrollment
    String notOverTime = "Class is not over. Do you want to remove the trainee from the classroom?";
    String confirmDel= "Do you want to delete this item?";
    String delEnrollment= "Delete Enrollment success!";
    String UpdateFail="Update fail!";
    String UpdateEnFail="Trainee already exists in the class";

    String AddAsmFail = "Assignment already exist";

    String ModuleName = "Please enter Module name and less than 255 character";
}
