#!/usr/bin python

from gpiozero import Button
import time

button = Button(4)

while True:
    if button.is_pressed:
        print("Raining")
    else:
        print("Not raining")

    time.sleep(5)