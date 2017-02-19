package com.idrawing.filemanager.api;


import java.io.IOException;
import java.nio.file.Path;

public interface FileManager extends FileCRUD, DirectoryCRUD, FileSearcher{

    Iterable<Path> getDiscsList();

    boolean rename(Path oldPath, Path newPath) throws IOException;

}
