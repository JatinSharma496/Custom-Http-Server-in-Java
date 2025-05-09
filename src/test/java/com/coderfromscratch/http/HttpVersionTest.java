package com.coderfromscratch.http;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HttpVersionTest {
    @Test
    void getBestCompatibleVersionExactMethod(){
        HttpVersion version= null;
        try {
            version = HttpVersion.getBestCompatibleVersion("HTTP/1.1");
        } catch (BadHttpVersionException e) {
            fail();
        }

        assertNotNull(version);
        assertEquals(version,HttpVersion.HTTP_1_1);
    }

    @Test
    void getBadCompatibleVersionBadFormat(){
        HttpVersion version= null;
        try {
            version = HttpVersion.getBestCompatibleVersion("http/1.1");
            fail();
        } catch (BadHttpVersionException e) {

        }
    }

    @Test
    void getBadCompatibleVersionHigherVersion(){
        HttpVersion version= null;
        try {
            version = HttpVersion.getBestCompatibleVersion("HTTP/1.2");
            assertNotNull(version);
            assertEquals(version,HttpVersion.HTTP_1_1);

        } catch (BadHttpVersionException e) {
            fail();
        }

    }
}
