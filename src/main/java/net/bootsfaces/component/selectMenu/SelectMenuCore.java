package net.bootsfaces.component.selectMenu;

import javax.faces.component.html.HtmlSelectOneMenu;

/**
 * Created by cooper on 7/1/16.
 */
public abstract class SelectMenuCore extends HtmlSelectOneMenu implements net.bootsfaces.render.IHasTooltip  {

    protected enum PropertyKeys {
        ajax,
        alt,
        binding,
        fieldSize,
        immediate,
        inline,
        oncomplete,
        placeholder,
        process,
        renderLabel,
        required,
        requiredMessage,
        size,
        span,
        tooltip,
        tooltipContainer,
        tooltipDelay,
        tooltipDelayHide,
        tooltipDelayShow,
        tooltipPosition,
        update;
        String toString;

        PropertyKeys(String toString) {
            this.toString = toString;
        }

        PropertyKeys() {
        }

        public String toString() {
            return ((this.toString != null) ? this.toString : super.toString());
        }
    }

    /**
     * Activates AJAX. The default value is false (no AJAX). <P>
     * @return Returns the value of the attribute, or null, if it hasn't been set by the JSF file.
     */
    public boolean isAjax() {
        return (boolean) (Boolean) getStateHelper().eval(SelectMenuCore.PropertyKeys.ajax, false);
    }

    /**
     * Activates AJAX. The default value is false (no AJAX). <P>
     * Usually this method is called internally by the JSF engine.
     */
    public void setAjax(boolean _ajax) {
        getStateHelper().put(SelectMenuCore.PropertyKeys.ajax, _ajax);
    }

    /**
     * Alternate textual description of the input element. <P>
     * @return Returns the value of the attribute, or null, if it hasn't been set by the JSF file.
     */
    public String getAlt() {
        return (String) getStateHelper().eval(SelectMenuCore.PropertyKeys.alt);
    }

    /**
     * Alternate textual description of the input element. <P>
     * Usually this method is called internally by the JSF engine.
     */
    public void setAlt(String _alt) {
        getStateHelper().put(SelectMenuCore.PropertyKeys.alt, _alt);
    }

    /**
     * An EL expression referring to a server side UIComponent instance in a backing bean. <P>
     * @return Returns the value of the attribute, or null, if it hasn't been set by the JSF file.
     */
    public javax.faces.component.UIComponent getBinding() {
        return (javax.faces.component.UIComponent) getStateHelper().eval(SelectMenuCore.PropertyKeys.binding);
    }

    /**
     * An EL expression referring to a server side UIComponent instance in a backing bean. <P>
     * Usually this method is called internally by the JSF engine.
     */
    public void setBinding(javax.faces.component.UIComponent _binding) {
        getStateHelper().put(SelectMenuCore.PropertyKeys.binding, _binding);
    }


    /**
     * The size of the input. Possible values are xs (extra small), sm (small), md (medium) and lg (large) . <P>
     * @return Returns the value of the attribute, or null, if it hasn't been set by the JSF file.
     */
    public String getFieldSize() {
        return (String) getStateHelper().eval(SelectMenuCore.PropertyKeys.fieldSize);
    }

    /**
     * The size of the input. Possible values are xs (extra small), sm (small), md (medium) and lg (large) . <P>
     * Usually this method is called internally by the JSF engine.
     */
    public void setFieldSize(String _fieldSize) {
        getStateHelper().put(SelectMenuCore.PropertyKeys.fieldSize, _fieldSize);
    }

    /**
     * Flag indicating that, if this component is activated by the user, notifications should be delivered to interested listeners and actions immediately (that is, during Apply Request Values phase) rather than waiting until Invoke Application phase. Default is false. <P>
     * @return Returns the value of the attribute, or null, if it hasn't been set by the JSF file.
     */
    public boolean isImmediate() {
        return (boolean) (Boolean) getStateHelper().eval(SelectMenuCore.PropertyKeys.immediate, false);
    }

    /**
     * Flag indicating that, if this component is activated by the user, notifications should be delivered to interested listeners and actions immediately (that is, during Apply Request Values phase) rather than waiting until Invoke Application phase. Default is false. <P>
     * Usually this method is called internally by the JSF engine.
     */
    public void setImmediate(boolean _immediate) {
        getStateHelper().put(SelectMenuCore.PropertyKeys.immediate, _immediate);
    }

    /**
     * Inline forms are more compact and put the label to the left hand side of the input field instead of putting it above the input field. Inline applies only to screens that are at least 768 pixels wide. <P>
     * @return Returns the value of the attribute, or null, if it hasn't been set by the JSF file.
     */
    public boolean isInline() {
        return (boolean) (Boolean) getStateHelper().eval(SelectMenuCore.PropertyKeys.inline, false);
    }

    /**
     * Inline forms are more compact and put the label to the left hand side of the input field instead of putting it above the input field. Inline applies only to screens that are at least 768 pixels wide. <P>
     * Usually this method is called internally by the JSF engine.
     */
    public void setInline(boolean _inline) {
        getStateHelper().put(SelectMenuCore.PropertyKeys.inline, _inline);
    }

