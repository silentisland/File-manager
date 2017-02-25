package com.idrawing.filemanager.model.matchers;

import com.idrawing.filemanager.domain.FileCriteria;
import org.apache.commons.validator.routines.LongValidator;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZoneOffset;

/**
 * Created by Admin on 25.02.2017.
 */
public class CreationDateMatcher extends MatchChain {
    public CreationDateMatcher(MatchChain nextMatcher, FileCriteria criteria) {
        super(nextMatcher, criteria);
    }

    @Override
    public boolean match(Path file, BasicFileAttributes attrs) {
        long from = criteria.getCreationDateFrom().toInstant(ZoneOffset.UTC).toEpochMilli();
        long to = criteria.getCreationDateTo().toInstant(ZoneOffset.UTC).toEpochMilli();
        return isMatch(attrs, from, to) && nextMatcher.match(file, attrs);
    }

    private boolean isMatch(BasicFileAttributes attrs, long from, long to) {
        return LongValidator.getInstance().isInRange(attrs.creationTime().toMillis(), from, to);
    }
}

