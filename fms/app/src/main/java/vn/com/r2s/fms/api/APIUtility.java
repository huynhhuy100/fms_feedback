package vn.com.r2s.fms.api;

public class APIUtility {
    private static String baseURL="http://10.82.169.12:3000/";
//    private static String baseURL="http://192.168.0.108:3000/";

    public static UserService getUserService(){
        return APIClient.getClient(baseURL).create(UserService.class);
    }
    public static ClassicService getClassicService(){
        return APIClient.getClient(baseURL).create(ClassicService.class);
    }
    public static LoginService getLoginService(){
        return  APIClient.getClient(baseURL).create(LoginService.class);
    }
    public static FeedbackService getFeedbackService(){
        return APIClient.getClient(baseURL).create(FeedbackService.class);
    }
    public static TypeFeedbackService getTypeFeedbackService(){
        return APIClient.getClient(baseURL).create(TypeFeedbackService.class);
    }

    public static EnrollmentService getEnService(){
        return APIClient.getClient(baseURL).create(EnrollmentService.class);
    }
    public static ModuleService getModuleService(){
        return APIClient.getClient(baseURL).create(ModuleService.class);
    }
    //     xem lai
    public static QuestionsService questionsService(){
        return APIClient.getClient(baseURL).create(QuestionsService.class);
    }
    public static TopicService getTopicService(){
        return APIClient.getClient(baseURL).create(TopicService.class);
    }

    public static AssignmentService getAssignmentService(){
        return APIClient.getClient(baseURL).create(AssignmentService.class);
    }

    public static TraineeService getTraineeService(){
        return APIClient.getClient(baseURL).create(TraineeService.class);
    }
    public static AnswerService getAnswerService(){
        return APIClient.getClient(baseURL).create(AnswerService.class);
    }
    public static CommentService getCommentService(){
        return APIClient.getClient(baseURL).create(CommentService.class);
    }
    public static JoinService gettraineeAllAssignment(){
        return APIClient.getClient(baseURL).create(JoinService.class);
    }
}
