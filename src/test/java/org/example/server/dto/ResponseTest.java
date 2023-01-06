package org.example.server.dto;

import org.example.server.http.Authorization;
import org.example.server.http.ContentType;
import org.example.server.http.StatusCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseTest {

    @Test
    void testSetStatusCode() {
        // Arrange
        Response response = new Response();

        //Act
        response.setStatusCode(StatusCode.OK);

        // Assert
        assertEquals(StatusCode.OK.code, response.getStatus());
        assertEquals(StatusCode.OK.message, response.getMessage());

    }

    @Test
    void testSetContent() {
        // Arrange
        Response response = new Response();

        //Act
        response.setContent("test content");

        // Assert
        assertEquals("test content", response.getContent());

    }

    @Test
    void testSetContentType() {
        // Arrange
        Response response = new Response();

        //Act
        response.setContentType(ContentType.TEXT_PLAIN);

        // Assert
        assertEquals(ContentType.TEXT_PLAIN.contentType, response.getContentType());

    }

    @Test
    void testSetAuthorization() {
        // Arrange
        Response response = new Response();

        //Act
        response.setAuthorization(Authorization.BASIC);

        // Assert
        assertEquals(Authorization.BASIC.authorization, response.getAuthorization());

    }


}