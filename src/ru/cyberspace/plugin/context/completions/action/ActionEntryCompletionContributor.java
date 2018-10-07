package ru.cyberspace.plugin.context.completions.action;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PlatformPatterns;

public class ActionEntryCompletionContributor extends CompletionContributor {
    public ActionEntryCompletionContributor() {
        extend(CompletionType.BASIC, PlatformPatterns.psiElement(), new ActionEntryCompletionProvider());
    }
}
