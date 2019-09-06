package com.monolithiot.iot.notification.template;

import com.monolithiot.iot.commons.exception.InternalServerErrorException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.val;
import lombok.var;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;
import java.util.HashMap;

/**
 * Create by 郭文梁 2019/9/6 9:43
 * EmailServiceImpl
 * TemplateCache
 * FreeMarker Template Cache
 *
 * @author 郭文梁
 * @data 2019/9/6 9:43
 */
@Component
public class TemplateCache extends HashMap<String, Template> {
    private final Configuration templateConfiguration;

    TemplateCache(@Qualifier("templateConfiguration") Configuration templateConfiguration) {
        super(16);
        this.templateConfiguration = templateConfiguration;
    }

    /**
     * Load Template
     *
     * @param name template name
     * @return Template Object
     */
    private Template loadTemplate(String name) {
        var template = get(name);
        if (template == null) {
            synchronized (this) {
                try {
                    template = templateConfiguration.getTemplate(name);
                    put(name, template);
                } catch (IOException e) {
                    throw new InternalServerErrorException("Error on load email template [" + name + "]!", e);
                }
            }
        }
        return template;
    }

    /**
     * Render template with data
     *
     * @param templateName template name
     * @param modelData    model data
     * @return render result
     */
    public String render(String templateName, Object modelData) {
        val template = loadTemplate(templateName);
        try {
            return FreeMarkerTemplateUtils.processTemplateIntoString(template, modelData);
        } catch (IOException | TemplateException e) {
            throw new InternalServerErrorException("Error on render template[" + templateName + "]", e);
        }
    }
}