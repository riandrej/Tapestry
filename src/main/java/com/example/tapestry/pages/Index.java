package com.example.tapestry.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tapestry5.*;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.HttpError;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;




import java.time.ZonedDateTime;


/**
 * Start page of application tapestry.
 */
public class Index {

    private static final Logger logger = LogManager.getLogger(Index.class);

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    @Property
    @Inject
    @Symbol(SymbolConstants.TAPESTRY_VERSION)
    private String tapestryVersion;

    @InjectPage
    private About about;
    @Inject
    private Block block;
    @Inject
    private Block updatedName;
    @Component
    private Form form;
    @Property
    private String testValue = "Default value";
    @Property
    private boolean formVisible;


    // Handle call with an unwanted context
    Object onActivate(EventContext eventContext) {
        return eventContext.getCount() > 0 ?
                new HttpError(404, "Resource not found") :
                null;
    }

    Object onActionFromLearnMore() {
        about.setLearn("LearnMore");

        return about;
    }

    @Log
    void onComplete() {
        logger.info("Complete call on Index page");
    }

    @Log
    void onAjax() {
        logger.info("Ajax call on Index page");

        ajaxResponseRenderer.addRender("middlezone", block);
    }

    @Log
    void onFormAjax() {
        logger.info("Form call on Index page");
        formVisible = true;

        ajaxResponseRenderer.addRender("formzone", form);
    }

    public ZonedDateTime getCurrentTime() {
        return ZonedDateTime.now();
    }

}
