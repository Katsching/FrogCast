#!/usr/bin/env python
import pika
import sys
import json


# builds message as a JSON object
def build_message(temperature, humidity, rain):

    # create the message as dictionary object
    message_dictionary = {'temperature': temperature, 'humidity': humidity, 'rain': rain}

    # convert message from dictionary to JSON object
    message_json = json.dumps(message_dictionary)

    # return converted JSON object
    return message_json


connection = pika.BlockingConnection(
    pika.ConnectionParameters(host='localhost'))
channel = connection.channel()

channel.exchange_declare(exchange='sensor data', exchange_type='fanout')

#message = ' '.join(sys.argv[1:]) or "info: Hello World!"

# fill attributes with arbitrary testing values
temperature = 20.2
humidity = 50.3
rain = False

# build the message as JSON object
message = build_message(temperature, humidity, rain)

print(message)

channel.basic_publish(exchange='sensor data', routing_key='', body=message)
print(" [x] Sent %r" % message)
connection.close()
