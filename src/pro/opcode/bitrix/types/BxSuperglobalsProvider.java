package pro.opcode.bitrix.types;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.PhpNamedElement;
import com.jetbrains.php.lang.psi.elements.Variable;
import com.jetbrains.php.lang.psi.resolve.types.PhpTypeProvider2;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class BxSuperglobalsProvider extends CompletionContributor implements PhpTypeProvider2
{
	private static final HashMap<String, String> bxSuperglobals = new HashMap<String, String>(); static {
		bxSuperglobals.put("APPLICATION", "CMain");
		bxSuperglobals.put("USER", "CUser");
		bxSuperglobals.put("DB", "CDatabase");
	}

	@Override
	public char getKey() {
		return 'Я';
	}

	@Nullable
	@Override
	public String getType(PsiElement e) {
		if (e instanceof Variable)
			return bxSuperglobals.get(((Variable) e).getName());

		return null;
	}

	@Override
	public Collection<? extends PhpNamedElement> getBySignature(String expression, Project project) {
		if (bxSuperglobals.containsValue(expression))
			return PhpIndex.getInstance(project).getClassesByFQN(expression);

		return Collections.emptySet();
	}
}