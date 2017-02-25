package com.idrawing.filemanager.api;


import com.google.common.collect.Iterables;
import com.idrawing.filemanager.domain.FileCriteria;
import com.idrawing.filemanager.domain.LocalFile;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by Sergej Povzanyuk on 07.08.2016.
 */
public class LocalFileManagerTest {

    private FileManager fileManager;

    @Before
    public void init() {
        fileManager = new LocalFileManager();
    }

    @Test
    public void shouldGetDiscsList() throws Exception {
        //when
        Iterable<Path> result = fileManager.getDiscsList();

        //then
        assertTrue(result.iterator().hasNext());
    }

    @Test
    public void shouldCopyFileToOtherDirectory() throws Exception {
        //given
        Path from = Paths.get("src\\test\\java\\fixture\\copytest\\from\\test5.cdw");
        Path to = Paths.get("src\\test\\java\\fixture\\copytest\\to\\test5.cdw");

        //when
        Path result = fileManager.copy(from, to);

        //then
        assertTrue(Files.exists(to));
        assertEquals(to, result);

        //cleaning
        Files.delete(to);
    }

    @Test
    public void shouldDeleteFile() throws Exception {
        //given
        Path from = Paths.get("src\\test\\java\\fixture\\copytest\\from\\test5.cdw");
        Path to = Paths.get("src\\test\\java\\fixture\\deletetest\\test5.cdw");
        Path result = fileManager.copy(from, to);

        //when
        assertTrue(Files.exists(to));
        fileManager.delete(result);

        //then
        assertFalse(Files.exists(to));
    }

    @Test
    public void shouldCreateFile() throws Exception {
        //given
        Path file = Paths.get("src\\test\\java\\fixture\\createtest\\test.cdw");

        //when
        Path result = fileManager.create(file);

        //then
        assertTrue(Files.exists(result));

        //cleaning
        fileManager.delete(result);
    }

    @Test
    public void shouldCreateDirectory() throws Exception {
        //given
        Path directory = Paths.get("src\\test\\java\\fixture\\directory\\");

        //when
        Path result = fileManager.createDirectory(directory);

        //then
        assertTrue(Files.isDirectory(result));

        //cleaning
        fileManager.delete(result);
    }

    @Test
    public void shouldDeleteNotEmptyDirectory() throws Exception {
        //given
        Path directory = Paths.get("src\\test\\java\\fixture\\directory\\");
        Path file1 = Paths.get("src\\test\\java\\fixture\\directory\\text1.txt");
        Path file2 = Paths.get("src\\test\\java\\fixture\\directory\\text2.txt");
        Path result = fileManager.createDirectory(directory);
        Path filepath1 = fileManager.create(file1);
        Path filepath2 = fileManager.create(file2);
        assertTrue(Files.exists(filepath1));
        assertTrue(Files.exists(filepath2));

        //when
        fileManager.deleteDirectory(result);

        //then
        assertFalse(Files.exists(directory));
    }

    @Test
    public void shouldMoveFileToOtherPath() throws Exception {
        //given
        Path file = Paths.get("src\\test\\java\\fixture\\movetest\\from\\text1.txt");
        Path from = Paths.get("src\\test\\java\\fixture\\movetest\\from\\text1.txt");
        Path to = Paths.get("src\\test\\java\\fixture\\movetest\\to\\text1.txt");
        fileManager.create(file);
        assertTrue(Files.exists(file));

        //when
        Path result = fileManager.move(from, to);

        //then
        assertFalse(Files.exists(file));
        assertTrue(Files.exists(result));

        //cleaning
        fileManager.delete(result);
    }

    @Test
    public void shouldMoveDirectoryToOtherPath() throws Exception {
        //given
        Path newDirectory = Paths.get("src\\test\\java\\fixture\\movetest\\from\\dir\\");
        Path file1 = Paths.get("src\\test\\java\\fixture\\movetest\\from\\dir\\text1.txt");
        Path file2 = Paths.get("src\\test\\java\\fixture\\movetest\\from\\dir\\text2.txt");
        Path from = Paths.get("src\\test\\java\\fixture\\movetest\\from\\");
        Path to = Paths.get("src\\test\\java\\fixture\\movetest\\to\\");
        fileManager.createDirectory(newDirectory);
        fileManager.create(file1);
        fileManager.create(file2);
        assertTrue(Files.exists(file1));
        assertTrue(Files.exists(file2));

        //when
        fileManager.moveDirectory(from, to);

        //then
        assertFalse(Files.exists(file1));
        assertFalse(Files.exists(file2));
        assertFalse(Files.exists(newDirectory));
        assertTrue(Files.exists(from));
        assertTrue(Files.exists(to));

        //cleaning
        fileManager.cleanDirectory(to);
    }

