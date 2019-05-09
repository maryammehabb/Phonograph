import  apiai
import json
# 'sessionId': 'b4146bdd67464cd098529af976f3a3d9'
ai = apiai.ApiAI('cd4fbf5bf0bb489784266935eb751421')
while True:
    print(u"> ", end=u"")
    user_message = input()
    if user_message == u"exit":
        break

    request = ai.text_request()
    request.query = user_message

    response = json.loads(request.getresponse().read())
    print(response)
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
        # class make reservation
        if (response['result']['metadata']['intentName'] == 'make reservation'):
            number = [response['result']['parameters']['number']]
            date = [response['result']['parameters']['date']]
            time = [response['result']['parameters']['time']]
            if(len(number)!=1 and len(date) != 1 and len(time) != 1):
                print("Class Name: "+response['result']['metadata']['intentName'])
                print("Number of persons: ")
                for x in range(len(number)):
                    print(number[x])
                print("The date: ")
                for x in range(len(date)):
                    print(date[x])
                print("Time: ")
                for x in range(len(time)):
                    print(time[x])

        # class edit reservation
        if (response['result']['metadata']['intentName'] == 'edit reservation'):
            number = [response['result']['parameters']['number']]
            date = [response['result']['parameters']['date']]
            time = [response['result']['parameters']['time']]
            id = response['result']['parameters']['id']
            if(len(number)!=1 and len(date) != 1 and len(time) != 1 and id != ''):
                print("Class Name: "+response['result']['metadata']['intentName'])
                print("ID: "+id)
                print("The Updated number of persons: ")
                for x in range(len(number)):
                    print(number[x])
                print("The Updated date: ")
                for x in range(len(date)):
                    print(date[x])
                print("The Updated time: ")
                for x in range(len(time)):
                    print(time[x])

        # class delete reservation
        if (response['result']['metadata']['intentName'] == 'delete reservation'):
            id = response['result']['parameters']['id']
            if(id != ''):
                print("Class Name: "+response['result']['metadata']['intentName'])
                print("ID: "+id)
