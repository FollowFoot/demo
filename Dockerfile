FROM java:8u111
MAINTAINER jifei_mei
COPY demo.jar /fpi/demo.jar
CMD cd /fpi && java -jar demo.jar 