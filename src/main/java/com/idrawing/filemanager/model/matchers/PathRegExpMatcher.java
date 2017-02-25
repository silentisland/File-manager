package com.idrawing.filemanager.model.matchers;

import com.idrawing.filemanager.domain.FileCriteria;
import org.apache.commons.validator.routines.RegexValidator;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * Created by Admin on 25.02.2017.
 */
public class PathRegExpMatcher extends MatchChain {
    public PathRegExpMatcher(MatchChain nextMatcher, FileCriteria criteria) {
        super(nextMatcher, criteria);
    }

    @Override
    public boolean match(Path file, BasicFileAttributes attrs) {
        if(isNullOrEmpty(criteria.getRegExp())){
            return nextMatcher.match(file, attrs);
        }else {
            return isMatch(criteria.getRegExp(), file) && nextMatcher.match(file, attrs);
        }
    }

    private boolean isMatch(String regExp, Path file) {
        return new RegexValidator(regExp, false).isValid(file.toAbsolutePath().toString());
    }
}
