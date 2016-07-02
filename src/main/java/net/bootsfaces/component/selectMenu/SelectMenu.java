package net.bootsfaces.component.selectMenu;

import net.bootsfaces.component.ajax.IAJAXComponent;
import net.bootsfaces.listeners.AddResourcesListener;
import net.bootsfaces.render.Tooltip;
import net.bootsfaces.utils.BsfUtils;

import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;
import javax.faces.context.FacesContext;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * Created by cooper on 7/1/16.
 */
@FacesComponent("net.bootsfaces.component.selectMenu.SelectMenu")
public class SelectMenu extends SelectMenuCore implements net.bootsfaces.render.IHasTooltip, IAJAXComponent {



    public static final String COMPONENT_TYPE = "net.bootsfaces.component.selectMenu.SelectMenu";

    public static final String COMPONENT_FAMILY = "net.bootsfaces.component";

    public static final String DEFAULT_RENDERER = "net.bootsfaces.component.selectMenu.SelectMenu";

    public SelectMenu() {
        Tooltip.addResourceFiles();
        AddResourcesListener.addThemedCSSResource("dropdowns.css");
        AddResourcesListener.addThemedCSSResource("bsf.css");
        AddResourcesListener.addThemedCSSResource("core.css");
        setRendererType(DEFAULT_RENDERER);
    }

    public void setValueExpression(String name, ValueExpression binding) {
        name = BsfUtils.snakeCaseToCamelCase(name);
        super.setValueExpression(name, binding);
    }

    private static final Collection<String> EVENT_NAMES = Collections.unmodifiableCollection(
            Arrays.asList("blur", "change", "valueChange", "click", "dblclick", "focus", "keydown", "keypress", "keyup",
                    "mousedown", "mousemove", "mouseout", "mouseover", "mouseup", "select"));

    /**
     * returns the subset of AJAX requests that are implemented by jQuery
     * callback or other non-standard means (such as the onclick event of
     * b:tabView, which has to be implemented manually).
     *
     * @return
     */
    public Map<String, String> getJQueryEvents() {
        return null;
    }

    public Collection<String> getEventNames() {
        return EVENT_NAMES;
    }

    public String getDefaultEventName() {
        return "valueChange";
    }

    public void validateValue(FacesContext context, Object newValue) {
        super.validateValue(context, newValue);
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

}
