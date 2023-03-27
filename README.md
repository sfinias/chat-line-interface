# Chat Line Interface 

This is ChatOps solution, basically combining the vast utilities of a command line interface with the portability of a chat client.

As my pet project, its purpose is to automate some of my daily tasks which I can run from my mobile phone and have a bit of fun :) 

As of this moment, it supports 3 main functions/commands
* Interaction with the [toggl](https://toggl.com/) API
* Fetch cat pics
* Fetch memes

Stack used:
* Quarkus
* Picocli
* [telegrambots](https://github.com/rubenlagus/TelegramBots)

It currently supports only telegram from the chat clients, with intention to add slack integration in the future.

In order to run, you need to set up an environment variable `sigmafi_apikey` with the api key of the telegram bot.

To run it in dev mode locally, simple run `./mvnw quarkus:dev` or if you have the Quarkus CLI installed `quarkus dev`

In dev mode, the telegram bot will run in LongPolling mode, querying for updates every second.

In production, it behaves as a REST API which you can use it to webhook with the telegram main server by curling the url below
`curl https://api.telegram.org/bot<apikey>/setWebhook?url=<hosted_url>`