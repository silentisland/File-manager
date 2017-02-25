package com.idrawing.filemanager.model.matchers;

import com.idrawing.filemanager.domain.FileCriteria;
import org.apache.commons.validator.routines.LongValidator;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by Admin on 25.02.2017.
 */
public class FileSizeMatcher extends MatchChain {

    public FileSizeMatcher(MatchChain nextMatcher, FileCriteria criteria) {
        super(nextMatcher, criteria);
    }

    @Override
    public boolean match(Path file, BasicFileAttributes attrs) {
        return isMatch(attrs, criteria.getFileSizeMin(), criteria.getFileSizeMax()) && nextMatcher.match(file, attrs);
    }

    private boolean isMatch(BasicFileAttributes attrs, long min, long max) {
        return LongValidator.getInstance().isInRange(attrs.size(), min, max);
    }
}