    /**
     * JavaScript to be executed when ajax completes with success. <P>
     * @return Returns the value of the attribute, or null, if it hasn't been set by the JSF file.
     */
    public String getOncomplete() {
        return (String) getStateHelper().eval(SelectMenuCore.PropertyKeys.oncomplete);
    }

    /**
     * JavaScript to be executed when ajax completes with success. <P>
     * Usually this method is called internally by the JSF engine.
     */
    public void setOncomplete(String _oncomplete) {
        getStateHelper().put(SelectMenuCore.PropertyKeys.oncomplete, _oncomplete);
    }

    /**
     * The placeholder attribute shows text in a field until the field is focused upon, then hides the text. <P>
     * @return Returns the value of the attribute, or null, if it hasn't been set by the JSF file.
     */
    public String getPlaceholder() {
        return (String) getStateHelper().eval(SelectMenuCore.PropertyKeys.placeholder);
    }

    /**
     * The placeholder attribute shows text in a field until the field is focused upon, then hides the text. <P>
     * Usually this method is called internally by the JSF engine.
     */
    public void setPlaceholder(String _placeholder) {
        getStateHelper().put(SelectMenuCore.PropertyKeys.placeholder, _placeholder);
    }

    /**
     * Comma or space separated list of ids or search expressions denoting which values are to be sent to the server. <P>
     * @return Returns the value of the attribute, or null, if it hasn't been set by the JSF file.
     */
    public String getProcess() {
        return (String) getStateHelper().eval(SelectMenuCore.PropertyKeys.process);
    }

    /**
     * Comma or space separated list of ids or search expressions denoting which values are to be sent to the server. <P>
     * Usually this method is called internally by the JSF engine.
     */
    public void setProcess(String _process) {
        getStateHelper().put(SelectMenuCore.PropertyKeys.process, _process);
    }

    /**
     * Allows you to suppress automatic rendering of labels. Used by AngularFaces, too. <P>
     * @return Returns the value of the attribute, or null, if it hasn't been set by the JSF file.
     */
    public boolean isRenderLabel() {
        return (boolean) (Boolean) getStateHelper().eval(SelectMenuCore.PropertyKeys.renderLabel,
                net.bootsfaces.component.ComponentUtils.isRenderLabelDefault());
    }

    /**
     * Allows you to suppress automatic rendering of labels. Used by AngularFaces, too. <P>
     * Usually this method is called internally by the JSF engine.
     */
    public void setRenderLabel(boolean _renderLabel) {
        getStateHelper().put(SelectMenuCore.PropertyKeys.renderLabel, _renderLabel);
    }

    /**
     * Boolean value Require input in the component when the form is submitted. <P>
     * @return Returns the value of the attribute, or null, if it hasn't been set by the JSF file.
     */
    public boolean isRequired() {
        return (boolean) (Boolean) getStateHelper().eval(SelectMenuCore.PropertyKeys.required, false);
    }

    /**
     * Boolean value Require input in the component when the form is submitted. <P>
     * Usually this method is called internally by the JSF engine.
     */
    public void setRequired(boolean _required) {
        getStateHelper().put(SelectMenuCore.PropertyKeys.required, _required);
    }

    /**
     * Message to show if the user did not specify a value and the attribute required is set to true. <P>
     * @return Returns the value of the attribute, or null, if it hasn't been set by the JSF file.
     */
    public String getRequiredMessage() {
        return (String) getStateHelper().eval(SelectMenuCore.PropertyKeys.requiredMessage);
    }

    /**
     * Message to show if the user did not specify a value and the attribute required is set to true. <P>
     * Usually this method is called internally by the JSF engine.
     */
    public void setRequiredMessage(String _requiredMessage) {
        getStateHelper().put(SelectMenuCore.PropertyKeys.requiredMessage, _requiredMessage);
    }

    /**
     * Number of characters used to determine the width of the input element. <P>
     * @return Returns the value of the attribute, or null, if it hasn't been set by the JSF file.
     */
    public int getSize() {
        return (int) (Integer) getStateHelper().eval(SelectMenuCore.PropertyKeys.size, 0);
    }

    /**
     * Number of characters used to determine the width of the input element. <P>
     * Usually this method is called internally by the JSF engine.
     */
    public void setSize(int _size) {
        getStateHelper().put(SelectMenuCore.PropertyKeys.size, _size);
    }

    /**
     * The size of the input specified as number of grid columns. <P>
     * @return Returns the value of the attribute, or null, if it hasn't been set by the JSF file.
     */
    public int getSpan() {
        return (int) (Integer) getStateHelper().eval(SelectMenuCore.PropertyKeys.span, 0);
    }

    /**
     * The size of the input specified as number of grid columns. <P>
     * Usually this method is called internally by the JSF engine.
     */
    public void setSpan(int _span) {
        getStateHelper().put(SelectMenuCore.PropertyKeys.span, _span);
    }

