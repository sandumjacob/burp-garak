package com.jacobsandum;

import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import burp.api.montoya.logging.Logging;

import burp.api.montoya.utilities.json.*;

import org.json.*;

import java.io.File;
import java.io.FileWriter;

public class Garak {

    public GarakExtension garakExtension;
    public String garakPath;

    private HttpRequest request;
    private HttpResponse response;

    public Garak(GarakExtension garakExtension) {
        this.garakExtension = garakExtension;
        this.garakPath = garakExtension.savedGarakPathPreference;
        this.request = null;
        this.response = null;
    }

    public void updateGarakRequestResponse(HttpRequest newHttpRequest, HttpResponse newHttpResponse) {
        this.request = newHttpRequest;
        this.response = newHttpResponse;
        this.garakExtension.garakSuiteTab.garakSuiteTabRequestEditor.setRequest(newHttpRequest);
        this.garakExtension.garakSuiteTab.garakSuiteTabResponseEditor.setResponse((newHttpResponse));
    }

    private void generateGarakRestJSON() {
        JSONObject garakJson = new JSONObject();

        JSONObject restgen = new JSONObject();
        restgen.put("name", "Burpsuite Garak Generator");
        restgen.put("uri", request.url());
        restgen.put("method", request.method());
        JSONObject headersJSON = new JSONObject();
        for (int i = 0; i < request.headers().size(); i++) {
            headersJSON.put(request.headers().get(i).name(), request.headers().get(i).value());
        }
        // TODO: JSON validation of text fields
        restgen.put("headers", headersJSON);
        restgen.put("req_template_json_object", new JSONObject(garakExtension.garakSuiteTab.garakRequestJSONTextField.getText()));
        restgen.put("response_json", true);
        restgen.put("response_json_field", garakExtension.garakSuiteTab.garakResponseJSONTextField.getText());

        garakJson.put("rest", restgen);

        // TODO: Path validation
        String exportPath = garakExtension.garak.garakPath + "/garak.json";

        try {
            FileWriter myWriter = new FileWriter(exportPath);
            myWriter.write(garakJson.toString());
            myWriter.close();
        } catch (Exception e) {
            garakExtension.api.logging().logToError(e.getMessage());
        }

        garakExtension.api.logging().logToOutput(garakJson.toString());
        garakExtension.api.logging().logToOutput(String.format("Created Garak JSON file at %s", exportPath));
    }

    public void startScan() {
        garakExtension.api.logging().logToOutput("Exporting Garak for response: ");
        garakExtension.api.logging().logToOutput(request.toString());
        garakExtension.api.logging().logToOutput("Exporting Garak scan for response: ");
        garakExtension.api.logging().logToOutput(response.toString());

        generateGarakRestJSON();
    }

    public void stopScan() {

    }

    public static void main(String[] args) {
        System.out.println("Running Main!");
    }

}

