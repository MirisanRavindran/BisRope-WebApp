# w23-csci2020u-FinalProject-Mata-Cardoso-Martin-Ravindran-Panchal

## This is the final project for the course CSCI2020U - Software Development

Written by the following students:

* [James Mata]()
* [Andres Cardoso]()
* [Dennis Martin]()
* [Harsh Panchal]()
* [Mirisan Ravindran]()

## Project Description

BisRope is an advanced chatserver application that allows users to create servers that contain chat rooms so that they can communicate in an organized way. This system supports an unlimited number of servers and chat rooms within memory. BisRope also supports the ability to send photos and emojis to other users aswell as video calling between 2 users. BisRope works with a Restful API and a Websocket API to perform such actions.

## This video demonstrates the functionality of the project https://www.youtube.com/watch?v=b8qguJ5WiyA

![Screenshot](server.png)

In this photo we can see the view from inside a server

![Screenshot](chat.png)

From this photo we can see the view from inside a room inside a server

![Screenshot](video.png)

From this photo we can see the view from inside a video call

## Project Features

* Account creation
* Server creation and the ability to join pre-existing servers
* Room creation and the ability to join existing rooms
* Video calling between users
* Text messaging between users
* Image file transfer between users
* Emoji messaging between users

## How to use BisRope

Initially, the entire repository must be cloned, and a glassfish server must be set up. For the BisropeServer project run glassfish local with the url http://localhost:8080/BisRopeServer-1.0-SNAPSHOT/ with the war exploded airtifact and for the WSChat Project run a remote server with the url http://localhost:8080/WSChat-1.0-SNAPSHOT/login.html with the war artifact

Starting up the application in the order local -> remote you should be met with the login screen where your can create an account and login

## Libraries Used

Agora.io Sdk https://www.agora.io/en/

## Known issues

We use alot of local caching of data within the browser so running multiple windows simultaneously can lead to data being overwritten. We are overlooking this because in reality there shouldnt be multiple accounts using the same machine at the same time.

## Planned features

* Chat History
* Custom Room Names
* Password encryption
* Screensharing and group video calls
* Multi media transfer
* Server privilages
