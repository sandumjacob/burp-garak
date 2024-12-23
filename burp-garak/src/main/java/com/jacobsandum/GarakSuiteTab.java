package com.jacobsandum;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import burp.api.montoya.burpsuite.BurpSuite;
import burp.api.montoya.collaborator.Collaborator;
import burp.api.montoya.comparer.Comparer;
import burp.api.montoya.decoder.Decoder;
import burp.api.montoya.extension.Extension;
import burp.api.montoya.http.Http;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.intruder.Intruder;
import burp.api.montoya.logging.Logging;
import burp.api.montoya.organizer.Organizer;
import burp.api.montoya.persistence.Persistence;
import burp.api.montoya.project.Project;
import burp.api.montoya.proxy.Proxy;
import burp.api.montoya.repeater.Repeater;
import burp.api.montoya.scanner.Scanner;
import burp.api.montoya.scope.Scope;
import burp.api.montoya.sitemap.SiteMap;
import burp.api.montoya.ui.Selection;
import burp.api.montoya.ui.UserInterface;
import burp.api.montoya.ui.editor.Editor;
import burp.api.montoya.ui.editor.HttpRequestEditor;
import burp.api.montoya.ui.editor.HttpResponseEditor;
import burp.api.montoya.utilities.Utilities;
import burp.api.montoya.websocket.WebSockets;

import burp.api.montoya.utilities.json.*;

public class GarakSuiteTab extends JComponent
{

    private final MontoyaApi api;

    public HttpRequestEditor garakSuiteTabRequestEditor;
    public HttpResponseEditor garakSuiteTabResponseEditor;
    public JTextField garakRequestJSONTextField;
    public JTextField garakResponseJSONTextField;

    public GarakSuiteTab(MontoyaApi api, Garak garak)
    {
        this.api = api;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));


        // JTabbedPane tabPanel = new JTabbedPane();

        // Options UI
        JPanel optionsContent = new JPanel();
        optionsContent.setName("The One Ring Custom Tab Panel");
        optionsContent.setBackground(Color.WHITE);

        JTextField garakPathTextField = new JTextField(32);

        if (garak.garakPath == null || garak.garakPath.isEmpty()) {
            garakPathTextField.setText("Enter Path for Output JSON");
        } else {
            garakPathTextField.setText(garak.garakPath);
        }
        optionsContent.add(garakPathTextField);

        JButton saveGarakPathButton = new JButton("Save");
        saveGarakPathButton.addActionListener(e -> {
            this.api.logging().logToOutput("Saving Garak Path: " + garakPathTextField.getText());
            this.api.persistence().preferences().setString(GarakExtension.garakPathKey, garakPathTextField.getText());
            garak.garakPath = garakPathTextField.getText();
        });

        optionsContent.add(saveGarakPathButton);


        // Editor Controls UI
        JPanel editorControls = new JPanel();

        HttpRequestEditor garakRequestEditor  = api.userInterface().createHttpRequestEditor();
        garakSuiteTabRequestEditor = garakRequestEditor;

        HttpResponseEditor garakResponseEditor  = api.userInterface().createHttpResponseEditor();
        garakSuiteTabResponseEditor = garakResponseEditor;
        // editorContents.add(garakSuiteTabRequestEditor.uiComponent());

        garakRequestJSONTextField = new JTextField(32);
        garakRequestJSONTextField.setText("Request JSON");
        editorControls.add(garakRequestJSONTextField);

        garakResponseJSONTextField = new JTextField(32);
        garakResponseJSONTextField.setText("Response JSON");
        editorControls.add(garakResponseJSONTextField);

        JButton startGarakButton = new JButton("Export Garak JSON");
        startGarakButton.addActionListener(e -> {
            garak.updateGarakRequestResponse(garakRequestEditor.getRequest(), garakResponseEditor.getResponse());
            garak.startScan();
        });
        editorControls.add(startGarakButton);

        JButton stopGarakButton = new JButton("Stop Garak");
        stopGarakButton.addActionListener(e -> {
            garak.stopScan();
        });
        // editorControls.add(stopGarakButton);

        add(optionsContent);
        add(editorControls);
        add(garakSuiteTabRequestEditor.uiComponent());
        add(garakSuiteTabResponseEditor.uiComponent());


    }

}

