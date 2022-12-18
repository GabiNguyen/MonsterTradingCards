package org.example.server.http;

public enum StatusCode {
    OK(200, "OK"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    CREATED(201, "Created");

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code;

    public String message;
}
