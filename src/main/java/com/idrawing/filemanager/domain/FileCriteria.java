package com.idrawing.filemanager.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created by Admin on 21.02.2017.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileCriteria {
    private Collection<Path> paths;

    private String fileName, partOfPath, extension, regExp;

    private long fileSizeMin, fileSizeMax;

    private LocalDateTime creationDateFrom, creationDateTo;

    private LocalDateTime lastModifiedDateFrom, lastModifiedTo;

    private LocalDateTime lastAccessDateFrom, lastAccessDateTo;
}
