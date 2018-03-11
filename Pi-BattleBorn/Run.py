#runUI = 'java Test'
#test = "ls -a"
#sendData = 'scp sensor.txt josh@192.168.113.188:/home/josh/BattleBorn'
remote='sethasadi@192.168.113.198:~/Desktop/BattleBorn/BattleBornHackathon'
remote='.'
sendData = "sshpass -p \"chummed159022.analogues\" scp sensor.txt sethasadi@192.168.113.198:~/Desktop/E/BattleBorn/BattleBornHackathon" 
#sendData = "scp sensor.txt josh@192.168.113.188:/home/josh/BattleBorn"
getWorkOrder = "sshpass -p \"chummed159022.analogues\" scp sethasadi@192.168.113.198:~/Desktop/E/BattleBorn/BattleBornHackathon/workOrder.txt ./"
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

notSent=True
try:
    greenState = True

    GPIO.output(GREEN_LED, greenState)
    GPIO.output(YELLOW_LED, yellowState)
    GPIO.output(RED_LED, redState)

    # Waiting for inspector to scan
    for x in range(0, 8):
        t.sleep(1)
   
    yellowState = True
    greenState = False
    GPIO.output(YELLOW_LED, yellowState)
    GPIO.output(GREEN_LED, greenState)
    while notSent:
        mag = GPIO.input(sensorPin)
        if (mag == False):
            #Inspector has signed into machine
            os.system(sendData)
            print("Data sent.") # Open up Java UI on inspector's iPad
            #GPIO.output(GREEN_LED, False) # Disable other LEDs
            #GPIO.output(YELLOW_LED, False)
            #GPIO.output(RED_LED, False)
            notSent=False
        else:
            GPIO.output(BLUE_LED, blueState)
   
    # Check for data from Java
    workOrder=False
    yellowState = True
    while (workOrder == False):
        t.sleep(.5)
        # blink proper light 
        if (greenState):
            GPIO.output(GREEN_LED, False)
            t.sleep(.5)
            GPIO.output(GREEN_LED, True)
        if (yellowState):
            GPIO.output(YELLOW_LED, False)
            t.sleep(.5)
            GPIO.output(YELLOW_LED, True)
        if (redState):
            GPIO.output(RED_LED, False)
            t.sleep(.5)
       
        os.system(getWorkOrder)
        print("Waiting for Work Order...")
        with open('workOrder.txt') as f:
            s = f.readline()
            print(s)
            if (s == 'request\n'): 
                workOrder = True


#        for subdir, dirs, files in os.walk('./'):
 #           for file in files:
  #              # check for file workOrder.txt
   #             if (file == 'workOrder.txt'):
    #                with open('workOrder.txt') as f:
     #                   s = f.read()
      #                  print(s)
       #                 if (s == 'request'):
        #                    workOrder = True


    print("Work order received.")
    #Keep blue light on light
    blueState = True
    GPIO.output(BLUE_LED, blueState)
   
    # Turn lights off, keep status on
    GPIO.output(GREEN_LED, greenState)
    GPIO.output(YELLOW_LED, yellowState)
    GPIO.output(RED_LED, redState)

    notFix=True
    # Waiting for mainetnace to scan in
    while notFix:
        GPIO.output(BLUE_LED, blueState)
        mag = GPIO.input(sensorPin)
        if (mag == False):
            # Open Maintenance UI 

            # Receive input from Maintenance UI, either "Delay" or "Complete"
            complete = False 
            #while (complete == False):
            for x in range(0, 7):
                t.sleep(.5)
                # blink proper light 
                if (greenState):
                    GPIO.output(GREEN_LED, False)
                    t.sleep(.5)
                    GPIO.output(GREEN_LED, True)
                if (yellowState):
                    GPIO.output(YELLOW_LED, False)
                    t.sleep(.5)
                    GPIO.output(YELLOW_LED, True)
                if (redState):
                    GPIO.output(RED_LED, False)
                    t.sleep(.5)
                   
         #       print("Waiting for complete or delay...")
         #       for subdir, dirs, files in os.walk('./'):
         #           for file in files:
          #              # check for file workOrder.txt
           #             if (file == 'complete.txt'):
            #                complete = True
                os.system(getComplete)
                print("Waiting for Maintenance Completion...")
                with open('complete.txt') as f:
                    s = f.readline()
                    print(s)
                    if (s == 'close\n'): 
                        complete = True



            # If Delay:
            # blue stays on

            # If complete
            # Green goes back on (Back to real-time monitoring)
            blueState = False
            GPIO.output(BLUE_LED, False)
            
            #GPIO.output(GREEN_LED, greenStatus)
            #GPIO.output(YELLOW_LED, yellowStatus)
            #GPIO.output(RED_LED, redStatus)

            notFix = False
            #os.system(sendData)
            #print("Data sent.")
            #GPIO.output(GREEN_LED, False)
            #GPIO.output(YELLOW_LED, False)
            #GPIO.output(RED_LED, False)
            #notSent=False
        else:
            GPIO.output(BLUE_LED, True)
        
    GPIO.output(GREEN_LED, True)
    GPIO.output(YELLOW_LED, False)
    GPIO.output(RED_LED, False)
    t.sleep(5)
   #     GPIO.output(BLUE_LED, True)
   #     t.sleep(blinkTime)
   #     GPIO.output(ledPin, False)
   #     t.sleep(blinkTime)

except(KeyboardInterrupt, SystemExit):
        print("Excepted interrupt")
        GPIO.output(BLUE_LED, False)
        GPIO.output(GREEN_LED, False)
        GPIO.output(YELLOW_LED, False)
        GPIO.output(RED_LED, False)
        exit
finally:
        GPIO.cleanup()

#process = subprocess.Popen(runUI.split(), stdout=subprocess.PIPE)
#output, error = process.communicate()

