package com.klezovich.springjsoncoaching;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileReaderTest {

    @Test
    void testCanReadFile() {
        assertEquals("test_string", FileReader.get("file-reader/test.txt"));
    }
}