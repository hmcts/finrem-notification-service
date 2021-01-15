#!/usr/bin/env bash
echo ${TEST_URL}
export LC_ALL=C.UTF-8
export LANG=C.UTF-8
zap-api-scan.py -t ${TEST_URL}/v2/api-docs -f openapi -S -d -u ${SecurityRules} -P 1001
cat zap.out
curl --fail http://0.0.0.0:1001/OTHER/core/other/jsonreport/?formMethod=GET --output report.json
zap-cli --zap-url http://0.0.0.0 -p 1001 alerts -l Medium report -o /zap/api-report.html -f html --exit-code False 
cp *.* functional-output/
