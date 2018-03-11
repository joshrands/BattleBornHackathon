sendData = "sshpass -p \"chummed159022.analogues\" scp install.txt sethasadi@192.168.113.198:~/Desktop/E/BattleBorn/BattleBornHackathon" 
#sendData = "scp sensor.txt josh@192.168.113.188:/home/josh/BattleBorn"
getInstall = "sshpass -p \"chummed159022.analogues\" scp sethasadi@192.168.113.198:~/Desktop/E/BattleBorn/BattleBornHackathon/oracle.txt ./"
#getWorkOrder = "scp josh@192.168.113.198:/home/josh/BattleBorn/workOrder.txt"
getComplete = "sshpass -p \"chummed159022.analogues\" scp sethasadi@192.168.113.198:~/Desktop/E/BattleBorn/BattleBornHackathon/complete.txt ./"

import subprocess
import os

import RPi.GPIO as GPIO
import numpy as np
import time as t

BLUE_LED=18
GREEN_LED=12
YELLOW_LED=16
RED_LED=22

blueState=False
greenState=False
yellowState=False
redState=False

sensorPin = 25
blinkTime = 1

GPIO.setmode(GPIO.BCM)

GPIO.setup(sensorPin, GPIO.IN, GPIO.PUD_UP)
GPIO.setup(BLUE_LED, GPIO.OUT)
GPIO.setup(GREEN_LED, GPIO.OUT)
GPIO.setup(YELLOW_LED, GPIO.OUT)
GPIO.setup(RED_LED, GPIO.OUT)

# Clean up for demo

install = False
try:
    greenState = True
    yellowState = True
    redState = True
    GPIO.output(GREEN_LED, greenState)
    GPIO.output(YELLOW_LED, yellowState)
    GPIO.output(RED_LED, redState)
    t.sleep(3)
    greenState = False
    GPIO.output(GREEN_LED, greenState)
    t.sleep(1)
    yellowState = False
    GPIO.output(YELLOW_LED, yellowState)
    t.sleep(1)

    # RED led on, waiting for scan...
    scan = False
    while (scan == False):
        mag = GPIO.input(sensorPin)
        t.sleep(.1)
        if (mag == False):
            os.system(sendData)
            print("New RFiD scanned.") # Open up Java UI on inspector's iPad
            scan = True
            yellowState = True
            redState = False
            GPIO.output(YELLOW_LED, yellowState)
            GPIO.output(RED_LED, redState)

        else:
            GPIO.output(RED_LED, redState)
   
    # Check for data from Java
    installed = False
    yellowState = True
    while (installed == False):
        os.system(getInstall)
        t.sleep(1)
        print("Waiting for Asset iD...")
        with open('oracle.txt') as f:
            s = f.readline()
            print(s)
            if (s == 'installed\n'): 
                installed = True

    print("Install complete.")
    yellowState = False
    greenState = True
    
    GPIO.output(YELLOW_LED, yellowState)
    GPIO.output(GREEN_LED, greenState)
    while True:
        t.sleep(.1)


except(KeyboardInterrupt, SystemExit):
        print("Excepted interrupt")
        GPIO.output(BLUE_LED, False)
        GPIO.output(GREEN_LED, True) # KEEP THIS GREEN FOR NEXT DEMO
        GPIO.output(YELLOW_LED, False)
        GPIO.output(RED_LED, False)
        exit
finally:
        GPIO.cleanup()

#process = subprocess.Popen(runUI.split(), stdout=subprocess.PIPE)
#output, error = process.communicate()

