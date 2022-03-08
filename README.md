# Stock Notifier

You can be notified when a product is on stock.
I needed this because I wanted to buy a bicycle and my length is quite popular at this moment.
So I thought maybe I can create a project of it and learn some Java on the way.

You can create a task which should be in json, 
in the task you can define the interval (which is in seconds) and to which email adress it should go and so on. 
You can copy the task that is already in the "tasks" folder if you would like to get started or you could simply create one yourself with the code below.

And if you think something is missing or the code can be better, Let me know or create a pull request.
I used jdk 11.

### Email template
The following variables are available in the mail template.
{{emailTitle}}, {{taskUrl}}, {{taskTitle}}.
You can see an example in the templates folder.

### Config
There is also a config.properties.example file in the root. You should copy this and use your own credentials or use the command below.

```
cp config.properties.example config.properties
```

### Example task
```
{
  "title": "Fietsvoordeelshop.nl Gazelle Orange c7 +",
  "url": "https://www.fietsvoordeelshop.nl/gazelle-orange-c7-plus-2022-grijs-heren",
  "selector": "#productSelector115915Label",
  "contains": "voorraad",
  "interval": 30,
  "email": {
    "from": "example@example.com",
    "to": "example@example.com",
    "title": "Gazelle Orange c7 + is op voorraad bij de fietsvoordeelshop.nl"
  }
}
```

### Commands for compiling and running the jar
```
mvn clean compile assembly:single
java -jar target/stock_notifier.jar
```

### Run project in Docker

First you have to build it
```
docker build -t stock_notifier .
```

Then you can use any of these two commands below

#### Run docker in interactive mode
```
docker run --rm -it stock_notifier:latest
```
#### Run docker in the background
```
docker run --rm -it -v  -d stock_notifier:latest
```