import  apiai
import json
def main():
    ai = apiai.ApiAI('cd4fbf5bf0bb489784266935eb751421')
    while True:
        print(u"> ", end=u"")
        user_message = input()

        if user_message == u"exit":
            break

        request = ai.text_request()
        request.query = user_message

        response = json.loads(request.getresponse().read())

        result = response['result']
        action = result['action']
        actionIncomplete = result.get('actionIncomplete', False)

        print(u"< %s" % response['result']['fulfillment']['speech'])

        if action is not None:
            if action == u"send_message":
                parameters = result['parameters']

                text = parameters.get('text')
                message_type = parameters.get('message_type')
                parent = parameters.get('parent')

                print (
                    'text: %s, message_type: %s, parent: %s' %
                    (
                        text if text else "null",
                        message_type if message_type else "null",
                        parent if parent else "null"
                    )
                )

                if not actionIncomplete:
                    print(u"...Sending Message...")
                    break
            #print("Class Name: "+response['result']['metadata']['intentName'])
            if (response['result']['metadata']['intentName'] == 'make reservation'):
                number = response['result']['parameters']['number']
                date = response['result']['parameters']['date']
                time = response['result']['parameters']['time']
                if(number != '' and date !='' and time !=''):
                    print("Class Name: "+response['result']['metadata']['intentName'])
                    print("Number: "+response['result']['parameters']['number'])
                    print("Date: "+response['result']['parameters']['date'])
                    print("Time: "+response['result']['parameters']['time'])

main()

