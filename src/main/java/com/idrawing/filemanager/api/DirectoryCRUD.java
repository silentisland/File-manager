package com.idrawing.filemanager.api;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by Admin on 19.02.2017.
 */
public interface DirectoryCRUD {

    Path createDirectory(Path directory) throws IOException;

    Path deleteDirectory(Path directory) throws IOException;

    Path cleanDirectory(Path directory) throws IOException;

    void moveDirectory(Path from, Path to) throws IOException;

}
