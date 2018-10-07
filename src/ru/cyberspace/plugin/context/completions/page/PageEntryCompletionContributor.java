package ru.cyberspace.plugin.context.completions.page;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PlatformPatterns;

public class PageEntryCompletionContributor extends CompletionContributor {

    public PageEntryCompletionContributor() {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(), new PageEntryCompletionProvider());
    }
}
