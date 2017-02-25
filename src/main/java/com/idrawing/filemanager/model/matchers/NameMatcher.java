package com.idrawing.filemanager.model.matchers;

import com.google.common.base.Strings;
import com.idrawing.filemanager.domain.FileCriteria;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import static com.google.common.io.Files.getNameWithoutExtension;

/**
 * Created by Admin on 25.02.2017.
 */
public class NameMatcher extends MatchChain {

    public NameMatcher(MatchChain nextMatcher, FileCriteria criteria) {
        super(nextMatcher, criteria);
    }

    @Override
    public boolean match(Path file, BasicFileAttributes attrs) {
        return !Strings.isNullOrEmpty(criteria.getFileName())
                && isMatch(criteria.getFileName(), file)
                && nextMatcher.match(file, attrs);
    }

    private boolean isMatch(String extension, Path file) {
        return extension.equalsIgnoreCase(getNameWithoutExtension(file.toAbsolutePath().toString()));
    }
}
