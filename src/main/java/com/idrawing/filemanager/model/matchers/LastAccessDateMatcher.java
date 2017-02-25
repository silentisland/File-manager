package com.idrawing.filemanager.model.matchers;

import com.idrawing.filemanager.domain.FileCriteria;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by Admin on 25.02.2017.
 */
public class LastAccessDateMatcher extends MatchChain {
    public LastAccessDateMatcher(MatchChain nextMatcher, FileCriteria criteria) {
        super(nextMatcher, criteria);
    }

    @Override
    public boolean match(Path file, BasicFileAttributes attrs) {
        return false;
    }
}
