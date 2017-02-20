package com.idrawing.filemanager.domain;

import lombok.Getter;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.google.common.io.Files.getFileExtension;
import static com.google.common.io.Files.getNameWithoutExtension;

/**
 * Created by Sergej Povzanyuk on 07.08.2016.
 */
@Getter
public class LocalFile {
    private Path path;
    private BasicFileAttributes attributes;

    public LocalFile(Path path) {
        if (!Files.isRegularFile(path))
            throw new IllegalArgumentException("Argument is not a path");
        this.path = path;
        try {
            this.attributes = Files.readAttributes(path, BasicFileAttributes.class);
        } catch (IOException ignored) {}
    }

    public String getName() {
        return getNameWithoutExtension(path.toAbsolutePath().toString().toLowerCase());
    }

    public String getExtension() {
        return getFileExtension(path.toAbsolutePath().toString().toLowerCase());
    }

    public String getPathString() {
        return path.toAbsolutePath().toString();
    }

    public LocalDateTime getCreationDate(){
        if (attributes != null) {
            return LocalDateTime.ofInstant(attributes.creationTime().toInstant(), ZoneId.systemDefault());
        } else {
            try {
                this.attributes = Files.readAttributes(path, BasicFileAttributes.class);
            } catch (IOException e) {
                return LocalDateTime.now();
            }
            Instant instant = attributes.creationTime().toInstant();
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        }
    }

    public LocalDateTime getLastModifiedDate() {
        if (attributes != null) {
            return LocalDateTime.ofInstant(attributes.lastModifiedTime().toInstant(), ZoneId.systemDefault());
        } else {
            try {
                this.attributes = Files.readAttributes(path, BasicFileAttributes.class);
            } catch (IOException e) {
                return LocalDateTime.now();
            }
            Instant instant = attributes.lastModifiedTime().toInstant();
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        }
    }

    public LocalDateTime getLastAccessDate() {
        if (attributes != null) {
            return LocalDateTime.ofInstant(attributes.lastAccessTime().toInstant(), ZoneId.systemDefault());
        } else {
            try {
                this.attributes = Files.readAttributes(path, BasicFileAttributes.class);
            } catch (IOException e) {
                return LocalDateTime.now();
            }
            Instant instant = attributes.lastAccessTime().toInstant();
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        }
    }

    public String getOwner() {
        try {
            return Files.getOwner(path).getName();
        } catch (IOException e) {
            return "";
        }
    }

    public String getContentType() {
        try {
            return Files.probeContentType(path);
        } catch (IOException e) {
            return "";
        }
    }

    public long getFileSizeBytes() {
        try {
            return Files.size(path);
        } catch (IOException e) {
            return 0L;
        }
    }

    public File getFile(){
        return path.toFile();
    }

    @Override
    public String toString() {
        return getPathString();
    }

}
