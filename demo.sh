#bash src/run.sh
javaUI='DashboardUI'

rm sensor.txt
# rm complete.txt
# rm delay.txt
rm workOrder.txt
touch workOrder.txt

close=true
while ${close}; do
	echo "Searching for data..."
	if [ -a "sensor.txt" ]
	then
		echo "Data received, running UI."
		close=false 
	fi	
	sleep 2
done

javac ${javaUI}.java
java ${javaUI}

