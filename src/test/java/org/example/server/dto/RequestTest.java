package org.example.server.dto;

import org.example.server.http.ContentType;
import org.example.server.http.Method;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestTest {
    @Test
    void testSetPath() {
        // Arrange
        Request request = new Request();

        //Act
        request.setPath("/users");

        // Assert
        assertEquals("/users", request.getPath());

    }

    @Test
    void testSetMethod() {
        // Arrange
        Request request = new Request();

        //Act
        request.setMethod(Method.POST.method);

        // Assert
        assertEquals(Method.POST.method, request.getMethod());

    }

    @Test
    void testSetContentType() {
        // Arrange
        Request request = new Request();

        //Act
        request.setContentType(ContentType.APPLICATION_JSON.contentType);

        // Assert
        assertEquals(ContentType.APPLICATION_JSON.contentType, request.getContentType());

    }
}
