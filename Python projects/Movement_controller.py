#!/usr/bin/python3
import RPi.GPIO as GPIO
import time
import pika
import json
import datetime
import sys
SENSOR_PIN = 26
GPIO.setmode(GPIO.BCM)
GPIO.setup(SENSOR_PIN, GPIO.IN)

start_time = time.monotonic()-20


def movement(channel):
    global start_time
    connection = pika.BlockingConnection(
        pika.ConnectionParameters(host='localhost'))
    channel = connection.channel()

    channel.exchange_declare(exchange='sensor data', exchange_type='fanout')
    message_dictionary = {"wake": "wake up"}
    message_json = json.dumps(message_dictionary)

    if time.monotonic() - start_time > 20:
        channel.basic_publish(exchange='sensor data', routing_key='', body=message_json)
        start_time = time.monotonic()
        print(" [x] Sent %r" % message_json)
    connection.close()


try:
    GPIO.add_event_detect(SENSOR_PIN, GPIO.RISING, callback = movement)
    while True:
        time.sleep(1)
except KeyboardInterrupt: 
    print("Program terminated")


GPIO.cleanup()