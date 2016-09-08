package shared;

public class ProtocolStrings {
    
    public static enum ARGS{
        STOP,LOGIN,MSG,LOGOUT,CLIENTLIST,MSGRESP;
    }
    
    public static final String STOP = "STOP";
    
    public void getMethod(String q){
        if(q.startsWith("MSG:")){
            message(q);
        }
        if(q.startsWith("LOGIN:")){
            
        }
        if(q.startsWith("LOGOUT:")){
            
        }
        if(q.startsWith("MSGRES:")){
            
        }
        if(q.startsWith("CLIENTLIST:")){
            
        }
    }
    
    public String message(String q){
        return q.substring(4);
    }
    public String login(){
        return "Login Success";
    }
    public String logout(){
        return "Logged out!";
    }
    public String clientList(){
        return "";
    }
    public String msgRes(){
        return "";
    }
    public String sendMessage(ARGS args, String msg){
        if(args.equals(ARGS.MSGRESP)){
            
        }
        return args+":"+msg;
    }
}