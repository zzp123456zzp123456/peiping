package edu.is.util;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.IContext;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.naming.Context;
import javax.swing.text.AbstractDocument;
import java.io.Writer;

public class ThymeleafUtil {

    private static final TemplateEngine engine;
    static {
        engine = new TemplateEngine();
        ClassLoaderTemplateResolver classLoader = new ClassLoaderTemplateResolver();
        classLoader.setCharacterEncoding("utf-8");
        engine.setTemplateResolver(classLoader);

    }

    public static void process(String template, IContext context, Writer writer) {
        engine.process(template, context, writer);
    }

}
