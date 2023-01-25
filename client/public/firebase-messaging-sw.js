importScripts('https://www.gstatic.com/firebasejs/8.8.0/firebase-app.js');
importScripts('https://www.gstatic.com/firebasejs/8.8.0/firebase-messaging.js');
const firebaseConfig = {
  apiKey: 'AIzaSyCyu7PAZoZkoFeihqUfT11U27iNK_PKnYg',
  authDomain: 'challenge-65eff.firebaseapp.com',
  projectId: 'challenge-65eff',
  storageBucket: 'challenge-65eff.appspot.com',
  messagingSenderId: '312532099846',
  appId: '1:312532099846:web:b0897878c226dda13db487',
  measurementId: 'G-4EM46N63HM',
};

firebase.initializeApp(firebaseConfig);
// Retrieve an instance of Firebase Messaging so that it can handle background
// messages.
const messaging = firebase.messaging();
console.log(messaging);

messaging.onBackgroundMessage((payload) => {
  console.log(
    '[firebase-messaging-sw.js] Received background message ',
    payload,
  );
  // Customize notification here
  const notificationTitle = 'Background Message Title';
  const notificationOptions = {
    body: 'Background Message body.',
    icon: '/firebase-logo.png',
  };

  self.registration.showNotification(notificationTitle, notificationOptions);
});
