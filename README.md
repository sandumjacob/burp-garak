# burp-garak
Burpsuite Extension that lets you test LLMs with the Garak REST generator using the Burp UI

This version currently only generates the garak.json file from a Burpsuite request and response that can be used with the REST generator in Garak.

## How it works
Uses the Burpsuite Montoya API to extend Burpsuite.

Uses the Garak REST Generator: https://reference.garak.ai/en/latest/garak.generators.rest.html

## Usage
Load the extension into Burpsuite with the built JAR file. You also need to add org.json JAR file to Burpsuite.

In intercepted requests and repeater, there will be a context menu under extensions.
You can use this context menu to send request-responses to the Garak extension tab in Burpsuite.

![Screenshot_20241223_095406](https://github.com/user-attachments/assets/748c658a-d0e9-4f94-98e7-6583dd1da6df)


In the Garak extension tab, you can then export a garak.json file using the request-response you added.
![image](https://github.com/user-attachments/assets/d9c9f90c-f9c7-4325-b6f2-8868036a865e)



### Dependencies
org.json:json

## TODO
- Improve UI 
  - Intruder-esque selection of relevant JSON field for Garak in request and response
- Run Garak from Burp
- Show Garak output in Burp
- Add Maven tests
- Publish to Burp extensions

## Building
The Java project is in
```
burp-garak
burp-garak/pom.xml
```

Used JDK 22

