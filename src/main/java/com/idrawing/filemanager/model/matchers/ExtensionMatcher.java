package com.idrawing.filemanager.model.matchers;

import com.google.common.base.Strings;
import com.idrawing.filemanager.domain.FileCriteria;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import static com.google.common.io.Files.getFileExtension;

/**
 * Created by Admin on 25.02.2017.
 */
public class ExtensionMatcher extends MatchChain {

    public ExtensionMatcher(MatchChain nextMatcher, FileCriteria criteria) {
        super(nextMatcher, criteria);
    }

    @Override
    public boolean match(Path file, BasicFileAttributes attrs) {
        return !Strings.isNullOrEmpty(criteria.getExtension())
                && isMatch(criteria.getExtension(), file)
                && nextMatcher.match(file, attrs);
    }

    private boolean isMatch(String extension, Path file) {
        return extension.equalsIgnoreCase(getFileExtension(file.toAbsolutePath().toString()));
    }
}
