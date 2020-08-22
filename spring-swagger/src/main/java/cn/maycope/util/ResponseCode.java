package cn.maycope.util;

import java.io.Serializable;

/**
 * @Author Maycope
 * @Date 2020/8/21
 * @Version 1.0
 */
public class ResponseCode implements Serializable {

    private int  code;
    private String message;
    private Object date;
    /**
     * @param code
     * @param message
     * @param date
     */
    public ResponseCode(int code, String message, Object date) {
        this.code = code;
        this.message = message;
        this.date = date;
    }
    public ResponseCode() {
    }
    /**
     * 完成基础的之后还是要完成两个具体的方法。
     * @return
     */

    public void setResultCode(ResultCode code) {
        this.code = code.code();
        this.message = code.message();
    }

    /**
     * 表示直接返回成功信息 但是不返回数据。
     * @return
     */
    public static  ResponseCode success(){
        ResponseCode responseCode = new ResponseCode();
        responseCode.setResultCode(ResultCode.SUCCESS);
        return  responseCode;
    }
    public static  ResponseCode failuer(){
        ResponseCode responseCode = new ResponseCode();
        responseCode.setResultCode(ResultCode.FAIL);
        return  responseCode;
    }

    /**
     * 成功 但是同时也返回对应的数据。
     * @param data
     * @return
     */
    public static  ResponseCode success(Object data){
        ResponseCode responseCode = new ResponseCode();
        responseCode.setResultCode(ResultCode.SUCCESS);
        responseCode.setDate(data);
        return  responseCode;
    }

    /**
     * 自定义成功的状态 加上数据。
     * @return
     */
    public  static  ResponseCode success(ResultCode resultCode ,Object data)
    {
        ResponseCode responseCode = new ResponseCode();
        responseCode.setResultCode(resultCode);
        responseCode.setDate(data);
        return  responseCode;
    }


    public  static  ResponseCode failuer(ResultCode resultCode){
        ResponseCode responseCode = new ResponseCode();
        responseCode.setResultCode(resultCode);
        return  responseCode;
    }
    public static  ResponseCode failuer(ResultCode resultCode ,Object data)
    {
        ResponseCode responseCode = new ResponseCode();
        responseCode.setResultCode(resultCode);
        responseCode.setDate(data);
        return  responseCode;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ResponseCode{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", date=" + date +
                '}';
    }
}
