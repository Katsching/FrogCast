#!/usr/bin/env python
import pika
import sys
import json
from gpiozero import Button
import time
import Adafruit_DHT

rain_sensor_gpio = 1
temperature_humidity_sensor = Adafruit_DHT.DHT22
# DHT22 sensor connected to GPIO12.
temperature_humidity_gpio = 12


# builds message as a JSON object
def build_message(temperature, humidity, rain):

    # create the message as dictionary object
    message_dictionary = {'temperature': temperature, 'humidity': humidity, 'rain': rain}

    # convert message from dictionary to JSON object
    message_json = json.dumps(message_dictionary)

    # return converted JSON object
    return message_json


def get_rain_sensor_data():
    button = Button(rain_sensor_gpio)
    if button.is_pressed:
        return True
    else:
        return False


def get_temperature_humidity_data():
    humidity, temperature = Adafruit_DHT.read_retry(temperature_humidity_sensor, temperature_humidity_gpio)
    return humidity, temperature


credentials = pika.PlainCredentials('rasp', '1234')
connection = pika.BlockingConnection(
    pika.ConnectionParameters('192.168.178.30', 5672, '/', credentials))
channel = connection.channel()

channel.exchange_declare(exchange='sensor_data', exchange_type='fanout')

# fill attributes with arbitrary testing values
humidity, temperature = get_temperature_humidity_data()
rain = get_rain_sensor_data()

# build the message as JSON object
message = build_message(temperature, humidity, rain)

print(message)

channel.basic_publish(exchange='sensor_data', routing_key='', body=message)
print(" [x] Sent %r" % message)
connection.close()