    @Test
    public void shouldCleanDirectory() throws Exception {
        //given
        Path dirtyDirectory = Paths.get("src\\test\\java\\fixture\\cleantest");
        Path newDirectory = Paths.get("src\\test\\java\\fixture\\cleantest\\dir\\");
        Path file1 = Paths.get("src\\test\\java\\fixture\\cleantest\\dir\\text1.txt");
        Path file2 = Paths.get("src\\test\\java\\fixture\\cleantest\\dir\\text2.txt");
        fileManager.createDirectory(newDirectory);
        fileManager.create(file1);
        fileManager.create(file2);
        assertTrue(Files.exists(file1));
        assertTrue(Files.exists(file2));

        //when
        fileManager.cleanDirectory(dirtyDirectory);

        //then
        assertFalse(Files.exists(newDirectory));
    }

    @Test
    public void shouldFindAllFilesByExtensionOnselectDisc() throws Exception {
        //when
        Collection<LocalFile> result = fileManager.findFilesByExtension(Paths.get("src/test/java/fixture/searchtest/"), "test");

        //then
        assertTrue(result.size() == 1);
    }

    @Test
    public void shouldFindAllFilesByMenyExtensionsOnselectDisc() throws Exception {
        //when
        Collection<LocalFile> result = fileManager.findFilesByExtension(Paths.get("src/test/java/fixture/searchtest/"), "test", "tester", "testix");

        //then
        assertTrue(result.size() == 3);
    }

    @Test
    public void shouldGetAllFilesByExtension() throws Exception {
        //when
        Collection<LocalFile> result = fileManager.findAllFilesByExtension("test");

        //then
        assertTrue(result.size() == 1);
    }

    @Test
    public void shouldGetAllFilesByManyExtension() throws Exception {
        //when
        Collection<LocalFile> result = fileManager.findAllFilesByExtension("test", "tester", "testix");

        //then
        assertTrue(result.size() == 3);
    }

    @Test
    public void shouldFindFilesByExtensionInPathSet() throws Exception {
        //given
        Collection<LocalFile> result1 = fileManager.findAllFilesByExtension("test");

        //when
        Collection<LocalFile> result2 = fileManager.findFilesByExtensionPathSet(fileManager.getDiscsList(), "test");

        //then
        assertEquals(result1.size(), result2.size());
    }

    @Test
    public void shouldRenameFile() throws Exception {
        //given
        Path file = Paths.get("src\\test\\java\\fixture\\renametest\\text1.txt");
        Path target = Paths.get("src\\test\\java\\fixture\\renametest\\text2.txt");
        fileManager.create(file);
        assertTrue(Files.exists(file));

        //when
        boolean isRenamed = fileManager.rename(file, target);

        //then
        assertTrue(isRenamed);
        assertFalse(Files.exists(file));
        assertTrue(Files.exists(target));

        //cleaning
        fileManager.delete(target);
    }

    @Test
    public void shouldRenameDirectory() throws Exception {
        //given
        Path newDirectory = Paths.get("src\\test\\java\\fixture\\renametest\\dir\\");
        Path target = Paths.get("src\\test\\java\\fixture\\renametest\\dir2\\");
        Path result = Paths.get("src\\test\\java\\fixture\\renametest\\dir2\\text1.txt");
        Path file = Paths.get("src\\test\\java\\fixture\\renametest\\dir\\text1.txt");
        fileManager.createDirectory(newDirectory);
        fileManager.create(file);
        assertTrue(Files.exists(file));

        //when
        boolean isRenamed = fileManager.rename(newDirectory, target);

        //then
        assertTrue(isRenamed);
        assertTrue(Files.exists(result));
        assertFalse(Files.exists(file));

        //cleaning
        fileManager.deleteDirectory(target);
    }

    @Test
    public void shouldFindFilesBetweenTwoDates() throws Exception {
        //given
        LocalDateTime from = LocalDateTime.of(2016, 6, 30, 12, 0);
        LocalDateTime to = LocalDateTime.now();

        //when
        Iterable<LocalFile> result = fileManager.findAllFilesBetweenTwoDates(from, to, "testix");

        //then
        assertTrue(Iterables.size(result) == 1);
    }

    @Test
    public void shouldCompareTwoFilesContent() throws IOException {
        //given
        Path file1 = Paths.get("src\\test\\java\\fixture\\comparetest\\drawing1.cdw");
        Path file2 = Paths.get("src\\test\\java\\fixture\\comparetest\\drawing2.cdw");

        //when
        boolean isSameFile = fileManager.isSameFile(file1, file2);

        //then
        assertTrue(isSameFile);
    }

    @Test
    public void shouldSearchFilesByCriteria() throws IOException {
        //given
        FileCriteria criteria = new FileCriteria();
        criteria.setRegExp("\\.*cdw");

        //when
        Collection<LocalFile> result = fileManager.findByCriteria(criteria);

        System.out.println(result.size());


    }
}