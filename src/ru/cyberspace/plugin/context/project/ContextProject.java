package ru.cyberspace.plugin.context.project;

import com.intellij.codeInsight.completion.PlainPrefixMatcher;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.util.Processor;
import org.jetbrains.annotations.NotNull;
import ru.cyberspace.plugin.context.Settings;
import ru.cyberspace.plugin.context.support.RowCompletionString;
import ru.cyberspace.plugin.context.support.entry.EntryStep;

import java.util.*;

import static com.intellij.codeInsight.completion.AllClassesGetter.processJavaClasses;
import static com.intellij.psi.search.GlobalSearchScope.projectScope;

public class ContextProject {

    public static EntryStep findStep(@NotNull List<EntryStep> steps, @NotNull String row) {
        return steps.stream()
                .filter(x -> x.match(row))
                .findFirst()
                .orElse(null);
    }

    public static EntryStep findPageStep(String text) {
        return findStep(Settings.pageSteps, text);
    }

    /**
     * Find current page on a feature file by the previous page step.
     */
    public static PageEntryAnnotation findCurrentPageName(@NotNull PsiElement element) {
        PsiElement cursor = element;
        while (cursor != null) {
            PsiElement step = cursor.getLastChild();
            if (step != null) {
                String stepText = step.getText();
                for (EntryStep entryStep : Settings.pageSteps) {
                    if (entryStep.match(stepText)) {
                        String name = entryStep.extractEntry(stepText);
                        return getPageByName(element.getProject(), name);
                    }
                }
            }
            cursor = cursor.getPrevSibling();
        }
        return null;
    }

    public static PageEntryAnnotation getPageByName(@NotNull Project project, @NotNull String name) {
        List<PageEntryAnnotation> result = new ArrayList<>();
        Processor<PsiClass> pageEntryProcessor = (psiClass -> {
            PageEntryAnnotation annotation = getPageEntryAnnotation(psiClass, project);
            if (annotation != null && name.equals(annotation.getTitle())) {
                result.add(annotation);
                return false;
            }
            return true;
        });
        processJavaClasses(new PlainPrefixMatcher(""), project, projectScope(project), pageEntryProcessor);
        return result.isEmpty() ? null : result.get(0);
    }

    public static List<PageEntryAnnotation> getPageEntries(@NotNull Project project) {
        List<PageEntryAnnotation> result = new ArrayList<>();
        Processor<PsiClass> pageEntryProcessor = (psiClass -> {
            PageEntryAnnotation annotation = getPageEntryAnnotation(psiClass, project);
            if (annotation != null) {
                result.add(annotation);
            }
            return true;
        });
        processJavaClasses(new PlainPrefixMatcher(""), project, projectScope(project), pageEntryProcessor);
        return result;
    }

    private static PageEntryAnnotation getPageEntryAnnotation(@NotNull PsiClass page, @NotNull Project project) {
        PsiModifierList modifiers = page.getModifierList();
        if (modifiers == null) {
            return null;
        }
        return Arrays.stream(modifiers.getAnnotations())
                .filter(x -> x.getQualifiedName() != null && x.getQualifiedName().endsWith(".PageEntry"))
                .map(x -> new PageEntryAnnotation(x, page, project))
                .findFirst()
                .orElse(null);
    }
}
