// ********************************
// * Data: 12/08/2017
// * Inicialização
// ********************************

const functions = require('firebase-functions');
const gcs = require('@google-cloud/storage')();
var firebase = require('firebase');
var request = require('request');
var dateFormat = require('dateformat');

var serviceAccount = require("./confirmaconsulta-63f26-firebase-adminsdk-29x0s-5f23cec972.json");
var API_KEY = "AAAACdA7eJU:APA91bEkS6yWa8DIQwD9yIlNAi502yLAI2dPZrJwtK0WVf_XKAb2sWMVwZn5c6FP5mHW5bxLd5J1D9EJw4HhOSsZqJw-AnmD7jeaXV3DR06aWrcHYwq1kKsu_STLZoHjTGOKMaUp5hR3";

var app = firebase.initializeApp({
//  apiKey: API_KEY,
  authDomain: "confirmaconsulta-63f26.firebaseapp.com",
  appName: "ConfirmaConsulta",
  credential: "./google-services.json",
  databaseURL: "https://confirmaconsulta-63f26.firebaseio.com",
  apiKey: "AIzaSyDmR3P5F8b7KD2omh4RrUfBSNL4cvsEuBU",
  projectId: "confirmaconsulta-63f26",
  storageBucket: "confirmaconsulta-63f26.appspot.com",
  messagingSenderId: "42148264085"
});

var databaseRef = app.database();

// ********************************
// * Data: 22/07/2017
// * Mensagens
// ********************************

exports.sendMSG = functions.https.onRequest((req, res) => {
  if (req.query.origem === undefined) {
    res.status(400).send('No user defined!');
  } else {
    const destino = req.query.destino;
    const mensagem = req.query.mensagem;
    const origem = req.query.origem;

    sendMessageToDevice(origem, destino, mensagem, function() {
      var now = new Date();
      var formatDate = dateFormat(now, "dd/mm/yyyy HH:MM:ss");

      databaseRef.ref('mensagens').push({ 
        origem: origem,
        data: formatDate,
        destino: destino,
        mensagem: mensagem 
      }, function(error) {
        if (error) console.log('ooops');
      });
      res.status(200).send('Adicionando Mensagem');
    });
  }
});

exports.mensagemList = functions.https.onRequest((req, res) => {
  databaseRef.ref('mensagens').once('value').then(function (snap) {
    res.status(200).send(snap.val());
  });
});

function sendMessageToTopic(username, message, onSuccess) {
  request({
    url: 'https://fcm.googleapis.com/fcm/send',
    method: 'POST',
    headers: {
      'Content-Type' :' application/json',
      'Authorization': 'key='+API_KEY
    },
    body: JSON.stringify({
      notification: {
        title: message
      },
      to : '/topics/user_'+username
    })
  }, function(error, response, body) {
    if (error) { console.error(error); }
    else if (response.statusCode >= 400) { 
      console.error('HTTP Error: '+response.statusCode+' - '+response.statusMessage); 
    }
    else {
      onSuccess();
    }
  });
}

function sendMessageToDevice(origem, destino, mensagem, onSuccess) {
    request({
        url: 'https://fcm.googleapis.com/fcm/send',
        method: 'POST',
        headers: {
            'Content-Type' :' application/json',
            'Authorization': 'key='+API_KEY
        },
        body: JSON.stringify({
            notification: { 
                body: {
                  mensagem: mensagem,
                  from: origem
                },
                title: 'confirmacao',
                priority: 10
            },
            to: destino
        })
    }, function(error, response, body) {
        if (error) { console.log(error); }
        else if (response.statusCode >= 400) {
            console.error('HTTP Error: '+response.statusCode+' - '+response.statusMessage); 
        } else {
            onSuccess();
        }
    });
}






