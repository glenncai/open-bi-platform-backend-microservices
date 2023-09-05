#!/bin/bash

while :
do
  response=$(curl -s -X POST -H 'Content-Type: application/json' -d '{"username":"admin","password":"test123456!"}' http://localhost:8000/api/user/login)
  code=$(echo "$response" | grep -oP '"code":\s*\K[0-9]+')
  message=$(echo "$response" | grep -oP '"message":\s*"\K[^"]+')
  data=$(echo "$response" | grep -oP '"data":\s*\K[^,]+')
  echo "Code: $code"
  echo "Message: $message"
  echo "Data: $data"
  echo ""
  # sleep 1
done