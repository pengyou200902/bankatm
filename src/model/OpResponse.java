/**
 * @author You Peng
 * @date 12/8/2021 5:21 PM
 */


package model;

public class OpResponse {
    public int code;
    public String response;

    public OpResponse(int code) {
        this.code = code;
    }

    public OpResponse(int code, String response) {
        this.code = code;
        this.response = response;
    }
}