    /**
     * The text of the tooltip. <P>
     * @return Returns the value of the attribute, or null, if it hasn't been set by the JSF file.
     */
    public String getTooltip() {
        return (String) getStateHelper().eval(SelectMenuCore.PropertyKeys.tooltip);
    }

    /**
     * The text of the tooltip. <P>
     * Usually this method is called internally by the JSF engine.
     */
    public void setTooltip(String _tooltip) {
        getStateHelper().put(SelectMenuCore.PropertyKeys.tooltip, _tooltip);
    }

    /**
     * Where is the tooltip div generated? That's primarily a technical value that can be used to fix rendering errors in special cases. Also see data-container in the documentation of Bootstrap. The default value is body. <P>
     * @return Returns the value of the attribute, or null, if it hasn't been set by the JSF file.
     */
    public String getTooltipContainer() {
        return (String) getStateHelper().eval(SelectMenuCore.PropertyKeys.tooltipContainer, "body");
    }

    /**
     * Where is the tooltip div generated? That's primarily a technical value that can be used to fix rendering errors in special cases. Also see data-container in the documentation of Bootstrap. The default value is body. <P>
     * Usually this method is called internally by the JSF engine.
     */
    public void setTooltipContainer(String _tooltipContainer) {
        getStateHelper().put(SelectMenuCore.PropertyKeys.tooltipContainer, _tooltipContainer);
    }

    /**
     * The tooltip is shown and hidden with a delay. This value is the delay in milliseconds. Defaults to 0 (no delay). <P>
     * @return Returns the value of the attribute, or null, if it hasn't been set by the JSF file.
     */
    public int getTooltipDelay() {
        return (int) (Integer) getStateHelper().eval(SelectMenuCore.PropertyKeys.tooltipDelay, 0);
    }

    /**
     * The tooltip is shown and hidden with a delay. This value is the delay in milliseconds. Defaults to 0 (no delay). <P>
     * Usually this method is called internally by the JSF engine.
     */
    public void setTooltipDelay(int _tooltipDelay) {
        getStateHelper().put(SelectMenuCore.PropertyKeys.tooltipDelay, _tooltipDelay);
    }

    /**
     * The tooltip is hidden with a delay. This value is the delay in milliseconds. Defaults to 0 (no delay). <P>
     * @return Returns the value of the attribute, or null, if it hasn't been set by the JSF file.
     */
    public int getTooltipDelayHide() {
        return (int) (Integer) getStateHelper().eval(SelectMenuCore.PropertyKeys.tooltipDelayHide, 0);
    }

    /**
     * The tooltip is hidden with a delay. This value is the delay in milliseconds. Defaults to 0 (no delay). <P>
     * Usually this method is called internally by the JSF engine.
     */
    public void setTooltipDelayHide(int _tooltipDelayHide) {
        getStateHelper().put(SelectMenuCore.PropertyKeys.tooltipDelayHide, _tooltipDelayHide);
    }

    /**
     * The tooltip is shown with a delay. This value is the delay in milliseconds. Defaults to 0 (no delay). <P>
     * @return Returns the value of the attribute, or null, if it hasn't been set by the JSF file.
     */
    public int getTooltipDelayShow() {
        return (int) (Integer) getStateHelper().eval(SelectMenuCore.PropertyKeys.tooltipDelayShow, 0);
    }

    /**
     * The tooltip is shown with a delay. This value is the delay in milliseconds. Defaults to 0 (no delay). <P>
     * Usually this method is called internally by the JSF engine.
     */
    public void setTooltipDelayShow(int _tooltipDelayShow) {
        getStateHelper().put(SelectMenuCore.PropertyKeys.tooltipDelayShow, _tooltipDelayShow);
    }

    /**
     * Where is the tooltip to be displayed? Possible values: "top", "bottom", "right", "left", "auto", "auto top", "auto bottom", "auto right" and "auto left". Default to "bottom". <P>
     * @return Returns the value of the attribute, or null, if it hasn't been set by the JSF file.
     */
    public String getTooltipPosition() {
        return (String) getStateHelper().eval(SelectMenuCore.PropertyKeys.tooltipPosition);
    }

    /**
     * Where is the tooltip to be displayed? Possible values: "top", "bottom", "right", "left", "auto", "auto top", "auto bottom", "auto right" and "auto left". Default to "bottom". <P>
     * Usually this method is called internally by the JSF engine.
     */
    public void setTooltipPosition(String _tooltipPosition) {
        getStateHelper().put(SelectMenuCore.PropertyKeys.tooltipPosition, _tooltipPosition);
    }

    /**
     * Component(s) to be updated with ajax. <P>
     * @return Returns the value of the attribute, or null, if it hasn't been set by the JSF file.
     */
    public String getUpdate() {
        return (String) getStateHelper().eval(SelectMenuCore.PropertyKeys.update);
    }

    /**
     * Component(s) to be updated with ajax. <P>
     * Usually this method is called internally by the JSF engine.
     */
    public void setUpdate(String _update) {
        getStateHelper().put(SelectMenuCore.PropertyKeys.update, _update);
    }

}
