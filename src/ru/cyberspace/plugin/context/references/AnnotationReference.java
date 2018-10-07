package ru.cyberspace.plugin.context.references;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AnnotationReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    private PsiAnnotation annotation;

    public AnnotationReference(@NotNull PsiElement element, @NotNull PsiAnnotation annotation, TextRange rangeInElement) {
        super(element, rangeInElement);
        this.annotation = annotation;
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        return annotation;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        LookupElement element = LookupElementBuilder.create(annotation)
                .withTypeText("Page Object");
        return new LookupElement[]{element};
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean b) {
        return new ResolveResult[]{new PsiElementResolveResult(annotation)};
    }
}
