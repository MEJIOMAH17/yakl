../gradlew :stdout:jmhJar
mkdir ./build/reports
java -jar ./build/libs/stdout-jmh.jar  -wi 5 -i 10 -f 2 > ./build/reports/jmh.txt
tail ./build/reports/jmh.txt