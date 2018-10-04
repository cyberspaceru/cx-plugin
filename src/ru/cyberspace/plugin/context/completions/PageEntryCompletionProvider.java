package ru.cyberspace.plugin.context.completions;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import ru.cyberspace.plugin.context.phantom.GherkinPhantom;
import ru.cyberspace.plugin.context.project.ContextProject;

public class PageEntryCompletionProvider extends CompletionProvider<CompletionParameters> {
    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet resultSet) {
        PsiElement element = parameters.getPosition().getContext();
        if (element == null) {
            return;
        }
        GherkinPhantom phantom = new GherkinPhantom(element);
        if (phantom.isGherkinStepImpl()) {
            String step = element.getLastChild().getText();
            ContextProject.getPageEntries(element.getProject());
        }
    }
}
