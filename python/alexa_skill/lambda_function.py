# Copyright (c) 2020 Jean-Sebastien Gelinas, see LICENSE at the root of the repository

import json


def send_response(success, result):
    response = {
        'version': '1.0',
        'sessionAttributes': {},
        'shouldEndSession': True,
        'response': {
            'outputSpeech' : {
                'type': 'PlainText',
                'text': result,
                'playBehavior': 'REPLACE_ENQUEUED',
            },
        },
    }

    if success:
        response['response']['card'] = {
            'type': 'Simple',
            'title': 'Success',
            'content': result,
        }

    print('Response ***************')
    print(json.dumps(response))
    return response


def lambda_handler(request, context):
    print('Request ***************')
    print(json.dumps(request))

    if context is not None:
        print('Context ***************')
        print(context)

    if 'request' not in request:
        return send_response(False, 'Bad configuration')

    intent = request['request']['intent']['name']
    print(f"Intent: '{intent}'")

    for name, slot in request['request']['intent']['slots'].items():
        value = slot['slotValue']['value']
        print(f"Slot '{name}': '{value}'")

    return send_response(True, f"Alright, I'll run {value}")
