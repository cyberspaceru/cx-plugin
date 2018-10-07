package ru.cyberspace.plugin.context.completions.action;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import ru.cyberspace.plugin.context.completions.EntryCompletion;
import ru.cyberspace.plugin.context.phantom.GherkinPhantom;
import ru.cyberspace.plugin.context.project.ActionEntryAnnotation;
import ru.cyberspace.plugin.context.project.ContextProject;
import ru.cyberspace.plugin.context.project.PageEntryAnnotation;
import ru.cyberspace.plugin.context.support.RowCompletionString;
import ru.cyberspace.plugin.context.support.entry.EntryStep;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ru.cyberspace.plugin.context.Settings.actionSteps;
import static ru.cyberspace.plugin.context.project.ContextProject.findStep;

public class ActionEntryCompletionProvider extends EntryCompletion {
    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet resultSet) {
        PsiElement element = parameters.getPosition().getContext();
        if (element == null) {
            return;
        }
        GherkinPhantom phantom = new GherkinPhantom(element);
        if (phantom.isGherkinStepImpl() && element.getLastChild() != null) {
            String stepText = element.getLastChild().getText();
            RowCompletionString row = new RowCompletionString(stepText);
            EntryStep step = findStep(actionSteps, row.getText());
            if (step == null) {
                return;
            }
            PageEntryAnnotation page = ContextProject.findCurrentPageName(element);
            if (page != null) {
                ActionEntriesProvider provider = new ActionEntriesProvider(page);
                List<String> completions = compile(stepText, element.getProject(), actionSteps, provider);
                if (!completions.isEmpty()) {
                    completions.forEach(x -> resultSet.addElement(LookupElementBuilder.create(x)));
                    resultSet.stopHere();
                }
            }
        }
    }

    class ActionEntriesProvider implements Function<Project, List<String>> {
        private final PageEntryAnnotation page;

        public ActionEntriesProvider(PageEntryAnnotation page) {
            this.page = page;
        }

        @Override
        public List<String> apply(Project project) {
            return page.getActionAnnotations().stream()
                    .map(ActionEntryAnnotation::getValue)
                    .collect(Collectors.toList());
        }
    }
}
