package com.idrawing.filemanager.model.visitors;

import com.idrawing.filemanager.domain.FileCriteria;
import com.idrawing.filemanager.domain.LocalFile;
import com.idrawing.filemanager.model.matchers.EndMatcher;
import com.idrawing.filemanager.model.matchers.ExtensionMatcher;
import com.idrawing.filemanager.model.matchers.MatchChain;
import com.idrawing.filemanager.model.matchers.NameMatcher;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collection;

/**
 * Created by Admin on 21.02.2017.
 */
@Getter
@AllArgsConstructor
public class CriteriaSearchVisitor extends SimpleFileVisitor<Path>{

    private FileCriteria criteria;
    private Collection<LocalFile> searchResult;

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (attrs.isRegularFile() && isMatchCriteria(file, attrs))
            searchResult.add(new LocalFile(file));
        return FileVisitResult.CONTINUE;
    }

    private boolean isMatchCriteria(Path file, BasicFileAttributes attributes) {
        MatchChain filter = new ExtensionMatcher(new NameMatcher(new EndMatcher(), criteria), criteria);
        return filter.match(file, attributes);
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        //TODO add logging
        return FileVisitResult.CONTINUE;
    }
}
