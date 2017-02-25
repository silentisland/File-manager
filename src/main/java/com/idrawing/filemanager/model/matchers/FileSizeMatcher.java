package com.idrawing.filemanager.model.matchers;

import com.idrawing.filemanager.domain.FileCriteria;
import org.apache.commons.validator.routines.LongValidator;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

/**
 * Created by Admin on 25.02.2017.
 */
public class FileSizeMatcher extends MatchChain {
    public FileSizeMatcher(MatchChain nextMatcher, FileCriteria criteria) {
        super(nextMatcher, criteria);
    }

    @Override
    public boolean match(Path file, BasicFileAttributes attrs) {
        return isMatch(attrs,
                defaultIfNull(criteria.getFileSizeMin(), Long.MIN_VALUE),
                defaultIfNull(criteria.getFileSizeMax(), Long.MAX_VALUE))
                && nextMatcher.match(file, attrs);
    }

    private boolean isMatch(BasicFileAttributes attrs, long min, long max) {
        return LongValidator.getInstance().isInRange(attrs.size(), min, max);
    }
}
