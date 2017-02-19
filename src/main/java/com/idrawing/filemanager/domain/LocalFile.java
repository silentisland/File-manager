package com.idrawing.filemanager.domain;

import lombok.Getter;

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
    }

    public String getName() {
        return getNameWithoutExtension(path.toAbsolutePath().toString());
    }

    public String getExtension() {
        return getFileExtension(path.toAbsolutePath().toString());
    }

    public String getPathString() {
        return path.toAbsolutePath().toString();
    }

    public LocalDateTime getCreationDate() throws IOException {
        if (attributes != null) {
            return LocalDateTime.ofInstant(attributes.creationTime().toInstant(), ZoneId.systemDefault());
        } else {
            this.attributes = Files.readAttributes(path, BasicFileAttributes.class);
            Instant instant = attributes.creationTime().toInstant();
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        }
    }

    public LocalDateTime getLastModifiedDate() throws IOException {
        if (attributes != null) {
            return LocalDateTime.ofInstant(attributes.lastModifiedTime().toInstant(), ZoneId.systemDefault());
        } else {
            this.attributes = Files.readAttributes(path, BasicFileAttributes.class);
            Instant instant = attributes.lastModifiedTime().toInstant();
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        }
    }

    public LocalDateTime getLastAccessDate() throws IOException {
        if (attributes != null) {
            return LocalDateTime.ofInstant(attributes.lastAccessTime().toInstant(), ZoneId.systemDefault());
        } else {
            this.attributes = Files.readAttributes(path, BasicFileAttributes.class);
            Instant instant = attributes.lastAccessTime().toInstant();
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        }
    }

    public String getCreator() throws IOException {
        return Files.getOwner(path).getName();
    }

    public String getContentType() throws IOException {
        return Files.probeContentType(path);
    }

    public long getFileSizeBytes() throws IOException {
        return Files.size(path);
    }

    @Override
    public String toString() {
        return getPathString();
    }
}
