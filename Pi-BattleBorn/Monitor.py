# Monitor.py for monitoring real-time data predictive analytics

import RPi.GPIO as GPIO
import numpy as np
import time as t

RED_LED=22
GREEN_LED=12
YELLOW_LED=16
BLUE_LED=18

sleepTime = 1

GPIO.setmode(GPIO.BCM)
GPIO.setup(BLUE_LED, GPIO.OUT)
GPIO.setup(GREEN_LED, GPIO.OUT)
GPIO.setup(YELLOW_LED, GPIO.OUT)
GPIO.setup(RED_LED, GPIO.OUT)

#GPIO.output(BLUE_LED, True)
GPIO.output(GREEN_LED, False)
GPIO.output(YELLOW_LED, False)
GPIO.output(RED_LED, False)
GPIO.output(BLUE_LED, False)
 
try:
    for x in range(0, 5):
        print("Data within range.")
        GPIO.output(GREEN_LED, True)
        # monitor LEDS
        t.sleep(sleepTime)

    print("Data anomaly detected")
    GPIO.output(GREEN_LED, False)

    for x in range(0, 10):
        GPIO.output(YELLOW_LED, True)
        t.sleep(sleepTime)
        GPIO.output(YELLOW_LED, False)
        t.sleep(sleepTime)

    GPIO.output(RED_LED, True)
    while True:
        GPIO.output(RED_LED, True)
        t.sleep(sleepTime/4)
        GPIO.output(RED_LED, False)
        t.sleep(sleepTime/4)

except(KeyboardInterrupt, SystemExit):
    print("Excepted interrupt")
    GPIO.output(GREEN_LED, False)
    GPIO.output(YELLOW_LED, False)
    GPIO.output(RED_LED, False)
    GPIO.output(BLUE_LED, False)
    exit
finally:
    GPIO.cleanup()
            
