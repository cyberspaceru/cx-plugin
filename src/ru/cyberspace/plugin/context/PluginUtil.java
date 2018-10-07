package ru.cyberspace.plugin.context;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.Ref;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiAnnotationMemberValue;
import com.intellij.psi.PsiNameValuePair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PluginUtil {
    public static final String RULEZZZZ = "IntellijIdeaRulezzz";

    @Nullable
    private static String getAnnotationName(@NotNull PsiAnnotation annotation) {
        final Ref<String> qualifiedAnnotationName = new Ref<>();
        ApplicationManager.getApplication().runReadAction(() -> {
            String qualifiedName = annotation.getQualifiedName();
            qualifiedAnnotationName.set(qualifiedName);
        });
        return qualifiedAnnotationName.get();
    }

    @Nullable
    public static PsiAnnotationMemberValue getAnnotationAttributeValue(@NotNull PsiAnnotation stepAnnotation, @NotNull String name) {
        PsiNameValuePair[] attributes = stepAnnotation.getParameterList().getAttributes();
        if (attributes.length > 0) {
            for (int i = 1; i < attributes.length; i++) {
                PsiNameValuePair pair = attributes[i];
                final String pairName = pair.getName();
                if (pairName != null && pairName.equals(name)) {
                    return pair.getValue();
                }
            }
            return attributes[0].getValue();
        }
        return null;
    }
}
