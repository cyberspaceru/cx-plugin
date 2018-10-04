package ru.cyberspace.plugin.context.phantom;

import com.intellij.psi.PsiElement;

public class GherkinPhantom {
    private PsiElement element;

    public GherkinPhantom(PsiElement element) {
        this.element = element;
    }

    public boolean isGherkinStepImpl() {
        return element.getClass().getSimpleName().equals("GherkinStepImpl");
    }
}
