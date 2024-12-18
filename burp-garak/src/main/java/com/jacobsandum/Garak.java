package com.jacobsandum;

import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.logging.Logging;
import org.json.*;
public class Garak {

    public GarakExtension garakExtension;
    public String garakPath;

    private HttpRequest request;

    public Garak(GarakExtension garakExtension) {
        this.garakExtension = garakExtension;
        this.garakPath = garakExtension.savedGarakPathPreference;
        this.request = null;
    }

    public void updateGarakRequestResponse(HttpRequest newRequest) {
        this.request = newRequest;
        this.garakExtension.garakSuiteTab.garakSuiteTabRequestEditor.setRequest(newRequest);
    }

    private void generateGarakRestJSON() {
        //{
        //   "rest": {
        //      "RestGenerator": {
        //         "name": "example service",
        //         "uri": "https://example.ai/llm",
        //         "method": "post",
        //         "headers": {
        //            "X-Authorization": "$KEY"
        //         },
        //         "req_template_json_object": {
        //            "text": "$INPUT"
        //         },
        //         "response_json": true,
        //         "response_json_field": "text"
        //      }
        //   }
        //}

        JSONObject garakJson = new JSONObject("{\n" +
            "   \"rest\": {\n" +
            "      \"RestGenerator\": {\n" +
            "         \"req_template_json_object\": {\n" +
            "            \"text\": \"$INPUT\"\n" +
            "         },\n" +
            "         \"response_json\": true,\n" +
            "         \"response_json_field\": \"text\"\n" +
            "      }\n" +
            "   }\n" +
            "}");

    }

    public void startScan() {
        garakExtension.api.logging().logToOutput("Starting Garak scan for request: ");
        garakExtension.api.logging().logToOutput(request.toString());


        generateGarakRestJSON();

    }

    public void stopScan() {

    }

}

