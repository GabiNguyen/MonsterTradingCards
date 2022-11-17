package org.example.server.dto;

import org.example.server.http.StatusCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseTest {

    @Test
    void testSetStatusCode() {
        // Arrange
        org.example.server.dto.Response response = new org.example.server.dto.Response();

        //Act
        response.setStatusCode(StatusCode.OK);

        // Assert
        assertEquals(StatusCode.OK.code, response.getStatus());
        assertEquals(StatusCode.OK.message, response.getMessage());

    }
}