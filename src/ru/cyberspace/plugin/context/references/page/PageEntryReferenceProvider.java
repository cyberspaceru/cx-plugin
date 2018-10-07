package ru.cyberspace.plugin.context.references.page;

import com.intellij.openapi.extensions.Extensions;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.picocontainer.defaults.SimpleReference;
import ru.cyberspace.plugin.context.phantom.GherkinPhantom;
import ru.cyberspace.plugin.context.project.ContextProject;
import ru.cyberspace.plugin.context.project.PageEntryAnnotation;
import ru.cyberspace.plugin.context.references.AnnotationReference;
import ru.cyberspace.plugin.context.support.entry.EntryStep;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PageEntryReferenceProvider extends PsiReferenceProvider {
    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
        GherkinPhantom phantom = new GherkinPhantom(element);
        if (phantom.isGherkinStepImpl() && element.getLastChild() != null) {
            EntryStep entryStep = ContextProject.findPageStep(element.getLastChild().getText());
            if (entryStep != null) {
                PageEntryAnnotation page = ContextProject.findCurrentPageName(element);
                if (page != null) {
                    PsiAnnotation annotation = page.getAnnotation();
                    int diff = element.getText().length() - element.getLastChild().getText().length();
                    int start = diff + entryStep.getStartIndex();
                    TextRange textRange = new TextRange(start, start + page.getTitle().length());
                    AnnotationReference reference = new AnnotationReference(element, annotation, textRange);
                    return new PsiReference[]{reference};
                }
            }
        }
        return PsiReference.EMPTY_ARRAY;
    }
//
//    public static class SimpleReference extends CucumberStepReference {
//        private PsiAnnotation annotation;
//
//        public SimpleReference(PsiElement step, PsiAnnotation annotation, TextRange range) {
//            super(step, range);
//            this.annotation = annotation;
//        }
//
//        @NotNull
//        @Override
//        public ResolveResult[] multiResolve(boolean incompleteCode) {
//            final List<ResolveResult> result = new ArrayList<>();
//            result.add(new PsiElementResolveResult(annotation));
//            final List<PsiElement> resolvedElements = new ArrayList<>();
//            final Object[] extensionList = Extensions.getExtensions(CucumberJvmExtensionPoint.EP_NAME);
//            for (Object e : extensionList) {
//                List<PsiElement> extensionResult = invokeResolveStep(e, getElement());
//                for (final PsiElement element : extensionResult) {
//                    if (element != null && !resolvedElements.contains(element)) {
//                        resolvedElements.add(element);
//                        result.add(new ResolveResult() {
//                            @Override
//                            public PsiElement getElement() {
//                                return element;
//                            }
//
//                            @Override
//                            public boolean isValidResult() {
//                                return true;
//                            }
//                        });
//                    }
//                }
//            }
//            return result.toArray(new ResolveResult[result.size()]);
//        }
//
//        private List<PsiElement> invokeResolveStep(Object extension, PsiElement element) {
//            try {
//                Method method = extension.getClass().getMethod("resolveStep", PsiElement.class);
//                return (List<PsiElement>) method.invoke(extension, element);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return new ArrayList<>();
//        }
//    }
}

