runUI = 'java Test'
test = "ls -a"

import subprocess
import os

print("Hello from Python!")

#process = subprocess.Popen(runUI.split(), stdout=subprocess.PIPE)
#output, error = process.communicate()
os.system(runUI)

print("Java called.")

