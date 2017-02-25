package com.idrawing.filemanager.model.matchers;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by Admin on 25.02.2017.
 */
public class EndMatcher extends MatchChain {

    public EndMatcher() {
        super(null, null);
    }

    @Override
    public boolean match(Path file, BasicFileAttributes attrs) {
        return true;
    }
}
