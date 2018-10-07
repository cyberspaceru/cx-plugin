package ru.cyberspace.plugin.context;

import com.intellij.openapi.module.Module;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.cucumber.BDDFrameworkType;
import org.jetbrains.plugins.cucumber.StepDefinitionCreator;
import org.jetbrains.plugins.cucumber.psi.GherkinFile;
import org.jetbrains.plugins.cucumber.steps.AbstractCucumberExtension;
import org.jetbrains.plugins.cucumber.steps.AbstractStepDefinition;

import java.util.*;

public class TestExtension extends AbstractCucumberExtension {

    @Override
    public List<PsiElement> resolveStep(@NotNull PsiElement element) {
        return new ArrayList<>();
    }

    @Override
    public boolean isStepLikeFile(@NotNull PsiElement psiElement, @NotNull PsiElement psiElement1) {
        return false;
    }

    @Override
    public boolean isWritableStepLikeFile(@NotNull PsiElement psiElement, @NotNull PsiElement psiElement1) {
        return false;
    }

    @NotNull
    @Override
    public BDDFrameworkType getStepFileType() {
        return null;
    }

    @NotNull
    @Override
    public StepDefinitionCreator getStepDefinitionCreator() {
        return null;
    }

    @NotNull
    @Override
    public Collection<String> getGlues(@NotNull GherkinFile gherkinFile, Set<String> set) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<AbstractStepDefinition> loadStepsFor(@Nullable PsiFile psiFile, @NotNull Module module) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<? extends PsiFile> getStepDefinitionContainers(@NotNull GherkinFile gherkinFile) {
        return Collections.EMPTY_LIST;
    }
}
