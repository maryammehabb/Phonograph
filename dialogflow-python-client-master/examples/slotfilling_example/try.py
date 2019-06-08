import apiai
import json

ai = apiai.ApiAI('cd4fbf5bf0bb489784266935eb751421')
while True:
    print(u"> ", end=u"")
    user_message = input()
    if user_message == u"exit":
        break

    request = ai.text_request()
    request.query = user_message

    response = json.loads(request.getresponse().read().decode('utf-8'))

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


        print("Class Name: "+response['result']['metadata']['intentName'])
        # class make reservation
        if (response['result']['metadata']['intentName'] == 'make reservation'):
            number = [response['result']['parameters']['number']]
            date = [response['result']['parameters']['date']]
            time = [response['result']['parameters']['time']]
            if(len(number)!=1 and len(date) != 1 and len(time) != 1):
                for x in range(len(number)):
                    print(number[x])
                for x in range(len(date)):
                    print(date[x])
                for x in range(len(time)):
                    print(time[x])
        # class edit reservation
        # class delete reservation


        #MAARIAM EHAB
        #Make an order
        if (response['result']['metadata']['intentName'] == 'make order'):
            Ordername = [response['result']['parameters']['Ordername']]
            number = [response['result']['parameters']['number']]
            if(len(number)!=1 and len(Ordername) != 1):
                for x in range(len(Ordername)):
                    print(Ordername[x])
                for x in range(len(number)):
                    print(number[x])

        #Edit order
        if(response['result']['metadata']['intentName'] == 'edit order'):
            Ordername = [response['result']['parameters']['Ordername']]
            number = [response['result']['parameters']['number']]
            OrderID = [response['result']['parameters']['number']]
            if (len(number) != 1 and len(Ordername) != 1 and len(OrderID)!= 1):
                for x in range(len(OrderID)):
                    print(OrderID[x])
                for x in range(len(Ordername)):
                    print(Ordername[x])
                for x in range(len(number)):
                    print(number[x])

        #Cancel order
        if (response['result']['metadata']['intentName'] == 'cancel order'):
            OrderID = [response['result']['parameters']['number']]
            if (len(OrderID) != 1):
                for x in range(len(OrderID)):
                    print(OrderID[x])