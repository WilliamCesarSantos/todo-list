./gradlew run &
sleep 10
npm install -g newman-reporter-html
npm install -g newman
newman run ./src/test/resources/todo-list.postman_collection.json -e ./src/test/resources/todo-list.postman_environment.json --reporters cli,html --reporter-html-template ./src/test/resources/html_template.hbs --reporter-html-export ./tests_reports/newman.html --insecure
./gradlew --stop