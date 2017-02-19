package com.idrawing.filemanager.domain;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Sergej Povzanyuk on 07.08.2016.
 */
public class LocalFileTest {
    private static final Path PATH_TO_TEST_FILE = Paths.get("src/test/java/fixture/metadata/metadata.docx");
    private static LocalFile localFile;

    @Before
    public void init() throws Exception {
        localFile = new LocalFile(PATH_TO_TEST_FILE);
    }

    @Test
    public void getExtension() throws Exception {
        //when
        String extension = localFile.getExtension();

        //then
        assertEquals("docx", extension);
    }

    @Test
    public void getName() throws Exception {
        //when
        String name = localFile.getName();

        //then
        assertEquals("metadata", name);
    }

    @Test
    public void getPath() throws Exception {
        //when
        Path result = localFile.getPath();

        //then
        assertEquals(PATH_TO_TEST_FILE, result);
    }

    @Test
    public void shouldReturnPathInString(){
        //when
        String path = localFile.getPathString();

        //then
        assertEquals(PATH_TO_TEST_FILE.toAbsolutePath().toString(), path);
    }

    @Test
    public void shouldGetCreationDate() throws Exception {
        //when
        LocalDateTime date = localFile.getCreationDate();

        //then
        assertNotNull(date);
    }

    @Test
    public void shouldReturnUpdatedDate() throws Exception {
        //when
        LocalDateTime date = localFile.getLastModifiedDate();

        //then
        assertNotNull(date);
    }

    @Test
    public void getLastAccessDate() throws Exception {
        //when
        LocalDateTime date = localFile.getLastAccessDate();

        //then
        assertNotNull(date);
    }

    @Test
    public void getCreator() throws Exception {
        //when
        String creator = localFile.getCreator();

        //then
        assertEquals("BUILTIN\\Администраторы", creator);
    }

    @Test
    public void getContentType() throws Exception {
        //when
        String contentType = localFile.getContentType();

        //then
        assertEquals("application/vnd.openxmlformats-officedocument.wordprocessingml.document", contentType);

    }

    @Test
    public void shouldGetFileSize() throws Exception {
        //when
        long fileSize = localFile.getFileSizeBytes();

        //then
        assertEquals(14408L, fileSize);
    }

    @Test
    public void shouldShowToString(){
        //when
        String result = localFile.toString();

        //then
        assertNotNull(result);
    }
}