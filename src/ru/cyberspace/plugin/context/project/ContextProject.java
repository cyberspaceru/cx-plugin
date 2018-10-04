package ru.cyberspace.plugin.context.project;

import com.intellij.codeInsight.completion.PlainPrefixMatcher;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.util.Processor;

import java.util.*;

import static com.intellij.codeInsight.completion.AllClassesGetter.processJavaClasses;
import static com.intellij.psi.search.GlobalSearchScope.projectScope;

public class ContextProject {

    public static List<PageEntryAnnotation> getPageEntries(Project project) {
        List<PageEntryAnnotation> result = new ArrayList<>();
        Processor<PsiClass> pageEntryProcessor = (psiClass -> {
            PageEntryAnnotation annotation = getPageEntryAnnotation(psiClass, project);
            if(annotation != null) {
                result.add(annotation);
            }
            return true;
        });
        processJavaClasses(new PlainPrefixMatcher(""), project, projectScope(project), pageEntryProcessor);
        return result;
    }

    private static PageEntryAnnotation getPageEntryAnnotation(PsiClass page, Project project) {
        if (page == null || project == null) {
            throw new NullPointerException();
        }
        PsiModifierList modifiers = page.getModifierList();
        if (modifiers == null) {
            return null;
        }
        return Arrays.stream(modifiers.getAnnotations())
                .filter(x -> x.getQualifiedName() != null && x.getQualifiedName().endsWith(".PageEntry"))
                .map(PageEntryAnnotation::new)
                .findFirst()
                .orElse(null);
    }
}
