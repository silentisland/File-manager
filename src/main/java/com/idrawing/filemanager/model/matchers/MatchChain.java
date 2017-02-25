package com.idrawing.filemanager.model.matchers;

import com.idrawing.filemanager.domain.FileCriteria;
import lombok.AllArgsConstructor;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by Admin on 25.02.2017.
 */
@AllArgsConstructor
public abstract class MatchChain {
    protected MatchChain nextMatcher;
    protected FileCriteria criteria;

    public abstract boolean match(Path file, BasicFileAttributes attrs);
}
