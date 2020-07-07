#!/usr/bin/env python
import pika
import json
from gpiozero import LED, RGBLED
import time


white = (1,1,1)
blue = (0,0,1)
lightBlue = (0,1,1)
magenta = (1,0,0.5)
top = RGBLED(16, 20, 21)
mid = RGBLED(6,5,0)
bot = RGBLED(11,9,10)


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
    weather_data = json.loads(body)
    position = weather_data['position']
    color = weather_data['color']
    temperature = weather_data['temperature']
    humidity = weather_data['humidity']
    eval(position).color = eval(color)

channel.basic_consume(
    queue=queue_name, on_message_callback=callback, auto_ack=True)

channel.start_consuming()
