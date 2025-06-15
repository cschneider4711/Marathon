#!/bin/bash

# setup and initialize ZAP
curl -s "http://localhost:7777/JSON/context/action/newContext/?zapapiformat=JSON&contextName=marathon"
curl -s "http://localhost:7777/JSON/context/action/includeInContext/?zapapiformat=JSON&contextName=marathon&regex=http://localhost:9090/.*"
curl -s "http://localhost:7777/JSON/core/action/setMode/?zapapiformat=JSON&mode=attack"

# run test automation
curl -s "http://localhost:7777/JSON/script/view/listEngines/?zapapiformat=JSON"
curl -s "http://localhost:7777/JSON/script/action/load/?zapapiformat=JSON&scriptName=Marathon.zst&scriptType=standalone&scriptEngine=Mozilla%20Zest&fileName=$(pwd)/../zest/Marathon.zst&scriptDescription=&charset="
#curl -s "http://localhost:7777/JSON/script/view/listScripts/?zapapiformat=JSON"
curl -s "http://localhost:7777/JSON/script/action/runStandAloneScript/?zapapiformat=JSON&scriptName=Marathon.zst" # keep aware that body assertions should be emptied in the Zest script to avoid errors about illegal characters etc...

echo "" && echo "Running ZAP guided scan..."

# check if scan has finished
for ((i=0; i<30; i++)); do
  sleep 20
  ATTACK_MODE_QUEUE=$(curl -s "http://localhost:7777/JSON/ascan/view/attackModeQueue/?zapapiformat=JSON")
  echo "Checking queue size if active scan has finished: $ATTACK_MODE_QUEUE"
  if [ "$ATTACK_MODE_QUEUE" == '{"attackModeQueue":"-1"}' ]; then
    echo "Active scan has finished"
    curl -s "http://localhost:7777/JSON/core/view/alertsSummary/?zapapiformat=JSON&baseurl="
    break
  fi
done


# list template types
curl -s "http://localhost:7777/JSON/reports/view/templates/?zapapiformat=JSON"

# generate report: html
curl -s "http://localhost:7777/JSON/reports/action/generate/?title=Marathon+Guided+Scan&template=modern&theme=construction&description=Guided+Scan+of+Marathon&contexts=&sites=&sections=&includedConfidences=&includedRisks=&reportFileName=report.html&reportFileNamePattern=&reportDir=/tmp&display="

# generate report: sarif
#curl -s "http://localhost:7777/JSON/reports/action/generate/?title=Marathon+Guided+Scan&template=sarif-json&theme=&description=Guided+Scan+of+Marathon&contexts=&sites=&sections=&includedConfidences=&includedRisks=&reportFileName=report.json&reportFileNamePattern=&reportDir=/tmp&display="



# debug logs
#find /home/runner/.ZAP
#cat /home/runner/.ZAP/zap.log
