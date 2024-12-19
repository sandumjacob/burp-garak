package com.jacobsandum;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.ToolType;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.ui.contextmenu.ContextMenuEvent;
import burp.api.montoya.ui.contextmenu.ContextMenuItemsProvider;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GarakContextMenuItemsProvider implements ContextMenuItemsProvider {

    private final MontoyaApi api;
    private Garak garak;
    private GarakSuiteTab garakSuiteTab;

    public GarakContextMenuItemsProvider(MontoyaApi api, Garak garak)
    {
        this.api = api;
        this.garak = garak;
    }

    @Override
    public List<Component> provideMenuItems(ContextMenuEvent event)
    {
        if (event.isFromTool(ToolType.PROXY, ToolType.REPEATER, ToolType.INTRUDER))
        {
            List<Component> menuItemList = new ArrayList<>();

            JMenuItem retrieveRequestItem = new JMenuItem("Send Request to Garak");

            HttpRequestResponse requestResponse = event.messageEditorRequestResponse().isPresent() ? event.messageEditorRequestResponse().get().requestResponse() : event.selectedRequestResponses().get(0);

            retrieveRequestItem.addActionListener(l -> {
                api.logging().logToOutput("Sending request to Garak:\r\n" + requestResponse.request().toString());
                garak.updateGarakRequestResponse(requestResponse.request(), requestResponse.response());

            });
            menuItemList.add(retrieveRequestItem);

            return menuItemList;
        }

        return null;
    }



}
