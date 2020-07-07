#!/usr/bin/env python
import pika
import json
from gpiozero import LED, RGBLED
import time


white = (1, 1, 1)
blue = (0, 0, 1)
lightblue = (0, 1, 1)
magenta = (1, 0, 0.5)
nocolor = (0, 0, 0)
top = RGBLED(16, 20, 21)
mid = RGBLED(6, 5, 0)
bot = RGBLED(11, 9, 10)
last_color = "white"
last_position = "top"


connection = pika.BlockingConnection(
    pika.ConnectionParameters(host='localhost'))
channel = connection.channel()
channel.exchange_declare(exchange='sensor data', exchange_type='fanout')
result = channel.queue_declare(queue='', exclusive=True)
queue_name = result.method.queue
channel.queue_bind(exchange='sensor data', queue=queue_name)


print(' [*] Waiting for logs. To exit press CTRL+C')


def callback(ch, method, properties, body):
#   print(" [x] %r" % body)
    message_data = json.loads(body)
    global last_color
    global last_position
    if "wake" in message_data:
        print("I woke up")
        eval(last_position).color = eval(last_color)
        time.sleep(20)
        eval(last_position).color = (0, 0, 0)
    else:
        print("received weather information")
        position = message_data['position']
        color = message_data['color']

        last_color = color
        last_position = position

        temperature = message_data['temperature']
        humidity = message_data['humidity']
        eval(position).color = eval(color)
        time.sleep(1)
        eval(position).color = (0, 0, 0)


channel.basic_consume(
    queue=queue_name, on_message_callback=callback, auto_ack=True)

channel.start_consuming()
