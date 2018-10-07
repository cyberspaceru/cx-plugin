package ru.cyberspace.plugin.context.project;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.intellij.psi.JavaPsiFacade.getInstance;
import static ru.cyberspace.plugin.context.PluginUtil.getAnnotationAttributeValue;

public class PageEntryAnnotation {
    private final PsiAnnotation annotation;
    private final PsiClass clazz;
    private final Project project;
    private String title;

    public PageEntryAnnotation(@NotNull PsiAnnotation annotation, @NotNull PsiClass clazz, @NotNull Project project) {
        this.annotation = annotation;
        this.clazz = clazz;
        this.project = project;
    }

    public String getTitle() {
        if (this.title != null) {
            return this.title;
        }
        PsiConstantEvaluationHelper evaluationHelper = getInstance(project).getConstantEvaluationHelper();
        PsiAnnotationMemberValue memberValue = getAnnotationAttributeValue(annotation, "title");
        Object expression = evaluationHelper.computeConstantExpression(memberValue, false);
        if (expression != null && expression.toString().length() > 0) {
            this.title = expression.toString();
        }
        return this.title;
    }

    public List<ActionEntryAnnotation> getActionAnnotations() {
        List<ActionEntryAnnotation> result = new ArrayList<>();
        for (PsiMethod method : clazz.getAllMethods()) {
            PsiAnnotation[] annotations = method.getModifierList().getAnnotations();
            for (PsiAnnotation annotation : annotations) {
                String qualifiedName = annotation.getQualifiedName();
                if (qualifiedName != null && qualifiedName.endsWith("ActionTitle")) {
                    result.add(new ActionEntryAnnotation(annotation, method, project));
                    break;
                }
            }
        }
        return result;
    }

    public PsiAnnotation getAnnotation() {
        return annotation;
    }

    public PsiClass getClazz() {
        return clazz;
    }
}
