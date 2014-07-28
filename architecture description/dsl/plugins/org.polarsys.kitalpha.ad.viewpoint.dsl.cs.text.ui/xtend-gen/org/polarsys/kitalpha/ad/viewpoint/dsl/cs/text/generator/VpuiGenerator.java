package org.polarsys.kitalpha.ad.viewpoint.dsl.cs.text.generator;

import com.google.common.base.Objects;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.polarsys.kitalpha.ad.viewpoint.dsl.as.model.vpui.UIDescription;
import org.polarsys.kitalpha.ad.viewpoint.dsl.cs.text.generator.CommonGenerator;

@SuppressWarnings("all")
public class VpuiGenerator extends CommonGenerator {
  public boolean checkInput(final List<EObject> objects) {
    final Function1<EObject,Boolean> _function = new Function1<EObject,Boolean>() {
        public Boolean apply(final EObject c) {
          return Boolean.valueOf((c instanceof UIDescription));
        }
      };
    EObject ui = IterableExtensions.<EObject>findFirst(objects, _function);
    boolean _equals = Objects.equal(ui, null);
    if (_equals) {
      return false;
    }
    return true;
  }
}