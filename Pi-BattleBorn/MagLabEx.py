#Author: Seth Jacob Asadi
#Description: Have some fun with magnetic field sensors and creating
# a spedometer of sorts
#Date: 6/3/18

import RPi.GPIO as GPIO
import numpy as np
import time as t

sensorPin = 25
ledPin = 22

GPIO.setmode(GPIO.BCM)

GPIO.setup(sensorPin, GPIO.IN, GPIO.PUD_UP)
GPIO.setup(ledPin, GPIO.OUT)

try:
    while True:
        mag = GPIO.input(sensorPin)
        if (mag == False):
            GPIO.output(ledPin, True)
            #t.sleep(.5)
            #GPIO.output(ledPin, False)
        else:
            GPIO.output(ledPin, False)
except(KeyboardInterrupt, SystemExit):
    print("Excepted interrupt")
    GPIO.output(ledPin, False)
    exit
finally:
    GPIO.cleanup()
