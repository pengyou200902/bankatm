/**
 * @author You Peng
 * @date 12/8/2021 5:21 PM
 */


package model;

public class OpResponse {
    public int code;
    public boolean status;
    public String response;
    public Object data;

    public OpResponse() {

    }

    public OpResponse(int code, boolean status) {
        this.code = code;
        this.response = "No specific response.";
        this.data = null;
    }

    public OpResponse(int code, boolean status, String response) {
        this.code = code;
        this.status = status;
        this.response = response;
        this.data = null;
    }

    public OpResponse(int code, boolean status, String response, Object data) {
        this.code = code;
        this.status = status;
        this.response = response;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
