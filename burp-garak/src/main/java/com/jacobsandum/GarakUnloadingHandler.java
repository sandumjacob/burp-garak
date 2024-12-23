package com.jacobsandum;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.extension.ExtensionUnloadingHandler;

public class GarakUnloadingHandler implements ExtensionUnloadingHandler
{
    private final MontoyaApi api;

    public GarakUnloadingHandler(MontoyaApi api)
    {
        this.api = api;
    }

    @Override
    public void extensionUnloaded()
    {
        api.logging().logToOutput("Burpsuite Garak extension has been unloaded.");
    }
}
