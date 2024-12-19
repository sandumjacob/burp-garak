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
            garakPathTextField.setText("Enter Path to Export JSON");
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

        HttpResponseEditor garakResponseEditor  = api.userInterface().createHttpResponseEditor();
        garakSuiteTabResponseEditor = garakResponseEditor;
        // editorContents.add(garakSuiteTabRequestEditor.uiComponent());

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

    public static void main (String[] args) {
        System.out.println("Running UI");

        JFrame frame = new JFrame();
        MontoyaApi api = new MontoyaApi() {
            @Override
            public BurpSuite burpSuite() {
                return null;
            }

            @Override
            public Collaborator collaborator() {
                return null;
            }

            @Override
            public Comparer comparer() {
                return null;
            }

            @Override
            public Decoder decoder() {
                return null;
            }

            @Override
            public Extension extension() {
                return null;
            }

            @Override
            public Http http() {
                return null;
            }

            @Override
            public Intruder intruder() {
                return null;
            }

            @Override
            public Logging logging() {
                return null;
            }

            @Override
            public Organizer organizer() {
                return null;
            }

            @Override
            public Persistence persistence() {
                return null;
            }

            @Override
            public Project project() {
                return null;
            }

            @Override
            public Proxy proxy() {
                return null;
            }

            @Override
            public Repeater repeater() {
                return null;
            }

            @Override
            public Scanner scanner() {
                return null;
            }

            @Override
            public Scope scope() {
                return null;
            }

            @Override
            public SiteMap siteMap() {
                return null;
            }

            @Override
            public UserInterface userInterface() {
                return null;
            }

            @Override
            public Utilities utilities() {
                return null;
            }

            @Override
            public WebSockets websockets() {
                return null;
            }
        };
        GarakExtension placeholderGarakExtension = new GarakExtension();
        placeholderGarakExtension.savedGarakPathPreference = "/usr/share";
        GarakSuiteTab garakSuiteTab = new GarakSuiteTab(api, new Garak(placeholderGarakExtension));
        frame.add(garakSuiteTab);

        // 400 width and 500 height
        frame.setSize(500, 600);

        // using no layout managers
        frame.setLayout(null);

        // making the frame visible
        frame.setVisible(true);
    }
}

