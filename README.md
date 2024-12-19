# burp-garak
Burpsuite Extension that lets you test LLMs over REST via Garak in the Burp UI.

This version currently only generates the garak.json file from a Burpsuite request and response that can be used with the REST generator in Garak.

## How it works
Uses the Garak REST Generator: https://reference.garak.ai/en/latest/garak.generators.rest.html

## TODO
- Improve UI
- - Intruder-esque selection of relevant JSON field for Garak in request and response
- Run Garak from Burp
- Show Garak output in Burp
