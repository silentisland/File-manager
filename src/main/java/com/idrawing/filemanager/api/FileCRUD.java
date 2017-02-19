package com.idrawing.filemanager.api;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by Admin on 19.02.2017.
 */
public interface FileCRUD {

    void delete(Path file) throws IOException;

    Path create(Path file) throws IOException;

    Path move(Path from, Path to) throws IOException;

    Path copy(Path from, Path to) throws IOException;
}
