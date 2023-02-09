#!/bin/sh
for i in {1..2}
do
  curl -s -o /dev/null -w "%{http_code}" -H "apikey: joe" localhost:9080/hello
  printf "\n"
  curl -s -o /dev/null -w "%{http_code}" -H "apikey: jane" localhost:9080/hello
  printf "\n"
done