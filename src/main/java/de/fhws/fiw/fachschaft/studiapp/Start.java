package de.fhws.fiw.fachschaft.studiapp;

import de.fhws.fiw.sutton.AbstractStart;

public class Start extends AbstractStart {
    public static final String CONTEXT_PATH = "studi-app";

    public static void main(final String[] args) throws Exception {
        new Start().startTomcat();
    }

    @Override
    protected String contextPath()
    {
        return CONTEXT_PATH;
    }
}
