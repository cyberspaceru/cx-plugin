package ru.cyberspace.plugin.context.project;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationMemberValue;
import com.intellij.psi.PsiConstantEvaluationHelper;
import com.intellij.psi.PsiMethod;

import static com.intellij.psi.JavaPsiFacade.getInstance;
import static ru.cyberspace.plugin.context.PluginUtil.getAnnotationAttributeValue;

public class ActionEntryAnnotation {
    private PsiAnnotation annotation;
    private PsiMethod method;
    private Project project;
    private String value;

    public ActionEntryAnnotation(PsiAnnotation annotation, PsiMethod method, Project project) {
        this.annotation = annotation;
        this.method = method;
        this.project = project;
    }

    public String getValue() {
        if (this.value != null) {
            return this.value;
        }
        PsiConstantEvaluationHelper evaluationHelper = getInstance(project).getConstantEvaluationHelper();
        PsiAnnotationMemberValue memberValue = getAnnotationAttributeValue(annotation, "value");
        Object expression = evaluationHelper.computeConstantExpression(memberValue, false);
        if (expression != null && expression.toString().length() > 0) {
            this.value = expression.toString();
        }
        return this.value;
    }
}
