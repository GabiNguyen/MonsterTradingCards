package org.example.server.http;

public enum Authorization {
    // basic for credentials
    AUTHORIZATION("Basic");

    Authorization(String authorization) {
        this.authorization = authorization;
    }

    public String authorization;
}
