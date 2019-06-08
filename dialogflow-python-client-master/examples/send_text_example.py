
import os.path
import sys
import json
import requests

try:
    import apiai
except ImportError:
    sys.path.append(
        os.path.join(os.path.dirname(os.path.realpath(__file__)), os.pardir)
    )
    import apiai

CLIENT_ACCESS_TOKEN = 'cd4fbf5bf0bb489784266935eb751421'


def main():
    ai = apiai.ApiAI(CLIENT_ACCESS_TOKEN)
    request = ai.text_request()
    request.lang = 'de'  # optional, default value equal 'en'
    request.session_id = "<SESSION ID, UNIQUE FOR EACH USER>"
    request.query = input("How can I help you?")
    response = request.getresponse()
    responsee = json.loads(response.read())
    result = responsee["result"]
    intentName = result["metadata"]
    intentName2 = intentName["intentName"]
    print("Classification")
    print("Class Name: "+intentName2)
    if intentName2 == 'Complains':
        comp = input("what's your complain? ")
        print("the complain: " + comp)
        print("Thank you")
    if intentName2 == 'make reservation':
        parameters = result["parameters"]
        if parameters["number"] =='':
            parameters["number"] = input("For how many person ")
        if parameters["time"] =='':
            parameters["time"] = input("when ")
        if parameters["date"] =='':
            parameters["date"] = input("the date ")
        print("number: "+parameters["number"]+"\ntime: "+parameters["time"]+"\ndate: "+parameters["date"])



if __name__ == '__main__':
    main()
