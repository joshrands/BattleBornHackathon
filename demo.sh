#bash src/run.sh

while true; do
	echo "Searching for data..."
	if [ -a "sensor.txt" ]
	then
		echo "Data received, running UI."
	fi	
	sleep 2
done

