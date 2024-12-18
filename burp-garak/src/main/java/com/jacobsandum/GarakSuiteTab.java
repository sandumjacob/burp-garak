package com.jacobsandum;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.intruder.Intruder;
import burp.api.montoya.ui.Selection;
import burp.api.montoya.ui.editor.Editor;
import burp.api.montoya.ui.editor.HttpRequestEditor;

public class GarakSuiteTab extends JComponent
{

    private final MontoyaApi api;

    public HttpRequestEditor garakSuiteTabRequestEditor;



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

        if (garak.garakPath.isEmpty()) {
            garakPathTextField.setText("Enter Path to Garak");
        } else {
            garakPathTextField.setText(garak.garakPath);
        }
        optionsContent.add(garakPathTextField);

        JTextField garakOutputTextField = new JTextField(32);
        optionsContent.add(garakOutputTextField);

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
        // editorContents.add(garakSuiteTabRequestEditor.uiComponent());

        JButton startGarakButton = new JButton("Start Garak");
        startGarakButton.addActionListener(e -> {
            garak.updateGarakRequestResponse(garakRequestEditor.getRequest());
            garak.startScan();
        });
        editorControls.add(startGarakButton);

        JButton stopGarakButton = new JButton("Stop Garak");
        stopGarakButton.addActionListener(e -> {
            garak.stopScan();
        });
        editorControls.add(stopGarakButton);

        add(optionsContent);
        add(editorControls);
        add(garakSuiteTabRequestEditor.uiComponent());


    }
}

