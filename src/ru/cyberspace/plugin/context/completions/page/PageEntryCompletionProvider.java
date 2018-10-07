package ru.cyberspace.plugin.context.completions.page;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import ru.cyberspace.plugin.context.completions.EntryCompletion;
import ru.cyberspace.plugin.context.phantom.GherkinPhantom;
import ru.cyberspace.plugin.context.project.ContextProject;
import ru.cyberspace.plugin.context.project.PageEntryAnnotation;
import ru.cyberspace.plugin.context.support.entry.EntryStep;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.cyberspace.plugin.context.Settings.pageSteps;

public class PageEntryCompletionProvider extends EntryCompletion {
    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet resultSet) {
        PsiElement element = parameters.getPosition().getContext();
        if (element == null) {
            return;
        }
        GherkinPhantom phantom = new GherkinPhantom(element);
        if (phantom.isGherkinStepImpl() && element.getLastChild() != null) {
            String stepText = element.getLastChild().getText();
            EntryStep page = ContextProject.findPageStep(stepText);
            if (page != null) {
                List<String> completions = compile(stepText, element.getProject(), pageSteps, new PageEntriesProvider());
                if (!completions.isEmpty()) {
                    completions.forEach(x -> resultSet.addElement(LookupElementBuilder.create(x)));
                    resultSet.stopHere();
                }
            }
        }
    }

    class PageEntriesProvider implements Function<Project, List<String>> {
        @Override
        public List<String> apply(Project project) {
            List<PageEntryAnnotation> pages = ContextProject.getPageEntries(project);
            return pages.stream().map(PageEntryAnnotation::getTitle).collect(Collectors.toList());
        }
    }
}
