package com.idrawing.filemanager.model.matchers;

import com.idrawing.filemanager.domain.FileCriteria;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by Admin on 25.02.2017.
 */
public abstract class MatchChain {
    protected MatchChain nextMatcher;
    protected FileCriteria criteria;

    public MatchChain(MatchChain nextMatcher, FileCriteria criteria) {
        this.nextMatcher = nextMatcher;
        this.criteria = criteria;
    }

    public MatchChain(MatchChain nextMatcher) {
        this.nextMatcher = nextMatcher;
    }

    public abstract boolean match(Path file, BasicFileAttributes attrs);
}
