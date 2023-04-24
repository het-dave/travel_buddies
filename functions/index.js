/* eslint-disable no-irregular-whitespace */
/* eslint-disable object-curly-spacing */
/* eslint-disable no-trailing-spaces */
/* eslint-disable max-len */
const functions = require("firebase-functions");
const admin = require("firebase-admin");
admin.initializeApp();

exports.storeToken = functions.database.ref("/users/{userId}/fcmToken").onWrite((change, context) => {
  const token = change.after.val();
  const userId = context.params.userId;
  console.log(`Received token: ${token}, for user: ${userId}`);
  
  // Store the token in Firebase Realtime Database
  const db = admin.database();
  const userRef = db.ref(`users/${userId}`);
  return userRef.update({ fcmToken: token });
});
